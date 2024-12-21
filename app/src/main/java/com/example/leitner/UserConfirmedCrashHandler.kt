package com.example.leitner
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserConfirmedCrashHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) : Thread.UncaughtExceptionHandler {

    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val errorReport = generateCrashReport(throwable)

            val intent = Intent(context, CrashActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra("error_report", errorReport)
            }

            context.startActivity(intent)
            Process.killProcess(Process.myPid())
            System.exit(1)

        } catch (e: Exception) {
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun generateCrashReport(throwable: Throwable): String {
        return buildString {
            append("زمان: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}\n\n")

            append("اطلاعات کاربر:\n")

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