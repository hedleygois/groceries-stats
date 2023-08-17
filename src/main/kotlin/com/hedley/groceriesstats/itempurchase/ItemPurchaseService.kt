package com.hedley.groceriesstats.itempurchase

import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class ItemPurchaseService(private val repository: ItemPurchaseRepository) {

    fun findByItemId(id: BigInteger) = repository.findByItem(id)

    fun findByPurchaseId(id: BigInteger) = repository.findByPurchase(id)

    fun findByPurchases(ids: List<BigInteger>) = repository.findByPurchases(ids)

    fun create(dto: SaveItemPurchaseDTO) = repository.create(dto)
}