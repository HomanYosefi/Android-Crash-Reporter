package com.example.leitner

import android.app.blob.BlobStoreManager.Session
import java.util.Properties
import javax.inject.Inject
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
class EmailSender @Inject constructor() {

    private val properties = Properties().apply {
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.port", "587")
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true")
    }

    private val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(EMAIL, PASSWORD)
        }
    })

    fun sendCrashReport(errorReport: String) {
        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(EMAIL))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(DEVELOPER_EMAIL))
                subject = "گزارش خطای برنامه"
                setText(errorReport)
            }
            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val EMAIL = "your-email@gmail.com"
        private const val PASSWORD = "your-app-password"
        private const val DEVELOPER_EMAIL = "developer@example.com"
    }
}