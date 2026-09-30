package com.samir.paymentsdk

import kotlinx.coroutines.delay
import java.util.UUID

/**
 * Demo backend client.
 *
 * Replace this class with HTTPS calls to your own payment backend.
 * In production, the backend should create/confirm the payment with the PSP.
 */
internal class PaymentBackendClient(
    private val config: PaymentSdkConfig
) {
    suspend fun createPayment(request: PaymentRequest): PaymentResult {
        delay(400)

        // Demo only. Never generate real payment authorization on-device.
        if (request.amountMinor <= 0) {
            return PaymentResult.Failed(
                code = "INVALID_AMOUNT",
                message = "Amount must be greater than zero.",
                retryable = false
            )
        }

        if (request.currency.length != 3) {
            return PaymentResult.Failed(
                code = "INVALID_CURRENCY",
                message = "Currency must be an ISO-4217 3-letter code.",
                retryable = false
            )
        }

        return PaymentResult.Success(
            paymentId = "pay_${UUID.randomUUID()}",
            orderId = request.orderId,
            amountMinor = request.amountMinor,
            currency = request.currency.uppercase()
        )
    }
}
