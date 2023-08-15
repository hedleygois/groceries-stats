package com.hedley.groceriesstats.purchase

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

interface PurchaseRepository {
    fun findById(id: BigInteger): Mono<PurchaseDTO>
    fun findByDate(startDate: String, endDate: String): Flux<PurchaseDTO>
    fun create(dto: SavePurchaseDTO): Mono<SavedPurchaseDTO>
}