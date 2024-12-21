package com.example.leitner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val errorReport = intent.getStringExtra("error_report") ?: "گزارش خطا در دسترس نیست"
        val userEmail = intent.getStringExtra("user_email")

        setContent {
            CrashScreen(
                onSendReport = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("developer@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "گزارش خطای برنامه")
                        putExtra(Intent.EXTRA_TEXT, errorReport)
                    }

                    try {
                        startActivity(Intent.createChooser(intent, "ارسال گزارش خطا"))
                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            "برنامه ایمیل یافت نشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onRestartApp = {
                    val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    startActivity(intent)
                    finish()
                },
                onClose = {
                    finishAffinity()
                }
            )
        }
    }
}

