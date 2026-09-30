package com.samir.paymentsdk

import android.content.Context
import com.samir.paymentsdk.DefaultPaymentGateway
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Main public entry point.
 *
 * Usage:
 * PaymentSdk.initialize(context, config)
 * PaymentSdk.instance.startPayment(request) { result -> ... }
 */
class PaymentSdk private constructor(
    private val applicationContext: Context,
    private val config: PaymentSdkConfig,
    private val gateway: PaymentGateway
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun startPayment(
        request: PaymentRequest,
        callback: (PaymentResult) -> Unit
    ) {
        validate(request)

        scope.launch {
            val result = runCatching {
                gateway.createPayment(request)
            }.getOrElse { throwable ->
                PaymentResult.Failed(
                    code = "SDK_ERROR",
                    message = throwable.message ?: "Payment failed.",
                    retryable = true
                )
            }

            callback(result)
        }
    }

    private fun validate(request: PaymentRequest) {
        require(request.amountMinor > 0) {
            "amountMinor must be greater than zero."
        }
        require(request.currency.length == 3) {
            "currency must be a 3-letter ISO-4217 code."
        }
        require(request.orderId.isNotBlank()) {
            "orderId must not be blank."
        }
        require(config.publishableKey.isNotBlank()) {
            "publishableKey must not be blank."
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PaymentSdk? = null

        fun initialize(
            context: Context,
            config: PaymentSdkConfig,
            gateway: PaymentGateway = DefaultPaymentGateway(config)
        ): PaymentSdk {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PaymentSdk(
                    context.applicationContext,
                    config,
                    gateway
                ).also { INSTANCE = it }
            }
        }

        val instance: PaymentSdk
            get() = INSTANCE
                ?: error("PaymentSdk.initialize(...) must be called first.")
    }
}
