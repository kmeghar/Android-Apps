/* 
 * Home Work 3
 * AsyncGetPhoto.java
 * Group N:
 * Roopesh Vemoori #800838641
 * Megha Shyam Reddy Kudumala #800817903
 */
package com.example.photogallery;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;


public class AsyncGetPhoto extends AsyncTask<String, Void, Drawable> {

	ProgressDialog pd;
	Drawable d;
	SetPhotoViews activity;
	
	public AsyncGetPhoto(SetPhotoViews activity){
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(activity.getMode().equals(MainActivity.PHOTO_MODE)){
		pd = new ProgressDialog(activity.getContext());
		pd.setCancelable(false);
		pd.setMessage("Loading Image");
		pd.show();
		//imageMap = new HashMap<Integer, Drawable>();
		}
	}
	@Override
	protected void onPostExecute(final Drawable result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(activity.getMode().equals(MainActivity.PHOTO_MODE)){
		pd.dismiss();
		} 
			activity.setPhotoView(result);
	}
	@Override
	protected Drawable doInBackground(String... params) {
		// TODO Auto-generated method stub		
		try {
			URL url = new URL(params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream is = con.getInputStream();
				d = Drawable.createFromStream(is, "srcName");
				if(activity.getMode().equals(MainActivity.PHOTO_MODE)){
					activity.addDrawabletoCache(d);
					} 
				
				return d;
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
	static public interface SetPhotoViews{
		public void setPhotoView(Drawable d);
		public Context getContext();
		public String getMode();
		public void addDrawabletoCache(Drawable d);
	}

}
