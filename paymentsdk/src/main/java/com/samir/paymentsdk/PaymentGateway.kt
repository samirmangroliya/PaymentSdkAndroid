package com.samir.paymentsdk

/**
 * Provider-neutral gateway abstraction.
 *
 * A real implementation should call your backend and/or a PSP SDK.
 * The Android client must not receive or store merchant secret credentials.
 */
interface PaymentGateway {
    suspend fun createPayment(request: PaymentRequest): PaymentResult
}
