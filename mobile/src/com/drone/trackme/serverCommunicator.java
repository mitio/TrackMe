package com.drone.trackme;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.drone.trackme.exceptions.*;

/**
 * Represents a class that is used to send and receive data from server.  
 *
 * @author miroslava.nikolova
 */
public class ServerCommunicator 
{
	/**
	 * Receives data from server.
	 * Executes http post request to server supplying a list of {@link NameValuePair}
	 * and returns xml as an {@link InputStream} if status is OK.
	 * If user provides wrong username and/or password, an {@link LoginException} is 
	 * thrown with an appropriate message. In case of status that is not OK, another 
	 * {@link LoginException} is thrown with error message.
	 *
	 * @author miroslava.nikolova
	 * @param parameters list of {@link NameValuePair} that are sent to server
	 * @param url url used to send data to server
	 * @return 
	 * @throws ClientProtocolException if an client protocol exception occured
	 * @throws IOException if an input or output exception occured
	 * @throws LoginException if user provides wrong username and/or password
	 * @see InputStream
	 */
	public static InputStream receiveDataFromServer(List<NameValuePair> parameters, String url) 
				throws ClientProtocolException, IOException, LoginException 
	{		
		 InputStream result = null;
		
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url); 
				 	
	     httppost.setEntity(new UrlEncodedFormEntity(parameters));  
	     
	     // Execute HTTP Post Request  
	     HttpResponse response = httpclient.execute(httppost); 
	     //Header[] headers = response.getAllHeaders();
	     StatusLine status = response.getStatusLine();
	     int statusCode = status.getStatusCode();
	     //HttpEntity httpEntity = response.getEntity();
	     InputStream inputStream = response.getEntity().getContent();
	     
	     if(statusCode == 200)
	     {
	    	 result = inputStream;
	     }
	     else if (statusCode == 401)
	     	{
	    	  throw new LoginException("Wrong username and/or password.");
	     	}
	     	else
	     	{
	     	  throw new LoginException("Oops! An error occured.");	
	     	}

		 return result;		 
	}
	
	/**
	 * Sends data to server.  
	 * This method sends list of {@link NameValuePair} to server
	 * and returns status code of executed request.
	 * Http post request is executed.
	 *
	 * @author miroslava.nikolova
	 * @param parameters list of {@link NameValuePair} that are sent to server
	 * @param url url used to send data to server
	 * @return status code of http request used to send data to server
	 * @throws ClientProtocolException if an client protocol exception occured
	 * @throws IOException if an input or output exception occured
	 * @see NameValuePair
	 */
	public static int sendDataToServer(List<NameValuePair> parameters, String url)
					throws ClientProtocolException, IOException
	{
		
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url); 
				 	
	     httppost.setEntity(new UrlEncodedFormEntity(parameters));  
	     
	     // Execute HTTP Post Request  
	     HttpResponse response = httpclient.execute(httppost); 
	     StatusLine status = response.getStatusLine();
	     int statusCode = status.getStatusCode();
	     
	     return statusCode;
	}
}
