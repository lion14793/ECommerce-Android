package com.appdynamics.pmdemoapps.android.cart.service.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.pmdemoapps.android.cart.misc.GlobalDataProvider;

import android.os.AsyncTask;

public class GetRequestService extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
        	HttpGet get = new HttpGet(uri[0]);
        	get.addHeader("Cookie", GlobalDataProvider.getInstance().getSessionId());
            get.addHeader("appdynamicssnapshotenabled","true");
            Instrumentation.startTimer("GetRequestService");

            response = httpclient.execute(get);
            StatusLine statusLine = response.getStatusLine();
            if((statusLine.getStatusCode() == HttpStatus.SC_OK) || (statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT)){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
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