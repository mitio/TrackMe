package com.drone.trackme.db;


/**
 * A class that represents data that is saved in off-line mode and later is sent to server.  
 *
 * @author martin.mitev
 */
public class TrackedCoordinates
{
	private Integer trackID;
	private Double latitude;
	private Double longitude;
	private String datetime;
	
	/**
	 * Represents constructor with no arguments.
	 *
	 * @author martin.mitev
	 */
	public TrackedCoordinates()
	{
	}
	
	/**
	 * Represents constructor with arguments.
	 *
	 * @author martin.mitev
	 * @param trackID id of track
	 * @param latitude latitude coordinate 
	 * @param longitude longitude coordinate
	 * @param datetime date and time for current position		
	 */
	public TrackedCoordinates(Integer trackID, Double latitude, Double longitude, String datetime)
	{
		this.trackID = trackID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.datetime = datetime;
	}

	/**
	 * Concatenate data.
	 * Create a string that is sent to server. A new string is concatenation of latitude, longitude and date.
	 *
	 * @author martin.mitev
	 * @return String concatenation of latitude, longitude and date
	 * @see String
	 */
	public String CreateSendingString()
	{
		return this.getLatitude().toString().concat("|").concat(getLongitude().toString()).
		   concat("|").concat(getDatetime());
	}
	
	/**
	 * Sets new track id.
	 * Changes track id with new value.
	 *
	 * @author martin.mitev
	 * @param int id of track that will be changed
	 */
	public void setTrackID(Integer trackID)
	{
		this.trackID = trackID;
	}

	/**
	 * Gets track id.
	 * Returns id of track.
	 *
	 * @author martin.mitev
	 */
	public Integer getTrackID()
	{
		return trackID;
	}

	/**
	 * Sets new latitude.
	 * Changes latitude coordinate with new value.
	 *
	 * @author martin.mitev
	 * @param Double new latitude coordinate value
	 * @see Double
	 */
	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * Gets latitude.
	 * Returns latitude coordinate.
	 *
	 * @author martin.mitev
	 * @return Double latitude coordinate
	 * @see Double
	 */
	public Double getLatitude()
	{
		return latitude;
	}

	/**
	 * Sets new longitude.
	 * Changes longitude coordinate with new value.
	 *
	 * @author martin.mitev
	 * @param Double new longitude coordinate value
	 * @see Double
	 */
	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * Gets longitude.
	 * Returns longitude coordinate.
	 *
	 * @author martin.mitev
	 * @return Double longitude coordinate
	 * @see Double
	 */
	public Double getLongitude()
	{
		return longitude;
	}

	/**
	 * Sets new date and time.
	 * Changes date and time with new value.
	 *
	 * @author martin.mitev
	 * @param String new date and time value
	 * @see String
	 */
	public void setDatetime(String datetime)
	{
		this.datetime = datetime;
	}

	/**
	 * Gets date and time.
	 * Returns date and time.
	 *
	 * @author martin.mitev
	 * @return String date and time
	 * @see String
	 */
	public String getDatetime()
	{
		return datetime;
	}
}

	
