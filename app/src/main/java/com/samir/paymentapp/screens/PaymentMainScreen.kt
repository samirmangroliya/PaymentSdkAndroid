package com.samir.paymentapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samir.paymentapp.ui.theme.PaymentSdkTheme
import com.samir.paymentsdk.PaymentRequest
import com.samir.paymentsdk.PaymentResult
import com.samir.paymentsdk.PaymentSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun PaymentMainScreen(modifier: Modifier = Modifier) {

    var textStatus by remember { mutableStateOf("Click On Pay Now") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 16.dp)
    ) {
        Text(
            text = textStatus,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp, bottom = 16.dp),
            thickness = 2.dp,
            color = Color.Blue
        )

        Button(onClick = {
            pay {
                textStatus = it
            }
        }) {
            Text("Pay Now")
        }
    }

}


private fun pay(onChangeText: (String) -> Unit) {
    onChangeText("Processing...")
    val customUiScope = CoroutineScope(Dispatchers.Main + Job())
    PaymentSdk.instance.startPayment(
        PaymentRequest(
            amountMinor = 49900, // ₹499.00
            currency = "INR",
            orderId = "ORDER-10001",
            description = "Demo order"
        )
    ) { result ->

        customUiScope.launch {
            when (result) {
                is PaymentResult.Success ->
                    onChangeText("SUCCESS\nPayment ID: ${result.paymentId}")

                is PaymentResult.Failed ->
                    onChangeText("FAILED\n${result.code}: ${result.message}")

                PaymentResult.Cancelled ->
                    onChangeText("CANCELLED")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaymentSdkTheme {
        PaymentMainScreen()
    }
}