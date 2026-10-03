package com.samir.paymentsdk.provider.demo

import com.samir.paymentsdk.core.provider.PaymentProvider
import com.samir.paymentsdk.core.provider.PaymentProviderFactory
import com.samir.paymentsdk.core.provider.PaymentProviderId

class DemoPaymentProviderFactory : PaymentProviderFactory {

    override val providerId = PaymentProviderId("demo")

    override fun create(): PaymentProvider {
        return DemoPaymentProvider()
    }
}