# Keep room database classes
-keep class androidx.room.** { *; }
-keepclassmembers class androidx.room.** { *; }

# Keep our package
-keep class com.sprintalert.** { *; }
-keepclassmembers class com.sprintalert.** { *; }

# Keep data classes
-keepclassmembers class * {
    *** *(...);
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
