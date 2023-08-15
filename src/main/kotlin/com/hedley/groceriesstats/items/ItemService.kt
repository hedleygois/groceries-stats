package com.hedley.groceriesstats.items

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class ItemService(val repository: ItemRepository) {
    fun findByName(name: String): Flux<ItemDTO> = repository.findByNameWithFranchise(name)
    fun findById(id: BigInteger): Mono<ItemDTO> = repository.findById(id)
    fun save(dto: SaveItemDTO): Mono<SavedItemDTO> = repository.save(dto)
}