package com.hedley.groceriesstats.purchase

import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class PurchaseService(val repository: PurchaseRepository) {
    fun list() = repository.list()
    fun findById(id: BigInteger) = repository.findById(id)
    fun findByDate(startDate: String, endDate: String) = repository.findByDate(startDate, endDate)
    fun save(dto: SavePurchaseDTO) = repository.create(dto)
}