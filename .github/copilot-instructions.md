# SprintAlert Development Guide

## Project Structure
```
app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sprintalert/
│   │   │   │   ├── MainActivity.kt - Main UI
│   │   │   │   ├── SprintViewModel.kt - State management
│   │   │   │   ├── RecordAdapter.kt - History list adapter
│   │   │   │   ├── db/ - Room database
│   │   │   │   ├── service/ - Background GPS service
│   │   │   │   └── utils/ - Utilities (GPS, alerts, distance calculation)
│   │   │   └── res/ - Layout, drawable, values
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/ - Gradle wrapper files
```

## Build & Run
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease

# Clean build
./gradlew clean build

# Run on connected device
./gradlew installDebug
```

## Key Features Implemented
1. ✓ Offline-first with local SQLite storage
2. ✓ Real-time GPS distance tracking (Haversine formula)
3. ✓ Automatic sprint detection at target distance
4. ✓ Sound and vibration alerts
5. ✓ Foreground service for background operation
6. ✓ Dark theme UI with bright yellow timer
7. ✓ History storage and best time tracking
8. ✓ Lightweight (<30MB target)

## Testing Checklist
- [ ] Location permissions granted
- [ ] GPS initialization completes
- [ ] Timer starts/stops correctly
- [ ] Distance calculation accurate
- [ ] Alerts trigger at target distance
- [ ] Background service maintains GPS in background
- [ ] History saves and loads correctly
- [ ] App works offline without internet

