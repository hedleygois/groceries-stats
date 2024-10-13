package com.hedley.groceriesstats.paymenttype

import com.hedley.groceriesstats.purchase.PaymentType
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.math.BigInteger

interface PaymentTypeRepository : ReactiveCrudRepository<PaymentType, BigInteger> {
    fun findUniqueByTypeIgnoreCase(type: String): Mono<PaymentType>
}