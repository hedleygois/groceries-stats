package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.items.ItemDTO
import java.math.BigInteger

data class SavePurchaseDTO(
    val items: List<ItemPurchaseCreation>,
    val totalValue: Float,
    val date: String,
    val supermarketId: BigInteger,
    val paymentTypeId: BigInteger
)

data class ItemPurchaseCreation(val itemId: BigInteger, val itemName: String, val value: Float, val grams: Float, val quantity: Int)
