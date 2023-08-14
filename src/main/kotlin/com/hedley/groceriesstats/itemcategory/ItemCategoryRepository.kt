package com.hedley.groceriesstats.itemcategory

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.math.BigInteger

interface ItemCategoryRepository : ReactiveCrudRepository<ItemCategory, BigInteger> {
    fun findByNameContainingIgnoreCase(name: String): Flux<ItemCategory>
}