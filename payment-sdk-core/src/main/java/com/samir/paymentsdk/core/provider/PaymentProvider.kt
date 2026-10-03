package com.samir.paymentsdk.core.provider

import com.samir.paymentsdk.core.model.PaymentRequest
import com.samir.paymentsdk.core.model.PaymentSession

interface PaymentProvider {

    suspend fun createPayment(
        request: PaymentRequest
    ): PaymentSession

    suspend fun getPaymentStatus(
        paymentId: String
    ): PaymentSession

    suspend fun cancelPayment(
        paymentId: String
    ): PaymentSession
}