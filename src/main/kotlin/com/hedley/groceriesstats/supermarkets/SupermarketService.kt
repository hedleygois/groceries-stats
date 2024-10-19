package com.hedley.groceriesstats.supermarkets

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class SupermarketService(val repository: SupermarketRepository) {
    fun findByName(name: String): Flux<SupermarketDTO> = repository.findByNameWithFranchise(name)
    fun findById(id: BigInteger): Mono<SupermarketDTO> = repository.findById(id)

    fun list(): Flux<SupermarketDTO> = repository.list()
    fun save(dto: SaveSupermarketDTO): Mono<SavedSupermarketDTO> = repository.save(dto)
}