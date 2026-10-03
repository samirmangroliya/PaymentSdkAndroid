package com.samir.paymentsdk.provider.demo

import com.samir.paymentsdk.core.model.PaymentRequest
import com.samir.paymentsdk.core.model.PaymentSession
import com.samir.paymentsdk.core.payment.PaymentStatus
import com.samir.paymentsdk.core.provider.PaymentProvider
import java.util.UUID

internal class DemoPaymentProvider : PaymentProvider {

    override suspend fun createPayment(
        request: PaymentRequest
    ): PaymentSession {
        return PaymentSession(
            paymentId = "demo_${UUID.randomUUID()}",
            orderId = request.orderId,
            status = PaymentStatus.SUCCEEDED,
            amountMinor = request.amountMinor,
            currency = request.currency.uppercase()
        )
    }

    override suspend fun getPaymentStatus(
        paymentId: String
    ): PaymentSession {
        return PaymentSession(
            paymentId = paymentId,
            orderId = "",
            status = PaymentStatus.SUCCEEDED,
            amountMinor = 0L,
            currency = "INR"
        )
    }

    override suspend fun cancelPayment(
        paymentId: String
    ): PaymentSession {
        return PaymentSession(
            paymentId = paymentId,
            orderId = "",
            status = PaymentStatus.CANCELLED,
            amountMinor = 0L,
            currency = "INR"
        )
    }
}