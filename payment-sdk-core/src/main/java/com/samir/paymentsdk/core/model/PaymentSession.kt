package com.samir.paymentsdk.core.model

import com.samir.paymentsdk.core.payment.PaymentAction
import com.samir.paymentsdk.core.payment.PaymentStatus

data class PaymentSession(
    val paymentId: String,
    val orderId: String,
    val status: PaymentStatus,
    val amountMinor: Long,
    val currency: String,
    val action: PaymentAction? = null,
    val failure: PaymentFailure? = null
)