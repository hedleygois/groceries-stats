package com.hedley.groceriesstats.brands

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("brands")
data class Brand(@Id val id: BigInteger? = null, val name: String)
