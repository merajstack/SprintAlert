#!/bin/bash

# SprintAlert Build Script
# This script builds a release APK for the SprintAlert app

set -e

echo "======================================"
echo "SprintAlert - Android Sprint Training App"
echo "======================================"
echo ""

# Check if Android SDK is installed
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME not set. Please install Android SDK."
    echo "Download from: https://developer.android.com/studio"
    exit 1
fi

echo "📦 Building release APK..."
echo "Android SDK: $ANDROID_HOME"
echo ""

cd "$(dirname "$0")"

# Clean build
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build release APK
echo "🔨 Building release APK..."
./gradlew assembleRelease -x lint

# Check if build succeeded
if [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
    echo "APK Location: app/build/outputs/apk/release/app-release.apk"
    echo "File Size: $(du -h app/build/outputs/apk/release/app-release.apk | cut -f1)"
    echo ""
    echo "📱 To install on device:"
    echo "   adb install app/build/outputs/apk/release/app-release.apk"
    echo ""
else
    echo "❌ Build failed!"
    exit 1
fi
