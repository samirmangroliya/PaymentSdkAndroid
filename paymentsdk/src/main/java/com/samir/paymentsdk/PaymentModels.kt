package com.samir.paymentsdk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentRequest(
    val amountMinor: Long,
    val currency: String,
    val orderId: String,
    val customerReference: String? = null,
    val description: String? = null
) : Parcelable

sealed interface PaymentResult {
    data class Success(
        val paymentId: String,
        val orderId: String,
        val amountMinor: Long,
        val currency: String
    ) : PaymentResult

    data class Failed(
        val code: String,
        val message: String,
        val retryable: Boolean
    ) : PaymentResult

    data object Cancelled : PaymentResult
}
