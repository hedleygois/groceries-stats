package com.hedley.groceriesstats.purchase

import com.hedley.groceriesstats.itempurchase.ItemPurchaseService
import com.hedley.groceriesstats.itempurchase.SaveItemPurchaseDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigInteger

@Service
class PurchaseService(val repository: PurchaseRepository, val itemPurchaseService: ItemPurchaseService) {

    private val log = LoggerFactory.getLogger(PurchaseService::class.java)

    fun list() = repository.list()
    fun findById(id: BigInteger) = repository.findById(id)
    fun findByDate(startDate: String, endDate: String) = repository.findByDate(startDate, endDate)

    @Transactional
    fun save(dto: SavePurchaseDTO): Mono<SavedPurchaseDTO> =
        repository.create(dto) // Save the purchase
            .flatMap { savedPurchaseDTO ->
                savedPurchaseDTO.purchase.id?.let { purchaseId ->
                    // Create a Flux from the list of items and save each one after the purchase is saved
                    val itemCreationFlux = Flux.fromIterable(dto.items)
                        .flatMap { itemCreation ->
                            itemPurchaseService.create(
                                SaveItemPurchaseDTO(
                                    itemId = itemCreation.itemId,
                                    purchaseId = purchaseId,
                                    value = itemCreation.quantity * itemCreation.value,
                                    grams = itemCreation.grams,
                                    quantity = itemCreation.quantity
                                )
                            )
                        }

                    // Ensure all item creations are completed before returning the saved purchase
                    itemCreationFlux.then(Mono.just(savedPurchaseDTO))
                } ?: Mono.error(IllegalStateException("Purchase ID is null"))
            }

}