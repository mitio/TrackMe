package com.drone.trackme;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Represents a custom GPS LocationListener.  
 *
 * @author martin.mitev
 */
public class GPSTest extends Object implements LocationListener
{
	private LocationManager lm;
	private double latitude;
	private double longitude;
	
	/**
	 * Represents Constructor of GPSTest class .
	 *
	 * @author martin.mitev
	 * @param context ...............
	 * @see Context
	 */
	public GPSTest(Context context)
	{
		lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1l, 1l, this);
		
	}
	
	/**
	 * Represents a method that collects GPS coordinates .
	 *
	 * @author martin.mitev
	 * @return .................
	 * @see NameValuePair
	 */
     public List<NameValuePair> GetLocationCoordinates()
     {  
    	 try
    	 {
    		 this.latitude = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
    		 this.longitude = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
    	 }
    	 catch(Exception ex)
    	 {
    		 //this.latitude++;
    		 //this.longitude++;
    	 }
    	 List<NameValuePair> coords = new ArrayList<NameValuePair>(2);    	 
    	 
    	 coords.add(new BasicNameValuePair("latitude", Double.toString(this.latitude)));
    	 coords.add(new BasicNameValuePair("longitude", Double.toString(this.longitude)));
    	 return coords;
     }
     
     
	@Override
	/**
	 * Represents  .
	 *
	 * @author martin.mitev
	 * @param location .................
	 * @see Location
	 */
	public void onLocationChanged(Location location)
	{	
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	@Override
	/**
	 * Represents ....................... .
	 *
	 * @author martin.mitev
	 * @param provider .................
	 */
	public void onProviderDisabled(String provider)
	{
		 Log.e("GPS", "provider disabled " + provider);		
	}

	@Override
	/**
	 * Represents ....................... .
	 *
	 * @author martin.mitev
	 * @param provider .................
	 */
	public void onProviderEnabled(String provider)
	{
		Log.e("GPS", "provider enabled " + provider);		
	}

	@Override
	/**
	 * Represents ....................... .
	 *
	 * @author martin.mitev
	 * @param provider .................
	 * @param status ...............
	 * @param extras ............
	 * @see Bundle
	 */
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		Log.e("GPS", "status changed to " + provider + " [" + status + "]");	
	}
}