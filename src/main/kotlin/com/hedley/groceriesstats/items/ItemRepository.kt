package com.hedley.groceriesstats.items

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

interface ItemRepository {
    fun findByName(name: String): Flux<ItemDTO>
    fun findById(id: BigInteger): Mono<ItemDTO>
    fun save(dto: SaveItemDTO): Mono<SavedItemDTO>

    fun list(): Flux<ItemDTO>
}