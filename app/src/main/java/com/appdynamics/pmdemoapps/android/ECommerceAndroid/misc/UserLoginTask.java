package com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.appdynamics.eumagent.runtime.Instrumentation;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		listener.cancelled();
	}

	@Override
	protected void onCancelled(Boolean result) {
		// TODO Auto-generated method stub
		listener.cancelled();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		listener.onPostExecute(result, error, exceptionMessage);
	}

	private static final String JSESSIONID="JSESSIONID";
	
	private AsyncTaskListener listener;
	
	private boolean error = false;
	boolean exceptionOccured = false;
	String exceptionMessage = null;
	boolean success = false;
	
	
	public UserLoginTask(AsyncTaskListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
        Instrumentation.startTimer("UserLoginTask");

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(GlobalDataProvider.getInstance().getRestServiceUrl() +"user/login");
		
		 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", params[0]));
		    nameValuePairs.add(new BasicNameValuePair("password", params[1]));
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
				HttpResponse response = httpclient.execute(httppost);
				if(response!=null && response.getEntity()!=null){
					String respStr = EntityUtils.toString(response.getEntity());
					if (respStr!=null && respStr.equalsIgnoreCase("Success"))
					{
						Header [] headers=response.getAllHeaders();
						for (Header header : headers) {
							if ("set-cookie".equalsIgnoreCase(header.getName())) {
								String headerValue = header.getValue();
								if(headerValue.startsWith(JSESSIONID)) {
									String jsessionId = headerValue.substring(0,headerValue.indexOf(';'));
									GlobalDataProvider.getInstance().setSessionId(jsessionId);
								}
							}
						}
						// TODO: Future enhancements - register the new account here??
						success = true;
                        Log.d("ADInstrumentation", "success");
						return true;
					} else { 
						error = true;
					}
				}else{
					exceptionOccured = true;
					exceptionMessage = "Unable to connect to server";
				}
				
			} catch (UnsupportedEncodingException e1) {
				exceptionOccured = true;
				e1.getLocalizedMessage();
				e1.printStackTrace(); 
			}
		    catch (ClientProtocolException e) {
		    	exceptionOccured = true;
		    	exceptionMessage = e.getLocalizedMessage();
				e.printStackTrace();
			} catch (IOException e) {
				exceptionOccured = true;
				exceptionMessage = e.getLocalizedMessage();
				e.printStackTrace();
			} finally {
                Instrumentation.stopTimer("UserLoginTask");
            }
        Log.d("ADInstrumentation", "failure");
		return false;
	}

}