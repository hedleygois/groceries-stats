package com.hedley.groceriesstats.franchises

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class FranchiseService(val repository: FranchiseRepository) {

    fun findByName(name: String): Flux<Franchise> {
        return repository.findByNameContainingIgnoreCase(name)
    }

    fun findById(id: BigInteger): Mono<Franchise> {
        return repository.findById(id)
    }

    fun save(dto: SaveFranchiseDTO): Mono<Franchise> {
        return repository.save(Franchise(name = dto.name))
    }

}