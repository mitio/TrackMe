package com.drone.trackme;

import android.app.Application;

/**
 * Represents Application class, used for storing user session data .  
 *
 * @author martin.mitev
 */
public class TrackMeApplication extends Application
{
	private String sessionID;
	private String trackID;
	private String userName;
	private String password;
	private String latitude;
	private String longitude;
	private Boolean isInOnlineMode;
	
	public TrackMeApplication()
	{
		super();
	}	
	
	public void onCreate()
	{
		super.onCreate();
	}

	public void onTerminate()
	{
		super.onTerminate();
	}
	
	public String getSessionID()
	{ return this.sessionID; }
	
	public void setSessionID(String sessionID)
	{ this.sessionID = sessionID; }

	public void setTrackID(String trackID)
	{ this.trackID = trackID; }
	
	public String getTrackID()
	{ return this.trackID; }

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

	public Boolean getIsInOnlineMode()
	{ return this.isInOnlineMode; }
	public void setIsInOnlineMode(Boolean isInOnlineMode)
	{ this.isInOnlineMode = isInOnlineMode;}
}
