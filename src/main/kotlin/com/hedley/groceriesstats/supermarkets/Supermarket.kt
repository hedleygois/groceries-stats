package com.hedley.groceriesstats.supermarkets

import com.hedley.groceriesstats.franchises.Franchise
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import reactor.core.publisher.Mono
import java.math.BigInteger

@Table("supermarkets")
data class Supermarket(
    @Id val id: BigInteger? = null,
    val name: String?,
    @Column("franchise_id") val franchiseId: BigInteger?,
//    @Transient val franchise: Franchise? = null
)
