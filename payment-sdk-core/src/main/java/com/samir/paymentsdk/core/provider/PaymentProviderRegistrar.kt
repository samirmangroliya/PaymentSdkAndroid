package com.samir.paymentsdk.core.provider

interface PaymentProviderRegistrar {

    fun factories(): List<PaymentProviderFactory>
}