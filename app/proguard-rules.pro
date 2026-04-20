-keep class com.refugios.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-dontwarn org.osmdroid.**
-keep class org.osmdroid.** { *; }
-dontwarn com.github.barteksc.**
-keep class com.github.barteksc.** { *; }