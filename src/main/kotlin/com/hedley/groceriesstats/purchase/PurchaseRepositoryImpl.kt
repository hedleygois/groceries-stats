package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.franchises.Franchise
import com.hedley.groceriesstats.itempurchase.ItemPurchaseDTO
import com.hedley.groceriesstats.itempurchase.ItemPurchaseRepository
import com.hedley.groceriesstats.supermarkets.Supermarket
import com.hedley.groceriesstats.supermarkets.SupermarketDTO
import io.r2dbc.spi.Readable
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.math.BigInteger
import java.time.Duration

@Service
class PurchaseRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val defaultPurchaseRepository: DefaultPurchaseRepository,
    private val itemPurchaseRepository: ItemPurchaseRepository
) : PurchaseRepository {
    override fun findById(id: BigInteger): Mono<PurchaseDTO> {
        val purchaseDTO = databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM purchase p
                INNER JOIN supermarkets s ON s.id = p.supermarket.id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.id = :purchaseId
            """.trimIndent()
        ).bind("purchaseId", id).map(::mapPurchaseSqlRowToPurchaseDTO).all()

        return purchaseDTO.flatMap { dto ->
            itemPurchaseRepository.findByPurchase(id).collectList().map { items ->
                dto.copy(items = items)
            }
        }.toMono()
    }

    override fun findByDate(startDate: String, endDate: String): Flux<PurchaseDTO> {
        val purchaseDTO = databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM purchase p
                INNER JOIN supermarkets s ON s.id = p.supermarket.id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.date BETWEEN :startDate AND :endDate
            """.trimIndent()
        ).bind("startDate", startDate).bind("endDate", endDate).map(::mapPurchaseSqlRowToPurchaseDTO).all()

        return purchaseDTO.flatMap { dto ->
            itemPurchaseRepository.findByPurchase(dto.purchase.id ?: BigInteger.ZERO).collectList().map { items ->
                dto.copy(items = items)
            }
        }
    }

    override fun create(dto: SavePurchaseDTO): Mono<SavedPurchaseDTO> =
        defaultPurchaseRepository.save(
            Purchase(
                totalValue = dto.totalValue,
                date = dto.date,
                supermarketId = dto.supermarketId
            )
        ).map { purchase ->
            SavedPurchaseDTO(purchase = purchase)
        }.toMono()

    override fun list(): Flux<PurchaseDTO> {
        val purchasesDTO = databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM purchase p
                INNER JOIN supermarkets s ON s.id = p.supermarket.id
                INNER JOIN franchises f ON f.id = s.franchise_id
            """.trimIndent()
        ).map(::mapPurchaseSqlRowToPurchaseDTO).all()

        return purchasesDTO.map { dto ->
            val items = itemPurchaseRepository.findByPurchase(dto.purchase.id ?: BigInteger.ZERO).map { items ->
                items.item
            }.collectList().block(Duration.ofMillis(100)) ?: emptyList()
            dto.copy(items = items.map { item ->
                ItemPurchaseDTO(
                    item = item,
                    purchase = dto,
                    value = dto.purchase.totalValue
                )
            })
        }
    }

    private fun mapPurchaseSqlRowToPurchaseDTO(row: Readable): PurchaseDTO {
        val purchase = Purchase(
            id = row.get("p_id", BigInteger::class.java),
            date = row.get("p_name", String::class.java) ?: "",
            totalValue = 0.0, // it won't matter for these operations,
            supermarketId = row.get("p_supermarket_id", BigInteger::class.java)
        )
        val supermarket = Supermarket(
            id = row.get("s_id", BigInteger::class.java),
            name = row.get("s_name", String::class.java) ?: "",
            franchiseId = row.get("f_id", BigInteger::class.java)
        )
        val franchise = Franchise(
            id = row.get("f_id", BigInteger::class.java),
            name = row.get("f_name", String::class.java) ?: "",
        )
        return PurchaseDTO(
            purchase = purchase,
            supermarket = SupermarketDTO(supermarket = supermarket, franchise = franchise),
            items = emptyList()
        )
    }
}