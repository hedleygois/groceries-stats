package com.hedley.groceriesstats.itempurchase

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

interface ItemPurchaseRepository {
    fun findByItem(itemId: BigInteger): Flux<ItemPurchaseDTO>
    fun findByPurchase(purchaseId: BigInteger): Flux<ItemPurchaseDTO>
    fun findByPurchases(purchasesIds: BigInteger): Flux<ItemPurchaseDTO>
    fun create(dto: SaveItemPurchaseDTO): Mono<SavedItemPurchaseDTO>
}