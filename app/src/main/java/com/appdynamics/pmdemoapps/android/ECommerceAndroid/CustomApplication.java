package com.appdynamics.pmdemoapps.android.ECommerceAndroid;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.appdynamics.eumagent.runtime.InfoPoint;
import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.PreferenceConstants;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.UserPrefActivity;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


/**
 * Currently used to initialize the Universal Image Loader which allows some flexibility with the number of threads flag
 * TODO Use VOLLEY for image loading and network requests later on as more documentation becomes available
 * @author harsha.hegde
 *
 */
public class CustomApplication extends Application {
	
	
	@Override
    @InfoPoint
    public void onCreate() {
        super.onCreate();
        setGlobalData();
        Instrumentation.start(GlobalDataProvider.getInstance().getEumAppKey(),
                getApplicationContext(),
                GlobalDataProvider.getInstance().getCollectorUrl(),
                true);

        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .resetViewBeforeLoading(false)  // default
                        .delayBeforeLoading(0)//Good way to simulate slow performance
                        .cacheInMemory(true) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();


        // Create global configuration and initialize ImageLoader with this configuration
        //ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                        .threadPriority(Thread.NORM_PRIORITY - 1) // default
                        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                        .memoryCacheSize(2 * 1024 * 1024)
                        .memoryCacheSizePercentage(13) // default
                        .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                        .defaultDisplayImageOptions(options) // default
                        .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    public void setGlobalData() {
        setEndpoints();
	}
	
	public void setEndpoints(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		String server_url = PreferenceConstants.END_POINT_URL;
		String eumAppKey = PreferenceConstants.EUM_APP_KEY;
		String collectorUrl = PreferenceConstants.EUM_COLLECTOR_URL;
		
		String image_url = server_url;
		editor. putString(UserPrefActivity.KEY_PREF_SERVER_ENDPOINT,server_url);
		editor. putString(UserPrefActivity.EUM_COLLECTOR_URL, collectorUrl);
		editor.putString(UserPrefActivity.EUM_APP_KEY, eumAppKey);
		editor.commit();
		
		if (server_url.endsWith("/")){
			server_url = server_url+"rest/";
		}
		else {
			server_url = server_url+"/rest/";
			image_url = image_url+"/";
		}

		GlobalDataProvider.getInstance().setCollectorUrl(collectorUrl);
		GlobalDataProvider.getInstance().setEumAppKey(eumAppKey);
		GlobalDataProvider.getInstance().setRestServiceUrl(server_url);
		GlobalDataProvider.getInstance().setImageUrl(image_url);
		
	}
}
