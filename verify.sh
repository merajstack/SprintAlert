#!/bin/bash

# SprintAlert Project Verification Script
# This script verifies that all necessary files are in place

echo "╔════════════════════════════════════════════════════════════╗"
echo "║         SprintAlert - Project Verification Script         ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

PROJECT_ROOT="/Users/mohammedmehraj/app"
REQUIRED_FILES=(
    # Kotlin Source Files
    "app/src/main/java/com/sprintalert/MainActivity.kt"
    "app/src/main/java/com/sprintalert/SprintViewModel.kt"
    "app/src/main/java/com/sprintalert/RecordAdapter.kt"
    "app/src/main/java/com/sprintalert/db/SprintAlertDatabase.kt"
    "app/src/main/java/com/sprintalert/db/SprintRecord.kt"
    "app/src/main/java/com/sprintalert/db/SprintRecordDao.kt"
    "app/src/main/java/com/sprintalert/service/GPSTrackingService.kt"
    "app/src/main/java/com/sprintalert/utils/GPSManager.kt"
    "app/src/main/java/com/sprintalert/utils/DistanceCalculator.kt"
    "app/src/main/java/com/sprintalert/utils/AlertManager.kt"
    "app/src/main/java/com/sprintalert/utils/FormatUtils.kt"
    
    # Layout Files
    "app/src/main/res/layout/activity_main.xml"
    "app/src/main/res/layout/item_record.xml"
    
    # Drawable Files
    "app/src/main/res/drawable/button_rounded.xml"
    "app/src/main/res/drawable/button_rounded_red.xml"
    "app/src/main/res/drawable/rounded_input.xml"
    "app/src/main/res/drawable/ic_launcher.xml"
    
    # Values Files
    "app/src/main/res/values/colors.xml"
    "app/src/main/res/values/strings.xml"
    "app/src/main/res/values/themes.xml"
    
    # Build Files
    "app/build.gradle.kts"
    "app/src/main/AndroidManifest.xml"
    "build.gradle.kts"
    "settings.gradle.kts"
    "gradle.properties"
    "gradlew"
    ".gitignore"
    "README.md"
    "BUILDING.md"
)

MISSING=0
FOUND=0

for file in "${REQUIRED_FILES[@]}"; do
    full_path="$PROJECT_ROOT/$file"
    if [ -f "$full_path" ]; then
        echo "✅ $file"
        ((FOUND++))
    else
        echo "❌ $file (MISSING)"
        ((MISSING++))
    fi
done

echo ""
echo "════════════════════════════════════════════════════════════"
echo "Summary: $FOUND found, $MISSING missing"
echo "════════════════════════════════════════════════════════════"
echo ""

if [ $MISSING -eq 0 ]; then
    echo "✅ All files present! Project is ready to build."
    echo ""
    echo "Next steps:"
    echo "1. Install Android SDK (if not already installed)"
    echo "2. Run: cd $PROJECT_ROOT && ./gradlew assembleRelease"
    echo "3. APK will be at: app/build/outputs/apk/release/app-release.apk"
    exit 0
else
    echo "❌ Some files are missing. Please check the project structure."
    exit 1
fi
