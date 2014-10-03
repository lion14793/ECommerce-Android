ECommerce-Android
=================

E-Commerce example for Android (Gradle build with Robotium)

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

