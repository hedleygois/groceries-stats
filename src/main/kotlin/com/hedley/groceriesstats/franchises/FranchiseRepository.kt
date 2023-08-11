package com.hedley.groceriesstats.franchises

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.math.BigInteger

interface FranchiseRepository : ReactiveCrudRepository<Franchise, BigInteger> {
    fun findByNameContainingIgnoreCase(name: String): Mono<Franchise>
}