package com.drone.trackme.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.drone.trackme.Constants;
import com.drone.trackme.GPSTest;
import com.drone.trackme.MainActivity;
import com.drone.trackme.TrackMeApplication;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;

public class MainService extends Service
{
	private Handler serviceHandler = null;
	private GPSTest gpsTest;
	private RunTask runTask;
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		gpsTest = new GPSTest(getApplicationContext());
		 
		this.serviceHandler = new Handler();
		// if you want certain task to be done periodically define a task and call it this way
		this.runTask = new RunTask();
		this.serviceHandler.postDelayed( runTask, 2000L );
	}
	
	@Override
	public IBinder onBind( Intent intent )
	{
	    //return binder;
	    //you might as well define an binder class and use it to bind to a running activity
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();		
	}
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		this.serviceHandler.removeCallbacks(this.runTask);
	}

	class RunTask implements Runnable 
	{
		private void doServiceWork()
		{	
			TrackMeApplication app = (TrackMeApplication)getApplication();
			
			List<NameValuePair> coords = gpsTest.GetLocationCoordinates();
			
			String latitude = coords.get(0).getValue();
			String longitude = coords.get(1).getValue();
			
			app.setLatitude(latitude);
			app.setLongitude(longitude);	
			
			// SendCoordsToServer(coords);
		}
		
		private void SendCoordsToServer(List<NameValuePair> coords)
		{
			HttpClient httpclient = new DefaultHttpClient();  
			HttpPost httppost = new HttpPost(Constants.SEND_COORDS_URL); 
			
			 try 
			 {  		 	
			     httppost.setEntity(new UrlEncodedFormEntity(coords));  
			     
			     // Execute HTTP Post Request  
			     HttpResponse response = httpclient.execute(httppost); 
			     StatusLine status = response.getStatusLine();
			     int statusCode = status.getStatusCode();   
			 } 
			 catch (ClientProtocolException e) 
			 {  
			     // handle exception   
			 } 
			 catch (IOException e) 
			 {  
			 	// handle exception
			 }
		}

		public void run() 
		{			
			doServiceWork();
			// to have the service task run after 2 seconds periodically
			serviceHandler.postDelayed( this, 2000L );
		}
	}
}