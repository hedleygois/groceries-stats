package com.hedley.groceriesstats.itempurchase

import com.hedley.groceriesstats.items.ItemDTO
import com.hedley.groceriesstats.purchase.PurchaseDTO

data class ItemPurchaseDTO(val item: ItemDTO, val purchase: PurchaseDTO, val value: Float)
