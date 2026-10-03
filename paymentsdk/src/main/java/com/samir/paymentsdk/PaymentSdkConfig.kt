package com.samir.paymentsdk

import com.samir.paymentsdk.core.provider.PaymentProviderId

data class PaymentSdkConfig(
    val environment: Environment,
    val publishableKey: String,
    val apiBaseUrl: String,
    val merchantName: String,
    val provider: PaymentProviderId
) {

    enum class Environment {
        SANDBOX,
        PRODUCTION
    }
}