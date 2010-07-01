package com.drone.trackme.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.drone.trackme.TrackMeApplication;
import com.drone.trackme.db.DBHelper;
import com.drone.trackme.db.TrackedCoordinates;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Represents the service which is used to get continuously GPS coordinates  .  
 *
 * @author martin.mitev
 */
public class MainService extends Service
{
	private Handler serviceHandler = null;
	private GPSTest gpsTest;
	private RunTask runTask;
	private DBHelper dbHelper;
	private Boolean isThereAnyCoordinatesToSend;
	
	@Override
	/**
	 * Represents a method that starts the service which gets and sends current GPS coordinates .
	 *
	 * @author martin.mitev
	 * @param intent ...............
	 * @param startId ...................
	 * @see Intent
	 */
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		gpsTest = new GPSTest(getApplicationContext());
		dbHelper = new DBHelper(getApplicationContext());
		isThereAnyCoordinatesToSend = false;
		 
		this.serviceHandler = new Handler();
		// if you want certain task to be done periodically define a task and call it this way
		this.runTask = new RunTask();
		this.serviceHandler.postDelayed( runTask, 2000L );
	}
	
	@Override
	/**
	 * Represents ....................... .
	 *
	 * @author martin.mitev
	 * @param intent ...............
	 * @return ..................
	 * @see Intent
	 */
	public IBinder onBind( Intent intent )
	{
	    //return binder;
	    //you might as well define an binder class and use it to bind to a running activity
		return null;
	}
	
	public void onCreate() 
	{
		super.onCreate();		
	}
	
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
			
			Calendar currentDate = Calendar.getInstance();
			//SimpleDateFormat simpelDateFormat = new SimpleDateFormat("dd.MM.yyyy");
			SimpleDateFormat simpelDateFormat = new SimpleDateFormat("yyyy-MM-dd HH::mm:ss");
			String currentDateTime = simpelDateFormat.format(currentDate.getTime());
			
			List<NameValuePair> coordsToSend = new ArrayList<NameValuePair>(4);
			coordsToSend.add(new BasicNameValuePair("session_id", app.getSessionID()));
				
			
			if(app.getIsInOnlineMode())
			{		
				if(isThereAnyCoordinatesToSend)
				{
					List<TrackedCoordinates> allTrackedCoordinates = dbHelper.selectAll();
					for (TrackedCoordinates trackedCoordinates : allTrackedCoordinates)
					{
						coordsToSend.add( new BasicNameValuePair("track_id", trackedCoordinates.getTrackID().toString()));
						coordsToSend.add( new BasicNameValuePair("coord", trackedCoordinates.CreateSendingString()));
						SendCoordsToServer(coordsToSend);
					}
					dbHelper.deleteAll();
					isThereAnyCoordinatesToSend = false;				
				}
				
				coordsToSend.add(new BasicNameValuePair("track_id", app.getTrackID()));
				coordsToSend.add(new BasicNameValuePair("coord", latitude.concat("|").concat(longitude).
						concat("|").concat(currentDateTime)));
				SendCoordsToServer(coordsToSend);
			}
			else
			{
				TrackedCoordinates trackedCoordinates = new TrackedCoordinates(Integer.parseInt(app.getTrackID()), 
						Double.parseDouble(latitude), Double.parseDouble(longitude), currentDateTime);	
				SendCoordsToDB(trackedCoordinates);
				isThereAnyCoordinatesToSend = true;
			}			
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

		private void SendCoordsToDB(TrackedCoordinates trackedCoordinates)
		{
			try
			{
				dbHelper.insert(trackedCoordinates);	
			}
			catch(Exception e)
			{	
				// handle exception
			}
		}
		
		public void run() 
		{			
			doServiceWork();
			// to have the service task run after 300*2 seconds periodically
			serviceHandler.postDelayed(this, 10000 );
		}
	}
}