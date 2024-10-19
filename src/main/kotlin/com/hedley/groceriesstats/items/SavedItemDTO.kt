package com.hedley.groceriesstats.items

import java.math.BigInteger

data class SavedItemDTO(
    val id: BigInteger,
    val name: String,
    val categoryId: BigInteger,
    val brandId: BigInteger
)