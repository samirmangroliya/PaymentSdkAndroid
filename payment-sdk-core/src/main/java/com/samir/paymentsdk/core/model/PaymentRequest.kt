package com.samir.paymentsdk.core.model

data class PaymentRequest(
    val amountMinor: Long,
    val currency: String,
    val orderId: String,
    val customerReference: String? = null,
    val description: String? = null
)