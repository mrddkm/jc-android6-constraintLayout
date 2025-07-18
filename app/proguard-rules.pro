# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Compose specific rules
-keep class androidx.compose.** { *; }
-keep class androidx.constraintlayout.compose.** { *; }

# Keep all Composables
-keep @androidx.compose.runtime.Composable class * {
    *;
}

# Keep all @Stable and @Immutable classes
-keep @androidx.compose.runtime.Stable class * {
    *;
}
-keep @androidx.compose.runtime.Immutable class * {
    *;
}

# Material3 specific rules
-keep class androidx.compose.material3.** { *; }

# ConstraintLayout Compose specific rules
-keep class androidx.constraintlayout.compose.** { *; }

# Keep Android 6+ compatibility classes
-keep class androidx.core.** { *; }
-keep class androidx.appcompat.** { *; }

# Keep vector drawables for Android 6+
-keep class androidx.vectordrawable.** { *; }

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep custom classes
-keep class com.example.constraintlayouttemplate.** { *; }

# AndroidX and Support Library rules
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }

# Kotlin coroutines rules
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep BuildConfig
-keep class **.BuildConfig { *; }

# Keep R class
-keep class **.R
-keep class **.R$* {
    <fields>;
}