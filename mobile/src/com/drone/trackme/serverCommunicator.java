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

public class serverCommunicator 
{
	
	public static String receiveDataFromServer(List<NameValuePair> parameters, String url)
	{		
		 String result = null;
		
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url); 
		
		 try 
		 {  		 	
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
			     StringBuffer stream = new StringBuffer();
			     byte[] b = new byte[4096];
			     for (int n; (n = inputStream.read(b)) != -1;) {
			     stream.append(new String(b, 0, n));
			     }
			     result =  stream.toString();
		     }
		     else
		     {
		    	 // exception?
		     }
		       
		 } 
		 catch (ClientProtocolException e) 
		 {  
		     // handle exception   
		 } 
		 catch (IOException e) 
		 {  
		 	// handle exception
		 }

		 return result;		 
	}
	
	public static void sendDataToServer(List<NameValuePair> parameters, String url)
	{
		
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url); 
		
		 try 
		 {  		 	
		     httppost.setEntity(new UrlEncodedFormEntity(parameters));  
		     
		     // Execute HTTP Post Request  
		     HttpResponse response = httpclient.execute(httppost); 
		     StatusLine status = response.getStatusLine();
		     int statusCode = status.getStatusCode();
		     
//		     if (statusCode != 200)
//		     {
//		    	 // exception?
//		     }   
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
}
