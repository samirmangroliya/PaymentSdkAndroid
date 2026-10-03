package com.samir.paymentsdk.core.provider

@JvmInline
value class PaymentProviderId(
    val value: String
) {
    init {
        require(value.isNotBlank()) {
            "Payment provider ID must not be blank"
        }
    }

    override fun toString(): String = value
}