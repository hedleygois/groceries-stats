package com.hedley.groceriesstats.paymenttype

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger

@Table("payment_types")
data class PaymentType(@Id val id: BigInteger? = null, val type: String)
