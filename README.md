# VineHeart - WearOS Heart Rate Monitor

**VineHeart** is a companion WearOS application that bridges the gap between your physical state and your music experience. Designed to work seamlessly with **Utavine** (a mood-based music streaming app), VineHeart continuously monitors your heart rate and syncs it to the cloud, enabling Utavine to intelligently curate and play music that matches your current mood and energy levels.

By analyzing real-time heart rate data, Utavine can determine whether you're calm, energized, stressed, or relaxed, and adapt your music experience accordingly. VineHeart acts as the sensor layer in this innovative music ecosystem, making your listening experience truly personalized and responsive to your physiological state.

## 📱 Features

- **Real-Time Heart Rate Monitoring**: Utilizes WearOS device sensors to continuously track heart rate
- **Automatic Cloud Sync**: Automatically sends heart rate data to Supabase every 60 seconds
- **Manual Sync**: Option to manually send current heart rate data on-demand
- **Firebase Authentication**: Secure user authentication using Firebase Auth
- **Modern UI**: Built with Jetpack Compose for WearOS with a clean, intuitive interface
- **Persistent Data Storage**: All heart rate readings are stored with timestamps and user IDs

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose for WearOS
- **Authentication**: Firebase Authentication
- **Database**: Supabase
- **HTTP Client**: OkHttp
- **Minimum SDK**: 33 (Android 13)
- **Target SDK**: 35
- **Build System**: Gradle with Kotlin DSL

## 📋 Prerequisites

Before you begin, ensure you have the following:

- Android Studio (latest version recommended)
- WearOS device or emulator with API level 33+
- Firebase project with Authentication enabled
- Supabase project with a configured table for heart rate data
- Google Play Services installed on your WearOS device

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/hsoyal-dot/VineHeart.git
cd VineHeart
```

### 2. Firebase Setup

1. Create a new project in the [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your Firebase project with package name: `com.example.vineheart`
3. Download the `google-services.json` file
4. Place the `google-services.json` file in the `app/` directory
5. Enable Email/Password authentication in Firebase Console

### 3. Supabase Setup

1. Create a new project in [Supabase](https://supabase.com/)
2. Create a table for heart rate data with the following schema:

```sql
CREATE TABLE heart_rate (
  id BIGSERIAL PRIMARY KEY,
  user_id TEXT NOT NULL,
  device_id TEXT,
  timestamp TIMESTAMPTZ NOT NULL,
  bpm FLOAT NOT NULL
);
```

3. Get your Supabase URL and API Key from Project Settings → API

### 4. Configure Local Properties

Create a `local.properties` file in the root directory (if it doesn't exist) and add:

```properties
SUPABASE_URL=https://your-project.supabase.co/rest/v1/heart_rate
SUPABASE_API_KEY=your-supabase-anon-key
```

> **Note**: The `local.properties` file is already in `.gitignore` to protect your sensitive credentials.

### 5. Build and Run

1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Connect your WearOS device or start an emulator
4. Run the application

## ☁ How to Use

1. **Launch the App**: Open VineHeart on your WearOS device
2. **Authenticate**: Sign in with your email and password (or create a new account)
3. **Monitor Heart Rate**: The app will automatically start monitoring your heart rate
4. **Automatic Sync**: Heart rate data is automatically sent to Supabase every 60 seconds
5. **Manual Sync**: Tap the "Send" button to immediately sync the current reading
6. **Logout**: Use the logout option to sign out of your account

## 🏗️ Project Structure

```
app/src/main/java/com/example/wearos_heartrate/
├── MainActivity.kt                 # Main entry point and activity
├── auth/
│   └── AuthScreen.kt              # Firebase authentication UI
├── sensor/
│   └── HeartRateSensorManager.kt  # Heart rate sensor management
└── ui/
    ├── HeartRateScreen.kt         # Main heart rate display screen
    └── theme/                     # App theming and styling
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## 🔐 Permissions

The following permissions are required:

- `BODY_SENSORS`: To access heart rate sensor data
- `INTERNET`: For cloud synchronization
- `WAKE_LOCK`: To keep the app running during heart rate monitoring

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

## 🔒 Security

- All sensitive credentials are stored in `local.properties` and never committed to version control
- Firebase Authentication secures user access
- Supabase API key is used for authenticated requests
- User data is associated with Firebase UID for privacy

## 📝 Configuration

### Adjust Sync Interval

To change the automatic sync interval (default: 60 seconds), modify the `sendAfter` variable in `HeartRateSensorManager.kt`:

```kotlin
private var sendAfter = 60_000L // milliseconds
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📲 Checkout Utavine - A Music Streaming App

- [GitHub Repo](https://github.com/hsoyal-dot/Utavine)

## 👤 Author

**Hemraj Soyal**

- GitHub: [@hsoyal-dot](https://github.com/hsoyal-dot)

## 🙏 Acknowledgments

- Built with [Jetpack Compose for WearOS](https://developer.android.com/jetpack/compose/wearos)
- Authentication powered by [Firebase](https://firebase.google.com/)
- Backend database by [Supabase](https://supabase.com/)

## 📨 Support

If you have any questions or run into issues, please open an issue on GitHub.

---

Made with dots ⚫️ for WearOS
