package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.supermarkets.SupermarketDTO

data class PurchaseDTO(val purchase: Purchase, val supermarket: SupermarketDTO)
