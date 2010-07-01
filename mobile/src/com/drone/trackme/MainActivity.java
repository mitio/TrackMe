package com.drone.trackme;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drone.trackme.exceptions.LoginException;
import com.drone.trackme.service.MainService;
import com.drone.trackme.service.MainService;

/**
 * Represents main activity of mobile user.  
 *
 * @author martin.mitev, miroslava.nikolova
 */
public class MainActivity extends Activity
{	
	/** Called when the activity is first created. */
	@Override
    /**
     * Represents main activity of mobile user. 
     * .......................................... 
     *
     * @author martin.mitev
     * @param savedInstanceState ........
     * @see Bundle
     */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
				
		final Button btnStartSendCoords = (Button) findViewById(R.id.btnStartSendCoords);
		final Button btnStopSendCoords = (Button) findViewById(R.id.btnStopSendCoords);
		//final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		final Button btnShowCurrentCoords = (Button) findViewById(R.id.btnShowCurrentCoords);
		final Button btnLogout = (Button) findViewById(R.id.btnLogout);
		final EditText edtLatitude = (EditText)findViewById(R.id.edtLatitude);
		final EditText edtLongitude = (EditText)findViewById(R.id.edtLongitude);
		final Button btnGoOffline = (Button) findViewById(R.id.btnGoOffline);
		final Button btnGoOnline = (Button) findViewById(R.id.btnGoOnline);
		final TextView errorMessage = (TextView) findViewById(R.id.txvMainActivityErrorMessage); 
		
		btnGoOffline.setEnabled(true); //.setClickable(true);
		btnGoOnline.setEnabled(false); //.setClickable(false);
		
		TrackMeApplication app = (TrackMeApplication)getApplication();
		app.setIsInOnlineMode(true);
		
		btnStartSendCoords.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				TrackMeApplication app = (TrackMeApplication) getApplication();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				nameValuePairs.add(new BasicNameValuePair("session_id", app.getSessionID()));
				InputStream xmlResult;
				
				try
				{
					xmlResult = ServerCommunicator.receiveDataFromServer(
							nameValuePairs, Constants.START_TRACK_URL);

					  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					  DocumentBuilder db = dbf.newDocumentBuilder();
					  Document doc = db.parse(xmlResult);
					  doc.getDocumentElement().normalize();
					  System.out.println("Root element " + doc.getDocumentElement().getNodeName());
					  NodeList nodeLst = doc.getElementsByTagName("track");

					  for (int s = 0; s < nodeLst.getLength(); s++) 
					  {
					    Node fstNode = nodeLst.item(s);
					    
					    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
					    {					  
					      Element fstElmnt = (Element) fstNode;
					      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("id");
					      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					      NodeList fstNm = fstNmElmnt.getChildNodes();
					      String track_id  = ((Node)fstNm.item(0)).getNodeValue();
					      app.setTrackID(track_id);					    
					    }
					  }
				}
				catch(Exception e)
				{}
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
                 
                 try 
                 {
					int statusCode = ServerCommunicator.sendDataToServer(nameValuePairs, Constants.LOGOUT_URL);
					
					if (statusCode == 200)
					{
						Intent intent = new Intent(MainActivity.this, LoginActivity.class );		
						startActivity(intent);
					}
					else
					{
						errorMessage.setText(Constants.ERROR_MESSAGE);
					}
                 } 
                 catch (ClientProtocolException e) 
                 {
                	 errorMessage.setText(Constants.ERROR_MESSAGE);
				 } 
                 catch (IOException e) 
                 {
                	 errorMessage.setText(Constants.ERROR_MESSAGE);
				 }  
	        }
		});		
		
		btnGoOffline.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				TrackMeApplication app = (TrackMeApplication)getApplication();
				app.setIsInOnlineMode(false);
				btnGoOnline.setEnabled(true); 
				btnGoOffline.setEnabled(false);
				
				//kato se natisne zna4i da se spre da se pra6ta kym server, a da se pylni v bazata
				
				//edtLatitude.setText(app.getLatitude());
				//edtLongitude.setText(app.getLongitude());
			}
		});
		
		btnGoOnline.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{		
				TrackMeApplication app = (TrackMeApplication)getApplication();
				app.setIsInOnlineMode(true);
				btnGoOffline.setEnabled(true); 
				btnGoOnline.setEnabled(false);
				
				//kato se natisne, zna4i da se pratqt vsi4ki sybrani v bazata koordinati 
				//i da se zapo4ne da se pra6ta nanovo direktno kym server-a
				
				//edtLatitude.setText(app.getLatitude());
				//edtLongitude.setText(app.getLongitude());
			}
		});
	}	
}