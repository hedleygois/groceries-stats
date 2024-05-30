package com.hedley.groceriesstats.itemcategory

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class ItemCategoryService(private val itemCategoryRepository: ItemCategoryRepository) {
    fun findByName(name: String): Flux<ItemCategory> = itemCategoryRepository.findByNameContainingIgnoreCase(name)
    fun findById(id: BigInteger): Mono<ItemCategory> = itemCategoryRepository.findById(id)
    fun save(dto: SaveItemCategoryDTO): Mono<ItemCategory> =
        itemCategoryRepository.save(ItemCategory(id = null, name = dto.name))
    fun list(): Flux<ItemCategory> = itemCategoryRepository.findAll()
}