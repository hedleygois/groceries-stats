package com.hedley.groceriesstats.brands

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.math.BigInteger

interface BrandRepository : ReactiveCrudRepository<Brand, BigInteger> {
    fun findByNameContainingIgnoreCase(name: String): Flux<Brand>
}