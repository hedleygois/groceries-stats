package com.hedley.groceriesstats.itempurchase

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("items_purchase")
data class ItemPurchase(
    @Id val id: BigInteger? = null,
    val itemId: BigInteger? = null,
    val purchaseId: BigInteger? = null,
    val value: Double
)