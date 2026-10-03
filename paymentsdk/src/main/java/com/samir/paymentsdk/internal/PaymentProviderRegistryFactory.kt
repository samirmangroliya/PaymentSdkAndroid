package com.samir.paymentsdk.internal

import com.samir.paymentsdk.provider.demo.DemoPaymentProviderRegistrar

internal object PaymentProviderRegistryFactory {

    fun create(): PaymentProviderRegistry {
        val registrars = listOf(
            DemoPaymentProviderRegistrar()
        )

        val factories = registrars
            .flatMap { it.factories() }

        return PaymentProviderRegistry(factories)
    }
}