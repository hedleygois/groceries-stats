package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.franchises.FranchiseDTO
import com.hedley.groceriesstats.itempurchase.ItemPurchaseRepository
import com.hedley.groceriesstats.supermarkets.SupermarketDTO
import io.r2dbc.spi.Readable
import org.slf4j.LoggerFactory
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class PurchaseRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val defaultPurchaseRepository: DefaultPurchaseRepository,
    private val itemPurchaseRepository: ItemPurchaseRepository,
) : PurchaseRepository {

    companion object {
        private val log = LoggerFactory.getLogger(PurchaseRepositoryImpl::class.java)
    }


    override fun findById(id: BigInteger): Mono<PurchaseDTO> =
        databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, 
                p.payment_types_id AS p_payment_types_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name, s.franchise_id AS s_franchise_id,
                f.id AS f_id, f.name AS f_name
                FROM purchases p
                INNER JOIN supermarkets s ON s.id = p.supermarket_id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.id = :purchaseId
            """.trimIndent()
        ).bind("purchaseId", id).map(::mapPurchaseSqlRowToPurchaseDTO).all().toMono()

    override fun findByDate(startDate: String, endDate: String): Flux<PurchaseDTO> =
        databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, 
                p.payment_types_id AS p_payment_types_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name, s.franchise_id AS s_franchise_id,
                f.id AS f_id, f.name AS f_name
                FROM purchases p
                INNER JOIN supermarkets s ON s.id = p.supermarket_id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.date BETWEEN :startDate AND :endDate
            """.trimIndent()
        ).bind("startDate", Instant.parse(startDate)).bind("endDate", Instant.parse(endDate))
            .map(::mapPurchaseSqlRowToPurchaseDTO).all()

    override fun create(dto: SavePurchaseDTO): Mono<SavedPurchaseDTO> =
        defaultPurchaseRepository.save(
            Purchase(
                totalValue = dto.totalValue,
                date = LocalDateTime.ofInstant(Instant.parse(dto.date), ZoneOffset.UTC),
                supermarketId = dto.supermarketId,
                paymentTypeId = dto.paymentTypeId
            )
        ).doOnError { error -> log.error(error.message) }.map { purchase -> SavedPurchaseDTO(purchase = purchase) }

    override fun list(): Flux<PurchaseDTO> =
        databaseClient.sql(
            """
                SELECT p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, 
                p.payment_types_id AS p_payment_types_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name, s.franchise_id AS s_franchise_id, 
                f.id AS f_id, f.name AS f_name,
                FROM purchases p
                INNER JOIN supermarkets s ON s.id = p.supermarket_id
                INNER JOIN franchises f ON f.id = s.franchise_id
                INNER JOIN payment_types pt ON pt.id = p.payment_types_id
            """.trimIndent()
        ).map(::mapPurchaseSqlRowToPurchaseDTO).all()

    override fun deleteAll(): Mono<Void> = defaultPurchaseRepository.deleteAll()

    // Note for the future me: This repository always returns a DTO instead of a model because these searches will always load the relations
    // and I'm not using good ol' Hibernate
    private fun mapPurchaseSqlRowToPurchaseDTO(row: Readable): PurchaseDTO =
        PurchaseDTO(
            id = row.get("p_id", BigInteger::class.java) ?: BigInteger.ZERO,
            date = row.get("p_date", LocalDateTime::class.java) ?: LocalDateTime.MIN,
            // for some reason decoding to float still throws an error on r2jdbc driver. let's convert to string and then to float
            totalValue = row.get("p_total_value", String::class.java)?.toFloat() ?: 0.0F,
            paymentTypeId = row.get("p_payment_types_id", BigInteger::class.java) ?: BigInteger.ZERO,
            supermarket = SupermarketDTO(
                id = row.get("s_id", BigInteger::class.java) ?: BigInteger.ZERO,
                name = row.get("s_name", String::class.java) ?: "",
                franchise = FranchiseDTO(
                    id = row.get("f_id", BigInteger::class.java) ?: BigInteger.ZERO,
                    name = row.get("f_name", String::class.java) ?: "",
                )
            ),
        )
}