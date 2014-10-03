# Grab the IDs of all the connected devices / emulators
IDS=($(adb devices | sed '1,1d' | sed '$d' | cut -f 1 | sort))
NUMIDS=${#IDS[@]}

# Check for number of connected devices / emulators
if [[ 0 -eq "$NUMIDS" ]]; then
	# No IDs, exit
	echo "No devices detected"
	exit 0;
fi 
 
# Grab the model name for each device / emulator
declare -a MODEL_NAMES
for (( x=0; x < $NUMIDS; x++ )); do
	MODEL_NAMES[x]=$(adb devices | grep ${IDS[$x]} | cut -f 1 | xargs -I $ adb -s $ shell cat /system/build.prop | grep "ro.product.model" | cut -d "=" -f 2 | tr -d ' \r\t\n')
done
 
# Grab the platform version for each device / emulator
declare -a PLATFORM_VERSIONS
for (( x=0; x < $NUMIDS; x++ )); do
	PLATFORM_VERSIONS[x]=$(adb devices | grep ${IDS[$x]} | cut -f 1 | xargs -I $ adb -s $ shell cat /system/build.prop | grep "ro.build.version.release" | cut -d "=" -f 2 | tr -d ' \r\t\n')
done
 
for (( x=0; x < $NUMIDS; x++ )); do
	echo -e "Device: ${IDS[x]}\t\t${PLATFORM_VERSIONS[x]}\t\t${MODEL_NAMES[x]}"
        echo -e "Sending Power On keyevent (KEYCODE_POWER)"
	adb -s ${IDS[$x]} shell dumpsys power | grep "mScreenOn=true" | xargs -0 test -z && adb -s ${IDS[$x]} shell input keyevent 26
        echo -e "Sending Unlock keyevent (KEYCODE_MENU)"
	sleep 1
	adb -s ${IDS[$x]} shell input keyevent 82
done

gradle connectedCheck

