#!/bin/bash

declare -a targets

targets=(
"demo1"
"demo2"
)

while true; do
	for i in ${targets[@]}; do
		echo "Target environment: ${i}"
		cp PreferenceConstants.${i} app/src/main/java/com/appdynamics/pmdemoapps/android/ECommerceAndroid/misc/PreferenceConstants.java 
		./WakeUpAndRun.sh
	done
done
