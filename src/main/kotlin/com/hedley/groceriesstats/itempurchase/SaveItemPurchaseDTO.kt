package com.hedley.groceriesstats.itempurchase

import java.math.BigInteger

data class SaveItemPurchaseDTO(val itemId: BigInteger, val purchaseId: BigInteger, val value: Double)