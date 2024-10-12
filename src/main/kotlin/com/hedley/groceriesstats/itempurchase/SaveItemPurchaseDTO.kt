package com.hedley.groceriesstats.itempurchase

import java.math.BigInteger

data class SaveItemPurchaseDTO(
    val itemId: BigInteger,
    val purchaseId: BigInteger,
    val value: Float,
    val grams: Float = 0.0F,
    val quantity: Int = 0
)