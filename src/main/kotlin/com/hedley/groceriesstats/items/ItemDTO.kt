package com.hedley.groceriesstats.items

import com.hedley.groceriesstats.brands.Brand
import com.hedley.groceriesstats.itemcategory.ItemCategory
import java.math.BigInteger

data class ItemDTO(val id: BigInteger, val name: String, val brand: Brand, val category: ItemCategory)