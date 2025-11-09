# StarLauncher

Custom Android Launcher built with Kotlin and Jetpack Compose.

## Features

- 🎨 Custom wallpaper support
- ⏰ Dynamic clock with time-based colors
- 📱 Favorite apps bar (up to 5 apps)
- 🔍 Real-time app search
- 🔤 Alphabetically sorted app list
- ✨ Glassmorphism UI design

## Build Requirements

- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34
- Gradle 8.7

## Build Instructions

```bash
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

## Installation

1. Install the APK on your device
2. Go to Settings → Apps → Default apps → Home app
3. Select StarLauncher

## Project Structure

```
src/com/lyciv/star/
├── MainActivity.kt           # HOME activity
├── SettingsActivity.kt       # Settings/drawer icon
├── ui/
│   ├── StarHomeScreen.kt
│   ├── components/
│   │   ├── ClockPanel.kt
│   │   ├── FavoriteBar.kt
│   │   ├── AppList.kt
│   │   └── SearchBar.kt
│   └── theme/
│       ├── Theme.kt
│       ├── Color.kt
│       └── Type.kt
├── data/
│   ├── AppModel.kt
│   ├── AppRepository.kt
│   └── FavoriteDao.kt
└── utils/
    ├── WallpaperUtils.kt
    └── TimeUtils.kt
```

## Permissions

- `QUERY_ALL_PACKAGES` - List installed apps
- `READ_MEDIA_IMAGES` (API 33+) - Select wallpaper
- `READ_EXTERNAL_STORAGE` (API ≤ 32) - Select wallpaper

## License

MIT License

## Notes

- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)
- Blur effects available on API 31+ (Android 12+)
