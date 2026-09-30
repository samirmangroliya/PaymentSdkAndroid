package com.samir.paymentsdk

class PaymentException(
    val code: String,
    override val message: String,
    val retryable: Boolean = false,
    cause: Throwable? = null
) : Exception(message, cause)
