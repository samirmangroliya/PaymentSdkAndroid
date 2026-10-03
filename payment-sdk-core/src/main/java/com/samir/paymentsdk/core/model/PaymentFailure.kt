package com.samir.paymentsdk.core.model

data class PaymentFailure(
    val code: String,
    val message: String,
    val retryable: Boolean
)