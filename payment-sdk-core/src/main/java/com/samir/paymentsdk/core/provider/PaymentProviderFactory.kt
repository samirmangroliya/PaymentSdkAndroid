package com.samir.paymentsdk.core.provider

interface PaymentProviderFactory {

    val providerId: PaymentProviderId

    fun create(): PaymentProvider
}