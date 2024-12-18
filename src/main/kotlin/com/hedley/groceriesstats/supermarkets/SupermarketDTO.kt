package com.hedley.groceriesstats.supermarkets

import com.hedley.groceriesstats.franchises.FranchiseDTO
import java.math.BigInteger

data class SupermarketDTO(
    val id: BigInteger,
    val name: String,
    val franchise: FranchiseDTO
)