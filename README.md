# MayaAssistant

MayaAssistant is a clean, modern Android personal assistant application developed in Kotlin using Jetpack Compose and Material Design 3.

## ✨ Features

- **Conversational Chat Interface:** Clean, mobile-friendly chat screen with assistant and user message bubbles, timestamps, and copy-to-clipboard functionality.
- **Offline Assistant Intelligence (`AssistantCore`):**
  - Instant answers to common questions and identity queries.
  - Device battery level and charging status reporting.
  - Real-time date and time inquiries.
  - Math calculations (arithmetic, percentages, expressions).
  - Daily productivity advice and time management strategies.
  - Fun facts, trivia, and humor.
  - Uplifting quotes and motivation.
- **Voice Support:**
  - **Text-to-Speech (TTS):** Tap the speaker icon on any message to hear Maya speak, or enable the auto-speech toggle in the top bar.
  - **Speech Recognition:** Tap the microphone button to dictate questions hands-free (requests audio recording permission only when voice input is engaged).
- **Quick Suggestion Chips:** Fast prompt chips for new queries and quick actions.
- **Modern Adaptive App Icon:** Custom electric violet and indigo emblem icon designed for modern Android launchers.

## 🛠️ Build Requirements

- **Language:** Kotlin
- **Build System:** Gradle with Kotlin DSL (`build.gradle.kts`)
- **JDK:** Java 17+
- **Minimum SDK:** Android 8.0 (API Level 26)
- **Target SDK:** API Level 36

## 🚀 Building the Project

### Local Build (Command Line)

To build the debug APK locally:

```bash
# Grant execution permissions to Gradle wrapper (Linux/macOS)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug
```

The compiled APK will be output to:
```
app/build/outputs/apk/debug/app-debug.apk
```

### GitHub Actions CI/CD

This repository includes a fully configured, production-ready workflow in:
`.github/workflows/build-apk.yml`

Whenever code is pushed to GitHub, the workflow automatically:
1. Sets up the JDK 17 environment.
2. Restores the debug signing configuration.
3. Builds the debug APK with `./gradlew assembleDebug`.
4. Verifies the generated APK in `app/build/outputs/apk/debug/`.
5. Uploads `app-debug.apk` as a downloadable GitHub Actions artifact (`MayaAssistant-Debug-APK`).

## 📱 Permissions

- `android.permission.INTERNET`: Required for network connectivity.
- `android.permission.RECORD_AUDIO`: Optional dangerous permission requested at runtime strictly when the user activates the microphone for voice input.
