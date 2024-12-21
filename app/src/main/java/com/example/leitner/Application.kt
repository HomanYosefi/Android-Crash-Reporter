package com.example.leitner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    @Inject
    lateinit var crashHandler: UserConfirmedCrashHandler // یا AutomaticCrashHandler

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
    }
}