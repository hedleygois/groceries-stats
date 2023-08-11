package com.hedley.groceriesstats.supermarkets

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.math.BigInteger

interface DefaultSupermarketRepository : ReactiveCrudRepository<Supermarket, BigInteger>