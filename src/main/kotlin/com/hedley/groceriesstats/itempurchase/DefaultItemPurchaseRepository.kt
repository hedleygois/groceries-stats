package com.hedley.groceriesstats.itempurchase

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.math.BigInteger

interface DefaultItemPurchaseRepository : ReactiveCrudRepository<ItemPurchase, BigInteger>