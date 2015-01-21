package com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc;

public interface AsyncTaskListener {
	void cancelled();
	void onPostExecute(boolean success, boolean error,String exceptionMessage);	
}
