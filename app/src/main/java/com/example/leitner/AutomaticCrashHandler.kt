package com.example.leitner


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AutomaticCrashHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val emailSender: EmailSender
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val errorReport = generateCrashReport(throwable)
            emailSender.sendCrashReport(errorReport)
        } finally {
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun generateCrashReport(throwable: Throwable): String {
        return buildString {
            append("زمان: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}\n\n")

            append("مشخصات دستگاه:\n")
            append("سازنده: ${Build.MANUFACTURER}\n")
            append("مدل: ${Build.MODEL}\n")
            append("نسخه اندروید: ${Build.VERSION.RELEASE}\n")
            append("SDK: ${Build.VERSION.SDK_INT}\n")

            append("\nاطلاعات برنامه:\n")
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                append("نسخه: ${packageInfo.versionName}\n")
                append("کد نسخه: ${packageInfo.longVersionCode}\n")
            } catch (e: Exception) {
                append("خطا در دریافت اطلاعات برنامه\n")
            }

            append("\nجزئیات خطا:\n")
            append(Log.getStackTraceString(throwable))
        }
    }
}
