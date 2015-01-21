package com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc;

public class GlobalDataProvider {
	
	private static final GlobalDataProvider INSTANCE = new GlobalDataProvider();
	
	private String restServiceUrl;
	private String imageUrl;
	private String collectorUrl;
	private String eumAppKey;
	private String sessionId;



	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRestServiceUrl() {
		return restServiceUrl;
	}

	public void setRestServiceUrl(String restServiceUrl) {
		this.restServiceUrl = restServiceUrl;
	}

	private GlobalDataProvider() {
	    if (INSTANCE != null) {
	        throw new IllegalStateException("Already instantiated");
	    }
	}

	public static GlobalDataProvider getInstance() {
	    return INSTANCE;
	}

	public String getCollectorUrl() {
		return collectorUrl;
	}

	public void setCollectorUrl(String collectorUrl) {
		this.collectorUrl = collectorUrl;
	}

	public String getEumAppKey() {
		return eumAppKey;
	}

	public void setEumAppKey(String eumAppKey) {
		this.eumAppKey = eumAppKey;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	

}


