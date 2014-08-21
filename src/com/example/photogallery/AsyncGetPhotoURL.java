/* 
 * Home Work 3
 * AsyncGetPhotoURL.java
 * Group N:
 * Roopesh Vemoori #800838641
 * Megha Shyam Reddy Kudumala #800817903
 */
package com.example.photogallery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;






import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class AsyncGetPhotoURL extends AsyncTask<String, Void, LinkedList<String>> {

	ArrayList<String> UrlList;
	String line;
	SetActivityViews activity;
	ProgressDialog pd;
	public AsyncGetPhotoURL(SetActivityViews activity){
		this.activity = activity;
	}
	@Override
	protected LinkedList<String> doInBackground(String... params) {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL(params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				UrlList = new ArrayList<String>();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
								
				while((line =in.readLine()) != null){
					UrlList.add(line);
					}
					in.close();
					con.disconnect();
					
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(activity.getContext());
		pd.setCancelable(false);
		pd.setMessage("Retrieving Image URLs...");
		pd.show();
	}
	@Override
	protected void onPostExecute(LinkedList<String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		activity.setUrlList(UrlList);
		pd.dismiss();
	}
	
	static public interface SetActivityViews{
		//public ArrayList<String> getUrlList();
		public void setUrlList(ArrayList<String> UrlList);
		public Context getContext();
	}

}
