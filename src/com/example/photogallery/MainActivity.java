/* 
 * Home Work 3
 * MainActivity.java
 * Group N:
 * Roopesh Vemoori #800838641
 * Megha Shyam Reddy Kudumala #800817903
 */
package com.example.photogallery;

import java.util.ArrayList;

import com.example.photogallery.AsyncGetPhotoURL.SetActivityViews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements SetActivityViews {

	Button button1, button2;
	ArrayList<String> UrlList;
	Intent intent;
	final static String PHOTO_MODE ="Photos mode";
	final static String SLIDE_MODE ="Slide Show mode";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this, PhotoActivity.class);
				intent.putStringArrayListExtra("UrlList", UrlList);
				intent.putExtra(PHOTO_MODE,PHOTO_MODE);
				startActivity(intent);
			}
		});
        button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this, PhotoActivity.class);
				intent.putStringArrayListExtra("UrlList", UrlList);
				intent.putExtra(SLIDE_MODE,SLIDE_MODE);
				startActivity(intent);
			}
		});
        new AsyncGetPhotoURL(this).execute("http://liisp.uncc.edu/~mshehab/api/photos.txt");
    }
	
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	/*@Override
	public ArrayList<String> getUrlList() {
		// TODO Auto-generated method stub
		return this.UrlList;
	}*/

	@Override
	public void setUrlList(ArrayList<String> UrlList) {
		// TODO Auto-generated method stub
		this.UrlList = UrlList;
	}
}
