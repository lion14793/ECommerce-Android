package com.appdynamics.pmdemoapps.android.ECommerceAndroid.test;


public class RobotiumTestDemo2 extends RobotiumBase {

    private static final String APP_URL = "http://54.214.16.198:8000/appdynamicspilot/";
    private static final String EUM_KEY = "demo-AAB-AUY";
    private static final String EUM_URL = "http:/54.71.42.123:9001";

    public String getAppURL() { return APP_URL; };
    public String getEumKey() { return EUM_KEY; };
    public String getEumURL() { return EUM_URL; };

    @Override
    public void testRun() {
        super.testRun();
    }
}
