package com.appdynamics.pmdemoapps.android.ECommerceAndroid.test;


public class RobotiumTestPmDemo extends RobotiumBase {

    private static final String APP_URL = "http://ec2-54-167-95-227.compute-1.amazonaws.com/appdynamicspilot/";
    private static final String EUM_KEY = "pm-cloud-AAB-AUZ";
    private static final String EUM_URL = "http://ec2-54-202-222-83.us-west-2.compute.amazonaws.com";

    public String getAppURL() { return APP_URL; };
    public String getEumKey() { return EUM_KEY; };
    public String getEumURL() { return EUM_URL; };

    @Override
    public void testRun() {
        super.testRun();
    }
}
