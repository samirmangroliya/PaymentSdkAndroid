package com.samir.paymentsdk

internal class DefaultPaymentGateway(
    config: PaymentSdkConfig
) : PaymentGateway {
    private val backend = PaymentBackendClient(config)

    override suspend fun createPayment(request: PaymentRequest): PaymentResult {
        return backend.createPayment(request)
    }
}
