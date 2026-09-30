package com.samir.paymentsdk

/**
 * Configuration supplied by the merchant application.
 *
 * Never put a secret API key, private key, card PAN, CVV, or encryption key
 * into this Android SDK configuration.
 */
data class PaymentSdkConfig(
    val environment: Environment,
    val publishableKey: String,
    val apiBaseUrl: String,
    val merchantName: String
) {
    enum class Environment {
        SANDBOX,
        PRODUCTION
    }
}
