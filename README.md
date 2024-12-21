# Android Crash Reporter 🛡️

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/Kotlin-1.9.0-blue.svg?style=flat&logo=kotlin" alt="Kotlin"></a>
  <a href="https://android.com"><img src="https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android" alt="Android"></a>
  <a href="https://github.com/alirezaRmz96/Android-Crash-Reporter/blob/main/LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License"></a>
  <a href="https://github.com/alirezaRmz96/Android-Crash-Reporter/stargazers"><img src="https://img.shields.io/github/stars/alirezaRmz96/Android-Crash-Reporter.svg?style=flat" alt="Stars"></a>
</p>

A modern and flexible solution for managing and reporting crashes in Android applications, featuring both automatic and user-confirmed reporting methods.

## ✨ Main Features

### 📱 Reporting Methods
- **Automatic Reporting**: Send crash reports directly to the developer's email.
- **User-Confirmed Reporting**: Display a crash report screen to the user for confirmation before sending.

### 📊 Comprehensive Information
- Device details
- App version
- Crash details (Stack Trace)
- User information (if logged in)
- Timestamp of the crash

### 🎨 Modern UI
- Built with Jetpack Compose
- Customizable
- Smooth user experience

## 🔗 Use Cases

- Development and testing
- Monitoring production versions
- Gathering user feedback
- Bug tracking
- Quality assurance

## 🚀 Quick Start

### Prerequisites

```groovy
// build.gradle (Project)
buildscript {
    dependencies {
        classpath "com.google.dagger:hilt-android-gradle-plugin:2.44"
    }
}

// build.gradle (Module)
dependencies {
    implementation "com.google.dagger:hilt-android:2.44"
    kapt "com.google.dagger:hilt-android-compiler:2.44"
    
    // For automatic reporting
    implementation "com.sun.mail:android-mail:1.6.7"
    implementation "com.sun.mail:android-activation:1.6.7"
}
```

## 🖋️ Implementation

### 1⃣ Configure Application Class

```kotlin
@HiltAndroidApp
class MyApp : Application() {
    
    @Inject
    lateinit var crashHandler: UserConfirmedCrashHandler // or AutomaticCrashHandler
    
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
    }
}
```

### 2⃣ Setup CrashActivity

```kotlin
@AndroidEntryPoint
class CrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val errorReport = intent.getStringExtra("error_report") ?: "Error report not available"
        
        setContent {
            AppTheme {
                CrashScreen(
                    onSendReport = { /* Code to send the report */ },
                    onRestartApp = { /* Code to restart the app */ },
                    onClose = { finishAffinity() }
                )
            }
        }
    }
}
```

## 🔧 Usage Examples

### Method 1: Automatic Reporting

```kotlin
// In Application class
@Inject
lateinit var automaticCrashHandler: AutomaticCrashHandler

// Set handler
Thread.setDefaultUncaughtExceptionHandler(automaticCrashHandler)
```

### Method 2: User-Confirmed Reporting

```kotlin
// In Application class
@Inject
lateinit var userConfirmedCrashHandler: UserConfirmedCrashHandler

// Set handler
Thread.setDefaultUncaughtExceptionHandler(userConfirmedCrashHandler)
```

## 🔧 Configuration

### Android Manifest

```xml
<manifest>
    <uses-permission android:name="android.permission.INTERNET" />
    
    <application
        android:name=".MyApp"
        android:label="@string/app_name"
        ...>
        
        <activity
            android:name=".ui.CrashActivity"
            android:exported="false"
            android:theme="@style/Theme.YourApp" />
        
    </application>
</manifest>
```

### Email Settings (For Automatic Reporting)

```kotlin
companion object {
    private const val EMAIL = "your-email@gmail.com"
    private const val PASSWORD = "your-app-password"
    private const val DEVELOPER_EMAIL = "developer@example.com"
}
```

## 🔖 Key Points

1. For automatic reporting:
   - Enable Two-Factor Authentication in Gmail.
   - Create an App Password.
   - Use the App Password in your settings.

2. To enhance security:
   - Store sensitive data in a separate file.
   - Use ProGuard for production builds.
   - Filter out sensitive user data.

## 🛠️ Technologies Used

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For modern UI.
- **Hilt**: Dependency injection.
- **JavaMail API**: For email sending.

## 💖 Support

If this project was helpful, please:
- Star the repository ⭐️
- Share it with others
- Contribute to its development

## 📲 Contact Us

- Email: bit.azmayesh@gmail.com
- LinkedIn: [Homan Yosefi](https://linkedin.com/in/homan-yosefi)

---

<p align="center">
  Made with ❤️ by Homan Yosefi
</p>
