package com.hedley.groceriesstats.itempurchase

import com.hedley.groceriesstats.brands.Brand
import com.hedley.groceriesstats.franchises.Franchise
import com.hedley.groceriesstats.itemcategory.ItemCategory
import com.hedley.groceriesstats.items.Item
import com.hedley.groceriesstats.items.ItemDTO
import com.hedley.groceriesstats.purchase.Purchase
import com.hedley.groceriesstats.purchase.PurchaseDTO
import com.hedley.groceriesstats.supermarkets.Supermarket
import com.hedley.groceriesstats.supermarkets.SupermarketDTO
import io.r2dbc.spi.Readable
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger
import java.time.LocalDateTime

@Service
class ItemPurchaseRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val defaultItemPurchaseRepository: DefaultItemPurchaseRepository
) : ItemPurchaseRepository {
    override fun findByItem(itemId: BigInteger): Flux<ItemPurchaseDTO> =
        databaseClient.sql(
            """
                SELECT ip.id AS ip_id, ip.item_id AS ip_item_id, ip.value AS ip_value, 
                ip.purchase_id AS ip_purchase_id, ip.grams AS ip_grams, ip.quantity AS ip_quantity,
                i.id AS i_id, i.name AS i_name, 
                ic.id AS ic_id, ic.name AS ic_name,
                b.id AS b_id, b.name AS b_name,
                p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM items_purchase ip
                INNER JOIN items i ON i.id = ip.id
                INNER JOIN items_category ic ON ic.id = i.category_id
                INNER JOIN brands b ON b.id = i.brand.id
                INNER JOIN purchase p ON p.id = ip.purchase_id
                INNER JOIN supermarkets s ON s.id = p.supermarket.id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE i.id = :itemId
            """.trimIndent()
        ).bind("itemId", itemId).map(::mapItemPurchaseSqlRowToItemPurchaseDTO).all()


    override fun findByPurchase(purchaseId: BigInteger): Flux<ItemPurchaseDTO> =
        databaseClient.sql(
            """
                SELECT ip.id AS ip_id, ip.item_id AS ip_item_id, ip.value AS ip_value, 
                ip.purchase_id AS ip_purchase_id, ip.grams AS ip_grams, ip.quantity AS ip_quantity,
                i.id AS i_id, i.name AS i_name, 
                ic.id AS ic_id, ic.name AS ic_name,
                b.id AS b_id, b.name AS b_name,
                p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, 
                p.payment_types_id AS p_payment_types_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM items_purchase ip
                INNER JOIN items i ON i.id = ip.id
                INNER JOIN items_category ic ON ic.id = i.category_id
                INNER JOIN brands b ON b.id = i.brand_id
                INNER JOIN purchases p ON p.id = ip.purchase_id
                INNER JOIN supermarkets s ON s.id = p.supermarket_id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.id = :purchaseId
            """.trimIndent()
        ).bind("purchaseId", purchaseId).map(::mapItemPurchaseSqlRowToItemPurchaseDTO).all()

    override fun findByPurchases(purchasesIds: List<BigInteger>): Flux<ItemPurchaseDTO> =
        databaseClient.sql(
            """
                SELECT ip.id AS ip_id, ip.item_id AS ip_item_id, ip.value AS ip_value, ip.purchase_id AS ip_purchase_id, ip.grams AS ip_grams,
                i.id AS i_id, i.name AS i_name, 
                ic.id AS ic_id, ic.name AS ic_name,
                b.id AS b_id, b.name AS b_name,
                p.id AS p_id, p.date AS p_date, p.supermarket_id AS p_supermarket_id, 
                p.payment_types_id AS p_payment_types_id, p.totalvalue AS p_total_value,
                s.id AS s_id, s.name AS s_name,
                f.id AS f_id, f.name AS f_name
                FROM items_purchase ip
                INNER JOIN items i ON i.id = ip.id
                INNER JOIN items_category ic ON ic.id = i.category_id
                INNER JOIN brands b ON b.id = i.brand_id
                INNER JOIN purchases p ON p.id = ip.purchase_id
                INNER JOIN supermarkets s ON s.id = p.supermarket_id
                INNER JOIN franchises f ON f.id = s.franchise_id
                WHERE p.id IN (:purchasesIds)
            """.trimIndent()
        ).bind("purchasesIds", purchasesIds).map(::mapItemPurchaseSqlRowToItemPurchaseDTO).all()


    override fun create(dto: SaveItemPurchaseDTO): Mono<SavedItemPurchaseDTO> =
        defaultItemPurchaseRepository.save(
            ItemPurchase(
                purchaseId = dto.purchaseId,
                itemId = dto.itemId,
                value = dto.value,
                grams = dto.grams,
                quantity = dto.quantity
            )
        ).map { itemPurchase ->
            SavedItemPurchaseDTO(itemPurchase = itemPurchase)
        }


    private fun mapItemPurchaseSqlRowToItemPurchaseDTO(row: Readable): ItemPurchaseDTO {
        val item = Item(
            id = row.get("ip_item_id", BigInteger::class.java),
            name = row.get("i_name", String::class.java) ?: "",
            categoryId = row.get("ic_id", BigInteger::class.java),
            brandId = row.get("b_id", BigInteger::class.java)
        )
        val brand = Brand(
            id = row.get("b_id", BigInteger::class.java),
            name = row.get("b_name", String::class.java) ?: "",
        )
        val category = ItemCategory(
            id = row.get("ic_id", BigInteger::class.java),
            name = row.get("ic_name", String::class.java) ?: "",
        )
        val purchase = Purchase(
            id = row.get("p_id", BigInteger::class.java),
            date = row.get("p_date", LocalDateTime::class.java) ?: LocalDateTime.MIN,
            // for some reason decoding to float still throws an error on r2jdbc driver. let's convert to string and then to float
            totalValue = row.get("p_total_value", String::class.java)?.toFloat() ?: 0.0F,
            supermarketId = row.get("p_supermarket_id", BigInteger::class.java),
            paymentTypeId = row.get("p_payment_types_id", BigInteger::class.java)
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
        val itemPurchase = ItemPurchase(
            id = row.get("ip_id", BigInteger::class.java),
            itemId = row.get("i_id", BigInteger::class.java) ?: BigInteger.ZERO,
            purchaseId = row.get("p_id", BigInteger::class.java) ?: BigInteger.ZERO,
            value = row.get("ip_value", String::class.java)?.toFloat() ?: 0.0F,
            grams = row.get("ip_grams", String::class.java)?.toFloat() ?: 0.0F,
            quantity = row.get("ip_quantity", String::class.java)?.toInt() ?: 0,
        )
        return ItemPurchaseDTO(
            item = ItemDTO(id = item.id ?: BigInteger.ZERO, name = item.name, brand = brand, category = category),
            purchase = PurchaseDTO(
                purchase = purchase,
                supermarket = SupermarketDTO(supermarket = supermarket, franchise = franchise),
            ),
            value = itemPurchase.value
        )
    }
}