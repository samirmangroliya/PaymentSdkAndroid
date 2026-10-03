package com.samir.paymentsdk.provider.demo

import com.samir.paymentsdk.core.provider.PaymentProviderFactory
import com.samir.paymentsdk.core.provider.PaymentProviderRegistrar

class DemoPaymentProviderRegistrar : PaymentProviderRegistrar {

    override fun factories(): List<PaymentProviderFactory> {
        return listOf(
            DemoPaymentProviderFactory()
        )
    }
}