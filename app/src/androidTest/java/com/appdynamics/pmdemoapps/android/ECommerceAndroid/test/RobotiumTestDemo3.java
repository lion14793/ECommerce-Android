package com.appdynamics.pmdemoapps.android.ECommerceAndroid.test;


public class RobotiumTestDemo3 extends RobotiumBase {

    private static final String APP_URL = "http://54.203.82.235/appdynamicspilot/";
    private static final String EUM_KEY = "DEMO-AAB-AUM";
    private static final String EUM_URL = "http://54.244.95.83:9001";

    public String getAppURL() { return APP_URL; };
    public String getEumKey() { return EUM_KEY; };
    public String getEumURL() { return EUM_URL; };

    @Override
    public void testRun() {
        super.testRun();
    }
}
