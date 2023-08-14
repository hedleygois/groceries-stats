package com.hedley.groceriesstats.items

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.math.BigInteger

interface DefaultItemRepository : ReactiveCrudRepository<Item, BigInteger>