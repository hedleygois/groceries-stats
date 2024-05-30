package com.hedley.groceriesstats.brands

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class BrandService(val repository: BrandRepository) {

    fun findByName(name: String): Flux<Brand> {
        return repository.findByNameContainingIgnoreCase(name)
    }

    fun findById(id: BigInteger): Mono<Brand> {
        return repository.findById(id)
    }

    fun save(dto: SaveBrandDTO): Mono<Brand> {
        return repository.save(Brand(name = dto.name))
    }

    fun list(): Flux<Brand> = repository.findAll()

}