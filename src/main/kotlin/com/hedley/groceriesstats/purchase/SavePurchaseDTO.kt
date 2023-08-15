package com.hedley.groceriesstats.purchase

import java.math.BigInteger

data class SavePurchaseDTO(val totalValue: Double, val date: String, val supermarketId: BigInteger)