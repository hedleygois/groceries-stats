package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.supermarkets.SupermarketDTO
import java.math.BigInteger
import java.time.LocalDateTime

data class PurchaseDTO(
    val id: BigInteger,
    val date: LocalDateTime,
    val totalValue: Float,
    val paymentTypeId: BigInteger,
    val supermarket: SupermarketDTO
)
