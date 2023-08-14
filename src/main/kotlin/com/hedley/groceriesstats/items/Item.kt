package com.hedley.groceriesstats.items

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("items")
data class Item(
    @Id val id: BigInteger? = null,
    val name: String,
    @Column("category_id") val categoryId: BigInteger?,
    @Column("brand_id") val brandId: BigInteger?
)