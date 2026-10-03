package com.samir.paymentsdk

import com.samir.paymentsdk.core.model.PaymentSession

sealed interface PaymentResult {

    data class Success(
        val session: PaymentSession
    ) : PaymentResult

    data class RequiresAction(
        val session: PaymentSession
    ) : PaymentResult

    data class Processing(
        val session: PaymentSession
    ) : PaymentResult

    data class Failed(
        val session: PaymentSession
    ) : PaymentResult

    data class Cancelled(
        val session: PaymentSession
    ) : PaymentResult
}