package com.drone.trackme;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.drone.trackme.service.MainService;

public class MainActivity extends Activity
{	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
				
		final Button btnStartSendCoords = (Button) findViewById(R.id.btnStartSendCoords);
		final Button btnStopSendCoords = (Button) findViewById(R.id.btnStopSendCoords);
		final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		final Button btnShowCurrentCoords = (Button) findViewById(R.id.btnShowCurrentCoords);
		final Button btnLogout = (Button) findViewById(R.id.btnLogout);
		final EditText edtLatitude = (EditText)findViewById(R.id.edtLatitude);
		final EditText edtLongitude = (EditText)findViewById(R.id.edtLongitude);
		
		btnStartSendCoords.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				//startService(new Intent(MainActivity.this, SimpleService.class));
			
				//Intent serviceIntent = new Intent();
				//serviceIntent.setClass(MainActivity.this, MainService.class);
				Intent serviceIntent = new Intent(MainActivity.this, MainService.class);
				startService(serviceIntent); 	
				
			}
		});
		
		btnStopSendCoords.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				Intent serviceIntent = new Intent(MainActivity.this, MainService.class);
				stopService(serviceIntent); 	
				
			}
		});
		
		btnShowCurrentCoords.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				TrackMeApplication app = (TrackMeApplication)getApplication();
				//btnSettings.setText(app.getSessionID());
				
				edtLatitude.setText(app.getLatitude());
				edtLongitude.setText(app.getLongitude());
			}
		});
		
		btnLogout.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
	        {               	
	        		 TrackMeApplication application = (TrackMeApplication)getApplication();
	                 String sessionId = application.getSessionID();
	                 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);  
	                 nameValuePairs.add(new BasicNameValuePair("session_id", sessionId)); 
	                 
	                 serverCommunicator.sendDataToServer(nameValuePairs, Constants.LOGOUT_URL);
	        }
		});		
	}	
}