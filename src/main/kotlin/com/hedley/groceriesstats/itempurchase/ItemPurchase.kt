package com.hedley.groceriesstats.itempurchase

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("items_purchase")
data class ItemPurchase(
    @Id val id: BigInteger? = null,
    @Column("item_id") val itemId: BigInteger,
    @Column("purchase_id") val purchaseId: BigInteger,
    val value: Float,
    val grams: Float,
    val quantity: Int
)