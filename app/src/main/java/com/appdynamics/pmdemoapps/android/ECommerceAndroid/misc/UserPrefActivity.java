package com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.R;

public class UserPrefActivity extends PreferenceActivity {

	public static final String KEY_PREF_SERVER_ENDPOINT = "pref_rest_uri";
	public static final String EUM_APP_KEY = "pref_eum_app_key";
	public static final String EUM_COLLECTOR_URL = "pref_eum_collector_url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);//Providing support for pre 3.0 devices, hence the deprecated method
	}
}
