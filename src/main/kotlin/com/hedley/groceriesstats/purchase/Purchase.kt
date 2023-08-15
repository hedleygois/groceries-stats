package com.hedley.groceriesstats.purchase

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("purchases")
data class Purchase(
    @Id val id: BigInteger? = null,
    val date: String,
    val totalValue: Double,
    val supermarketId: BigInteger? = null,
)
