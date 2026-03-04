# SprintAlert - Android Sprint Training App

A lightweight, offline-first Android app for accurate sprint training on short distances (50m-400m+).

## Features
- ✅ Real-time GPS distance tracking using Haversine formula
- ✅ Automatic sprint timing with millisecond precision
- ✅ Powerful alerts (sound, vibration, notifications)
- ✅ Background operation with foreground GPS service
- ✅ Local SQLite database for offline history
- ✅ Dark theme optimized for outdoor visibility
- ✅ Lightweight build (<30MB)
- ✅ 100% offline capability

## Quick Start

### Installation on Android Device

#### Option 1: Direct Installation (After Building)
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

#### Option 2: Via Android Studio
1. Open the project in Android Studio
2. Click **Run > Run 'app'**
3. Select your device

### System Requirements
- **Android**: 8.0+ (API 26+)
- **Permissions**: Fine Location, Background Location, Vibrate, Foreground Service
- **Storage**: ~30MB

## Building from Source

### Prerequisites
- Java 11+: `brew install openjdk@11` (macOS)
- Gradle (included via wrapper)
- Android SDK (API 34, build-tools 34.0.0)

### Build Instructions

#### macOS/Linux:
```bash
# 1. Clone the repo (or navigate to your project)
cd SprintAlert

# 2. Set Java home (if not set)
export JAVA_HOME=/opt/homebrew/opt/openjdk@11

# 3. Build Debug APK
./gradlew assembleDebug

# 4. Build Release APK (optimized)
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

#### Windows:
```bash
# 1. Set Java home (if not set)
set JAVA_HOME=C:\Program Files\Java\jdk-11

# 2. Build Release APK
gradlew.bat assembleRelease

# Output: app/build/outputs/apk/release/app-release.apk
```

### Android Studio Installation

If you have Android Studio installed:

1. **Install Android SDK**:
   - Open Android Studio
   - Go to: **Settings > SDK Manager**
   - Install: **Platform 34** and **Build Tools 34.0.0**

2. **Build the APK**:
   - File > Open > Select `SprintAlert` folder
   - Build > Build Bundle(s)/APK(s) > Build APK(s)
   - APK appears in: `app/build/outputs/apk/release/`

## Architecture

### Project Structure
```
SprintAlert/
├── app/src/main/java/com/sprintalert/
│   ├── MainActivity.kt              - Main UI & controls
│   ├── SprintViewModel.kt           - MVVM state management
│   ├── RecordAdapter.kt             - History list adapter
│   ├── db/
│   │   ├── SprintAlertDatabase.kt  - Room database
│   │   ├── SprintRecord.kt         - Data model
│   │   └── SprintRecordDao.kt      - DAO queries
│   ├── service/
│   │   └── GPSTrackingService.kt   - Background service
│   └── utils/
│       ├── GPSManager.kt            - GPS location updates
│       ├── DistanceCalculator.kt    - Haversine formula
│       ├── AlertManager.kt          - Sound/vibration
│       └── FormatUtils.kt           - Time formatting
├── app/src/main/res/
│   ├── layout/                      - UI layouts
│   ├── drawable/                    - Icons & shapes
│   └── values/                      - Strings, colors, themes
└── build.gradle.kts                 - Dependencies
```

### Key Technologies
- **Language**: Kotlin
- **UI**: Material Design
- **Database**: Room + SQLite
- **Location**: Google Play Services (Fused Location Provider)
- **Async**: Coroutines + Flow
- **Architecture**: MVVM

## How It Works

1. **GPS Tracking**: Uses Fused Location Provider for high-accuracy GPS
2. **Distance Calculation**: Haversine formula for real-world meter accuracy
3. **Sprint Detection**: Compares current distance to target
4. **Alerts**: Sound + vibration when target is reached
5. **Storage**: All data saved locally in SQLite (offline-capable)
6. **Background**: Foreground service keeps GPS running when app is minimized

## Permissions

Required permissions in `AndroidManifest.xml`:
- `ACCESS_FINE_LOCATION` - Precise GPS
- `ACCESS_COARSE_LOCATION` - Network-based location
- `ACCESS_BACKGROUND_LOCATION` - GPS when screen is off
- `VIBRATE` - Vibration alerts
- `FOREGROUND_SERVICE_LOCATION` - Background GPS service

## Testing Checklist

- [ ] Location permissions granted
- [ ] GPS initialization completes (watch GPS Ready status)
- [ ] Timer starts/stops correctly
- [ ] Distance calculation accurate (compared to known distance)
- [ ] Alerts trigger at target distance
- [ ] Background service maintains GPS with screen off
- [ ] History saves and loads correctly
- [ ] App works offline without internet

## Performance

- **APK Size**: ~25MB (release build)
- **Target API**: 34
- **Min API**: 26 (Android 8.0+)
- **Battery**: Optimized GPS updates (500ms intervals)
- **Memory**: <50MB typical usage

## Configuration

### Distance Targets
Set custom distance in the distance input field:
- 50m, 100m, 200m, 400m
- Or any custom value

### Customization
Future versions will support:
- Alert sound selection
- Vibration intensity
- Countdown timer (3s/5s)
- 10-meter interval beeps

## Troubleshooting

### "GPS Waiting..." message
- Enable Location permission
- Turn on device GPS
- Wait 20-30 seconds for lock

### Inaccurate distance
- Move in straight line
- Avoid buildings/dense areas
- Use open field for testing

### App crashes
- Clear app data: **Settings > Apps > SprintAlert > Storage > Clear Data**
- Ensure Android 8.0+
- Grant all permissions

### Build fails
```bash
# Clean build
./gradlew clean build

# Clear cache
./gradlew --stop
rm -rf .gradle
./gradlew assembleRelease
```

## Development

### Running Tests
```bash
./gradlew test
```

### Code Analysis
```bash
./gradlew lint
```

### Generate Code Coverage
```bash
./gradlew jacocoTestReport
```

## Release Build Process

1. **Update version**:
   ```kotlin
   // app/build.gradle.kts
   versionCode = 2
   versionName = "1.0.1"
   ```

2. **Build release APK**:
   ```bash
   ./gradlew clean assembleRelease
   ```

3. **Sign APK** (if needed):
   ```bash
   jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
     -keystore keystore.jks \
     app/build/outputs/apk/release/app-release-unsigned.apk alias
   ```

4. **Optimize with zipalign**:
   ```bash
   zipalign -v 4 app-release-unsigned.apk app-release.apk
   ```

## License
MIT License - Open source

## Support
For issues, create a GitHub issue or contact: your-email@example.com

---

**Built with ❤️ for runners**
