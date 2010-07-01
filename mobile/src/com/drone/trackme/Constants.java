package com.drone.trackme;

/**
 * Contains string constants used in TrackMe project. 
 *
 * @author miroslava.nikolova
 * @author martin.mitev
 */
public class Constants
{
	/**
	 * Represents string constant for full name of main activity. 
	 *
	 * @author martin.mitev
	 */
    public static final String INTENT_ACTION_VIEW_MAINACTIVITY = 
    	"com.drone.trackme.MainActivity";
    
	/**
	 * Represents string constant for login url.
	 *
	 * @author martin.mitev
	 */
    public static final String LOGIN_URL = "http://master.slavcho.org/api/login";
    
	/**
	 * Represents string constant for logout url.
	 *
	 * @author martin.mitev
	 */
	public static final String LOGOUT_URL = "http://master.slavcho.org/api/logout";
	
	/**
	 * Represents string constant for url used when sending coordinates to server. 
	 *
	 * @author martin.mitev
	 */
	public static final String SEND_COORDS_URL = 
		"http://master.slavcho.org/api/push_coordinates";
	
	/**
	 * Represents string constant for error message displayed to user when application goes wrong for some reason. 
	 *
	 * @author miroslava.nikolova
	 */
	public static final String ERROR_MESSAGE = "Oops! An error occured.";
	
	public static final String START_TRACK_URL = 
		"http://master.slavcho.org/api/start_track";
}
