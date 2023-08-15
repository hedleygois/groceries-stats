package com.hedley.groceriesstats.purchase

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.math.BigInteger

interface DefaultPurchaseRepository : ReactiveCrudRepository<Purchase, BigInteger>