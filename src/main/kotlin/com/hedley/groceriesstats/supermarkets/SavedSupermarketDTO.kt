package com.hedley.groceriesstats.supermarkets

import java.math.BigInteger

data class SavedSupermarketDTO(
    val id: BigInteger,
    val name: String,
    val franchiseId: BigInteger,
)