package com.hedley.groceriesstats.purchase

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger
import java.time.LocalDateTime

@Table("purchases")
data class Purchase(
    @Id val id: BigInteger? = null,
    val date: LocalDateTime,
    @Column("totalvalue") val totalValue: Float,
    val supermarketId: BigInteger? = null,
    @Column("payment_types_id") val paymentTypeId: BigInteger? = null
)