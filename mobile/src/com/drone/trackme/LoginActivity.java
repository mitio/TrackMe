package com.drone.trackme;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.drone.trackme.R;
import com.drone.trackme.ServerCommunicator;
import com.drone.trackme.exceptions.LoginException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Represents a login activity of mobile user.  
 *
 * @author miroslava.nikolova
 */
public class LoginActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    /**
     * Represents a login activity of mobile user. 
     * Gets entered username and password and send them to server.
     * Server check credentials and return response with session id
     * in case of correct username and password. Session id is saved.
     * In case of incorrect username and/or password an appropriate 
     * message is displayed to user. When any other errors occur 
     * another error message is displayed to user.  
     *
     * @author miroslava.nikolova
     * @param savedInstanceState ........
     * @see Bundle
     */
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);        
        final EditText edtUsername = (EditText) findViewById(R.id.edtUserName);
        final EditText edtPassword = (EditText) findViewById(R.id.edtPassword);        
        final TextView errorMessage = (TextView) findViewById(R.id.txvErrorMessage); 
             
        btnLogin.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{				

				try {
					String username = edtUsername.getText().toString();  
	                String password = edtPassword.getText().toString();  

	                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
	                nameValuePairs.add(new BasicNameValuePair("login", username));  
	                nameValuePairs.add(new BasicNameValuePair("password", password)); 	
	                
	                String sessionID = new String();
	                InputStream xmlResult = ServerCommunicator.receiveDataFromServer(nameValuePairs, Constants.LOGIN_URL);
	                //get session id from xml, save it and redirect screen
	                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	                DocumentBuilder db = dbf.newDocumentBuilder();
	                Document doc = db.parse(xmlResult);
	  			  	doc.getDocumentElement().normalize();
					
		  			NodeList nodeLst = doc.getElementsByTagName("root");
	
		  			for (int s = 0; s < nodeLst.getLength(); s++) 
		  			{
		  			  Node fstNode = nodeLst.item(s);
		  			    
		  			  if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
		  			  {
		  			    Element fstElmnt = (Element) fstNode;
		  			    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("session_id");
		  			    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		  			    NodeList fstNm = fstNmElmnt.getChildNodes();
		  			    sessionID = ((Node) fstNm.item(0)).getNodeValue();
		  			  }
		  			}
					
					TrackMeApplication application = (TrackMeApplication)getApplication();
					application.setSessionID(sessionID);
					application.setUserName(username);
					application.setPassword(password);				
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class );		
					startActivity(intent);
				} 
				catch(LoginException le)
				{
					errorMessage.setText(le.getMessage());
				}
				catch (ClientProtocolException cpe)
				{
					errorMessage.setText(Constants.ERROR_MESSAGE);
				}
				catch (ParserConfigurationException pce) 
				{
					errorMessage.setText(Constants.ERROR_MESSAGE);
				}
				catch (SAXException se) 
				{
					errorMessage.setText(Constants.ERROR_MESSAGE);
				} 
				catch (IOException ioe) 
				{
					errorMessage.setText(Constants.ERROR_MESSAGE);
				}
				catch (Exception ioe) 
				{
					errorMessage.setText(Constants.ERROR_MESSAGE);
				}
			}
		});
    }
}