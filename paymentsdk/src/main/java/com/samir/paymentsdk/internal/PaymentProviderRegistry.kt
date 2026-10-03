package com.samir.paymentsdk.internal

import com.samir.paymentsdk.core.provider.PaymentProvider
import com.samir.paymentsdk.core.provider.PaymentProviderFactory
import com.samir.paymentsdk.core.provider.PaymentProviderId

internal class PaymentProviderRegistry(
    factories: List<PaymentProviderFactory>
) {

    private val factoriesById =
        factories.associateBy { it.providerId }

    fun createProvider(
        providerId: PaymentProviderId
    ): PaymentProvider {
        return factoriesById[providerId]
            ?.create()
            ?: error(
                "No payment provider registered for providerId='$providerId'"
            )
    }
}