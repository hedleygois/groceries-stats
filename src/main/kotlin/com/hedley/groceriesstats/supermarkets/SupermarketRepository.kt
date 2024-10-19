package com.hedley.groceriesstats.supermarkets

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

interface SupermarketRepository {
    fun findByNameWithFranchise(name: String): Flux<SupermarketDTO>
    fun findById(id: BigInteger): Mono<SupermarketDTO>

    fun list(): Flux<SupermarketDTO>
    fun save(dto: SaveSupermarketDTO): Mono<SavedSupermarketDTO>
}