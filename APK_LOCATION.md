# SprintAlert APK - How to Get Your Build

## 🎯 APK Location (After Building)

Once you build the project, your APK will be at:

```
/Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

---

## 📦 Available APK Variants

### Release APK (Recommended for Devices)
**Location**: `app/build/outputs/apk/release/app-release.apk`
- ✅ Minified & optimized
- ✅ Obfuscated code
- ✅ ~25MB size
- ✅ Production quality
- ✅ Best performance

### Debug APK (For Development)
**Location**: `app/build/outputs/apk/debug/app-debug.apk`
- Full debug symbols
- Larger file size
- Logcat output enabled
- Useful for debugging

---

## 🚀 Quick Build Commands

### Build Release APK
```bash
cd /Users/mohammedmehraj/app
./gradlew assembleRelease
```

### Build Debug APK
```bash
cd /Users/mohammedmehraj/app
./gradlew assembleDebug
```

### Build & Install on Device
```bash
cd /Users/mohammedmehraj/app
./gradlew installRelease
# Or
./gradlew installDebug
```

---

## 📱 Install the APK

### Via ADB (Android Debug Bridge)
```bash
# Install release APK
adb install /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk

# Or debug APK
adb install /Users/mohammedmehraj/app/app/build/outputs/apk/debug/app-debug.apk

# Launch the app
adb shell am start -n com.sprintalert/.MainActivity
```

### Via USB File Transfer
1. Connect Android device via USB
2. Copy APK to device folder
3. Open file manager on device
4. Tap the APK file to install

### Via Email/Cloud
1. Copy APK from build folder
2. Share via email or cloud storage
3. Download on device
4. Tap to install

---

## ✅ How to Know Your Build is Ready

### Success Indicators
```
BUILD SUCCESSFUL in 45s
```

### Verify APK Exists
```bash
ls -lh /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

### Check File Size
```bash
# Should be around 25MB for release build
du -h /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

---

## 🔗 Direct APK Path (Ready After Build)

**Release APK**:
```
/Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

**Copy Command**:
```bash
cp /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk ~/Downloads/SprintAlert.apk
```

---

## 📊 APK Information

| Property | Value |
|----------|-------|
| **Package Name** | com.sprintalert |
| **App Name** | SprintAlert |
| **Version** | 1.0.0 |
| **Version Code** | 1 |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 34 (Android 14) |
| **Size (Release)** | ~25MB |
| **Architecture** | ARM64 + ARMv7 |

---

## 🎓 Build Troubleshooting

### Build Fails - Java Not Found
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@11
./gradlew assembleRelease
```

### Build Fails - SDK Not Found
1. Download Android Studio
2. Install SDK via SDK Manager
3. Try again

### APK Not Generated
```bash
# Clean and rebuild
./gradlew clean assembleRelease
```

### APK Size Too Large
The release build includes all resources. Size is expected at ~25MB.

---

## 🎯 Next Steps After Getting APK

1. ✅ **Install on Device**: Use ADB or file transfer
2. ✅ **Grant Permissions**: Allow Location, Vibrate
3. ✅ **Test GPS**: Wait for "GPS Ready" status
4. ✅ **Run First Sprint**: Enter 100m, press START
5. ✅ **Walk/Run 100m**: Watch distance increase
6. ✅ **Hear Alert**: Alert sounds when 100m reached
7. ✅ **Check History**: Verify record was saved

---

## 📝 Keeping Your APK

### Save for Later
```bash
# Copy to a safe location
mkdir -p ~/SprintAlert-Builds/v1.0.0
cp /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk \
   ~/SprintAlert-Builds/v1.0.0/SprintAlert-v1.0.0.apk
```

### Version Your Builds
Each time you rebuild, the new APK replaces the old one. Consider:
```bash
# Rename with timestamp
cp app-release.apk app-release-$(date +%Y%m%d-%H%M%S).apk
```

---

## 🔄 Rebuild Anytime

The project is set up so you can rebuild the APK anytime by running:

```bash
cd /Users/mohammedmehraj/app && ./gradlew assembleRelease
```

The APK will always be generated at:
```
app/build/outputs/apk/release/app-release.apk
```

---

## 📦 Summary

**After building, your APK is at:**
```
/Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

**Install with:**
```
adb install /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk
```

**Or copy to Downloads:**
```
cp /Users/mohammedmehraj/app/app/build/outputs/apk/release/app-release.apk ~/Downloads/SprintAlert.apk
```

---

**SprintAlert v1.0.0** - Ready for Your Device 🚀
