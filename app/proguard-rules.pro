# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renameSourcefileattribute SourceFile

# ── CarFunction domain models ───────────────────────────────────────────
# Keep all enums and data classes used by MVI State (serialized/reflected).
-keep class com.example.carfunction.domain.model.** { *; }
-keep class com.example.carfunction.core.platform.** { *; }
-keep class com.example.carfunction.core.oem.** { *; }

# Keep MVI contracts (sealed interfaces used by when-expressions)
-keep class com.example.carfunction.presentation.**.ComfortInteriorContract$* { *; }
-keep class com.example.carfunction.presentation.**.MyCarContract$* { *; }

# Keep ViewModel companion objects (PIN_LENGTH, hashPin, etc.)
-keep class com.example.carfunction.presentation.**$Companion { *; }
