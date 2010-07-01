package com.drone.trackme.exceptions;
/**
 * Represents a custom exception which may occur during login activity.  
 *
 * @author miroslava.nikolova
 */
public class LoginException extends Exception {
	
	private static final long serialVersionUID = -5975104177714333052L;
	/**
	 * Constructor of custom LoginException.
	 *
	 * @author miroslava.nikolova
	 * @param errorMessage error message that is displayed to user
	 */
	public LoginException(String errorMessage)
	{
		super(errorMessage);
	}
}
