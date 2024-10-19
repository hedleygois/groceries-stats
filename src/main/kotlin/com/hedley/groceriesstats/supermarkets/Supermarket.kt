package com.hedley.groceriesstats.supermarkets

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("supermarkets")
data class Supermarket(
    @Id val id: BigInteger? = null,
    val name: String,
    @Column("franchise_id") val franchiseId: BigInteger,
)
