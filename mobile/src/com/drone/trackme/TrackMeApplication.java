package com.drone.trackme;

import android.app.Application;

public class TrackMeApplication extends Application
{
	private String sessionID;
	private String userName;
	private String password;
	private String latitude;
	private String longitude;
	
	public TrackMeApplication()
	{
		super();
	}	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();
	}
	
	public String getSessionID()
	{ return this.sessionID; }
	
	public void setSessionID(String sessionID)
	{ this.sessionID = sessionID; }
	
	public String getUserName()
	{ return this.userName; }
	
	public void setUserName(String userName)
	{ this.userName = userName; }
	
	public String getPassword()
	{ return this.userName; }
	
	public void setPassword(String password)
	{ this.password = password; }
	
	public String getLatitude()
	{ return this.latitude; }
	
	public void setLatitude(String latitude)
	{ this.latitude = latitude; }
	
	public String getLongitude()
	{ return this.longitude; }
	
	public void setLongitude(String longitude)
	{ this.longitude = longitude; }
}
