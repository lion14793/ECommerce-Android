#!/bin/bash

./gradlew clean assembleDebug assembleDebugTest

while true; do
	./WakeUpAndRun.sh
done
