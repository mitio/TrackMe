package com.drone.trackme;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.XMLReader;

import com.drone.trackme.R;
import com.drone.trackme.serverCommunicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);        
        final EditText edtUsername = (EditText) findViewById(R.id.edtUserName);
        final EditText edtPassword = (EditText) findViewById(R.id.edtPassword);        
        
        btnLogin.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{				
				String username = edtUsername.getText().toString();  
                String password = edtPassword.getText().toString();  

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
                nameValuePairs.add(new BasicNameValuePair("login", username));  
                nameValuePairs.add(new BasicNameValuePair("password", password)); 	
                
                // server under construction!
                String xmlResult = serverCommunicator.receiveDataFromServer(nameValuePairs, Constants.LOGIN_URL);
                //get session id from xml, save it and redirect screen
				
				
				
				
				TrackMeApplication application = (TrackMeApplication)getApplication();
				application.setSessionID("sessionID");
				application.setUserName(username);
				application.setPassword(password);				
				
				Intent intent = new Intent(LoginActivity.this, MainActivity.class );		
				startActivity(intent);
			}
		});
    }
}