ECommerce-Android
=================

E-Commerce example for Android (Gradle build with Robotium)

To install and run manually: `gradle clean installDebug`

To install and run automated Robotium tests: `gradle clean connectedCheck`

The connectedCheck task will upload the application and test .apk files to all connected devices and run in parallel

To view connected devices: `adb devices`

To view output, use logcat and filter on ADInstrumentation
