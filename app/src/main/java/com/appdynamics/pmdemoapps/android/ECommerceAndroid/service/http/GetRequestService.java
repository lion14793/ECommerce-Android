package com.appdynamics.pmdemoapps.android.ECommerceAndroid.service.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.misc.GlobalDataProvider;

import android.os.AsyncTask;
import android.util.Log;

public class GetRequestService extends AsyncTask<String, String, String>{
    private static final String TAG = GetRequestService.class.getName();

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            Log.d(TAG, "Call to " + uri[0]);
            HttpGet get = new HttpGet(uri[0]);
        	get.addHeader("Cookie", GlobalDataProvider.getInstance().getSessionId());
            get.addHeader("appdynamicssnapshotenabled","true");
            Instrumentation.startTimer("GetRequestService");

            response = httpclient.execute(get);
            StatusLine statusLine = response.getStatusLine();
            Log.d(TAG, "Call to " + uri[0] + " Status Code = " + statusLine.getStatusCode());
            if((statusLine.getStatusCode() == HttpStatus.SC_OK)) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                Log.d(TAG, "Call to " + uri[0] + " Response body = " + responseString);
            }
            else if (statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT) {
            }
            else {
                //Closes the connection.
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        catch(Exception e){
			e.printStackTrace();
		}

        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        Instrumentation.stopTimer("GetRequestService");
        super.onPostExecute(result);
    }
}