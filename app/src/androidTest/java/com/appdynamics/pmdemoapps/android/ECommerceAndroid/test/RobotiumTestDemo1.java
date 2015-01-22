package com.appdynamics.pmdemoapps.android.ECommerceAndroid.test;


public class RobotiumTestDemo1 extends RobotiumBase {
    private static final String APP_URL = "http://54.214.13.135:8000/appdynamicspilot/";
    private static final String EUM_KEY = "EUM-AAB-AUN";
    private static final String EUM_URL = "http://54.244.124.19";

    public String getAppURL() { return APP_URL; };
    public String getEumKey() { return EUM_KEY; };
    public String getEumURL() { return EUM_URL; };

    @Override
    public void testRun() {
        super.testRun();
    }
}
