package com.samir.paymentsdk

import android.content.Context
import com.samir.paymentsdk.core.model.PaymentFailure
import com.samir.paymentsdk.core.model.PaymentRequest
import com.samir.paymentsdk.core.model.PaymentSession
import com.samir.paymentsdk.core.payment.PaymentStatus
import com.samir.paymentsdk.core.provider.PaymentProvider
import com.samir.paymentsdk.internal.PaymentProviderRegistryFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentSdk internal constructor(
    private val applicationContext: Context,
    private val config: PaymentSdkConfig,
    private val provider: PaymentProvider
) {

    private val scope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO
        )

    fun startPayment(
        request: PaymentRequest,
        callback: (PaymentResult) -> Unit
    ) {

        validate(request)

        scope.launch {

            val result = runCatching {
                provider.createPayment(request)
            }.fold(
                onSuccess = { session ->
                    session.toPaymentResult()
                },
                onFailure = { error ->
                    PaymentResult.Failed(
                        PaymentSession(
                            paymentId = "",
                            orderId = request.orderId,
                            status = PaymentStatus.FAILED,
                            amountMinor = request.amountMinor,
                            currency = request.currency,
                            failure = PaymentFailure(
                                code = "SDK_ERROR",
                                message = error.message
                                    ?: "Payment failed",
                                retryable = true
                            )
                        )
                    )
                }
            )

            withContext(Dispatchers.Main.immediate) {
                callback(result)
            }
        }
    }

    private fun validate(
        request: PaymentRequest
    ) {

        require(request.amountMinor > 0L) {
            "amountMinor must be greater than zero"
        }

        require(request.currency.length == 3) {
            "currency must be a 3-letter ISO-4217 code"
        }

        require(request.orderId.isNotBlank()) {
            "orderId must not be blank"
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: PaymentSdk? = null

        fun initialize(
            context: Context,
            config: PaymentSdkConfig
        ): PaymentSdk {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: run {

                    val registry = PaymentProviderRegistryFactory.create()
                    PaymentSdk(
                        applicationContext = context.applicationContext,
                        config = config,
                        provider = registry.createProvider(config.provider)
                    ).also {
                        INSTANCE = it
                    }
                }
            }
        }

        val instance: PaymentSdk
            get() = INSTANCE
                ?: error(
                    "PaymentSdk.initialize() must be called first"
                )
    }
}

private fun PaymentSession.toPaymentResult(): PaymentResult {
    return when (status) {

        PaymentStatus.SUCCEEDED ->
            PaymentResult.Success(this)

        PaymentStatus.REQUIRES_ACTION ->
            PaymentResult.RequiresAction(this)

        PaymentStatus.PROCESSING ->
            PaymentResult.Processing(this)

        PaymentStatus.FAILED ->
            PaymentResult.Failed(this)

        PaymentStatus.CANCELLED ->
            PaymentResult.Cancelled(this)

        PaymentStatus.CREATED ->
            PaymentResult.Processing(this)
    }
}