package com.samir.paymentapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.samir.paymentapp.screens.PaymentMainScreen
import com.samir.paymentapp.ui.theme.PaymentSdkTheme
import com.samir.paymentsdk.PaymentSdk
import com.samir.paymentsdk.PaymentSdkConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initializePayment()

        setContent {
            PaymentSdkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PaymentMainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    fun initializePayment() {
        PaymentSdk.initialize(
            applicationContext,
            PaymentSdkConfig(
                environment = PaymentSdkConfig.Environment.SANDBOX,
                publishableKey = "pk_test_demo",
                apiBaseUrl = "https://api.example.com/",
                merchantName = "Demo Merchant"
            )
        )
    }
}

