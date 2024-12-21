package com.example.leitner
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CrashScreen(
    onSendReport: () -> Unit,
    onRestartApp: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "متأسفانه خطایی رخ داده است",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "برای کمک به بهبود برنامه، لطفاً گزارش خطا را ارسال کنید",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSendReport,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("ارسال گزارش خطا")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRestartApp,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("راه‌اندازی مجدد برنامه")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("بستن")
        }
    }
}