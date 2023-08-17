package com.hedley.groceriesstats.franchises

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("franchises")
data class Franchise(
    @Id @field:Schema(
        description = "The ID of a Franchise",
        example = "11111",
        type = "number"
    ) val id: BigInteger? = null, @Column("name") @field:Schema(
        description = "The name of a Franchise",
        example = "Jumbo",
        type = "string"
    ) val name: String
)