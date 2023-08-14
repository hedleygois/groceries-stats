package com.hedley.groceriesstats.itemcategory

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("items_category")
data class ItemCategory(@Id val id: BigInteger? = null, val name: String)
