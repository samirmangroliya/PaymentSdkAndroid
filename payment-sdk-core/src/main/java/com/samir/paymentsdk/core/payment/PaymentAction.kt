package com.samir.paymentsdk.core.payment

sealed interface PaymentAction {

    data class Redirect(
        val url: String
    ) : PaymentAction

    data class ThreeDSecure(
        val url: String
    ) : PaymentAction

    data class ExternalApp(
        val packageName: String?,
        val intentUri: String
    ) : PaymentAction
}