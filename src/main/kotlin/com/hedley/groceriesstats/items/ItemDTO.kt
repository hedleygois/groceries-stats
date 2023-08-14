package com.hedley.groceriesstats.items

import com.hedley.groceriesstats.brands.Brand
import com.hedley.groceriesstats.itemcategory.ItemCategory

data class ItemDTO(val item: Item, val brand: Brand, val category: ItemCategory)