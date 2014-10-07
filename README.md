ECommerce-Android
=================
E-Commerce example for Android (Gradle build with Robotium). The Robotium tests can be used to run repeated randomized simulations of an e-commerce shopping cart checkout scenario.

Pre-Requisites
--------------
1. Android Studio: download latest version from [here](https://developer.android.com/sdk/installing/studio.html)
2. Gradle: download latest version (must be 2.0+) from [here](http://www.gradle.org/downloads) and set PATH to include Gradle bin dir
3. Android SDK: Install at least the following:
    - Latest Android Tools, Platform-Tools and Build-Tools
    - SDK Platforms for 4.2.2 (Android 17), 4.3.1 (Android 18) and 4.4.2 (Android 19)
    - Extras: Android Support Repository, Android Support Library and Google Repository

Importing the Project
---------------------
1. Clone a local copy of this project with `git clone https://github.com/Appdynamics/ECommerce-Android`
2. Open Android Studio and Import Project (select app/build.gradle)

Running the Robotium Tests
--------------------------
To install and run manually: `gradle clean installDebug`

To install and run automated Robotium tests: `gradle clean connectedCheck`

The connectedCheck task will upload the application and test .apk files to all connected devices and run in parallel

To view connected devices: `adb devices`

To send a command to a specific device, use: `adb -s <device> ...`

To view output, use logcat and filter on ADInstrumentation: `adb logcat ADInstrumentation:D *:S`

To test if a device is powered on, and send a power key press if not: 
`adb shell dumpsys power | grep "mScreenOn=true" | xargs -0 test -z && adb shell input keyevent 26`

To unlock a device:
`adb shell input keyevent 82`

The [wakeUpAndRun.sh](https://github.com/Appdynamics/ECommerce-Android/blob/master/wakeUpAndRun.sh) shell script will check for all connected devices, wake them up and unlock if necessary before running `gradle connectedCheck`

