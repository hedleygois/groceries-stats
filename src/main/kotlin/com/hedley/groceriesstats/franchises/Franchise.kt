package com.hedley.groceriesstats.franchises

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("franchises")
data class Franchise(@Id val id: BigInteger? = null, @Column("name") val name: String)