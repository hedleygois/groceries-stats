package com.hedley.groceriesstats.items

import com.hedley.groceriesstats.brands.Brand
import com.hedley.groceriesstats.itemcategory.ItemCategory
import io.r2dbc.spi.Readable
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.math.BigInteger

@Component
class ItemRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val defaultItemRepository: DefaultItemRepository,
) : ItemRepository {
    override fun findByNameWithFranchise(name: String): Flux<ItemDTO> {
        return databaseClient.sql(
            """
            SELECT i.id as i_id, i.name as i_name, ic.id as ic_id, ic.name as ic_name, b.id as b_id, b.name as b_name 
            FROM items i
            INNER JOIN items_category ic ON ic.id = i.category_id
            INNER JOIN brands b ON b.id = i.brand_id
            WHERE s.name LIKE :name                
        """.trimIndent()
        ).bind("name", name).map(::mapItemSqlRowToItemDTO).all()
    }


    override fun findById(id: BigInteger): Mono<ItemDTO> =
        databaseClient.sql(
            """
            SELECT i.id as i_id, i.name as i_name, ic.id as ic_id, ic.name as ic_name, b.id as b_id, b.name as b_name 
            FROM items i
            INNER JOIN items_category ic ON ic.id = i.category_id
            INNER JOIN brands b ON b.id = i.brand_id
            WHERE s.id = :id                
        """.trimIndent()
        ).bind("id", id).map(::mapItemSqlRowToItemDTO).all().toMono()

    override fun save(dto: SaveItemDTO): Mono<SavedItemDTO> =
        defaultItemRepository.save(Item(name = dto.name, brandId = dto.brandId, categoryId = dto.categoryId))
            .map { item ->
                SavedItemDTO(item = item)
            }

    private fun mapItemSqlRowToItemDTO(row: Readable): ItemDTO {
        val brand = Brand(
            id = row.get("b_id", BigInteger::class.java),
            name = row.get("b_name", String::class.java) ?: ""
        )
        val category = ItemCategory(
            id = row.get("ic_id", BigInteger::class.java),
            name = row.get("ic_name", String::class.java) ?: ""
        )
        val item =
            Item(
                id = row.get("i_id", BigInteger::class.java),
                name = row.get("i_name", String::class.java) ?: "",
                brandId = brand.id,
                categoryId = category.id
            )
        return ItemDTO(item = item, brand = brand, category = category)
    }
}
