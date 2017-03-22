package com.example.avto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;



public class MysincTasc extends AsyncTask<String, Void, String> {
	private ProgressDialog pDialog;
	private MainActivity activity;
	private String url;
	private XmlPullParserFactory xmlFactoryObject;
	

	public MysincTasc(MainActivity activity, String url) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.url = url;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(activity);
		pDialog.setTitle("Get USD Information from XML");
		pDialog.setMessage("Loading curs $...");
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(this.url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(10000  /*milliseconds*/ );
			connection.setConnectTimeout(150000  /*milliseconds*/ ); 
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();
			InputStream stream = connection.getInputStream();
			xmlFactoryObject = XmlPullParserFactory.newInstance();
			XmlPullParser myParser = xmlFactoryObject.newPullParser(); 

			myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			myParser.setInput(stream, null);
			String result=parseXML(myParser);
			
			stream.close();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("AsyncTask", "exception");
			return null;
		}
	}
	

	

	public String parseXML(XmlPullParser myParser) {
		// TODO Auto-generated method stub
		String l2 = null;
		try {int i = 0;
		while (myParser.getEventType()!= XmlPullParser.END_DOCUMENT) {
		    if (myParser.getEventType() == XmlPullParser.START_TAG
		            && myParser.getName().equals("Currency") 
		            && myParser.getAttributeValue(0).equals("145"))
		   	 	do {myParser.next(); 
		    	String l = null;
		    	
				if (myParser.getEventType() == XmlPullParser.START_TAG)
		      		{l=myParser.getName();
		    	    if (l.equals("Rate")) 
		    		i=1; 
		    		do 
		    			myParser.next(); 
		    		while (myParser.getEventType() != XmlPullParser.TEXT);
	    		
		    		l2= myParser.getText();}
		    	   	}while (i ==0);
		    				  
		    	
		    	
		    myParser.next();
		}
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return l2;
	}

	protected void onPostExecute(String result) {
		//call back data to main thread
		pDialog.dismiss();
		activity.callBackData(result);
		
	}

	

}
