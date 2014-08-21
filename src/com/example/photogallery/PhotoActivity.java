/* 
 * Home Work 3
 * PhotoActivity.java
 * Group N:
 * Roopesh Vemoori #800838641
 * Megha Shyam Reddy Kudumala #800817903
 */
package com.example.photogallery;

import java.util.ArrayList;



import com.example.photogallery.AsyncGetPhoto.SetPhotoViews;



import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PhotoActivity extends Activity implements SetPhotoViews,
		OnGestureListener {

	ArrayList<String> UrlList;
	RelativeLayout layout;
	Handler mHandler = new Handler();
	ImageView iv;
	String mode;
	int i = 0;
	private static final int SWIPE_THRESHOLD = 100;
	private static final int SWIPE_VELOCITY_THRESHOLD = 100;
	@SuppressWarnings("deprecation")
	GestureDetector gesture = new GestureDetector(this);
	private LruCache<Integer, Drawable> mMemoryCache;
	  final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	  final int cacheSize = maxMemory / 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		layout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		iv = (ImageView) findViewById(R.id.imageView1);
		 mMemoryCache = new LruCache<Integer, Drawable>(cacheSize);
		if (getIntent().getExtras() != null) {
			this.UrlList = getIntent().getStringArrayListExtra("UrlList");
			if (getIntent().getExtras().containsKey(MainActivity.PHOTO_MODE)) {
				mode = getIntent().getStringExtra(MainActivity.PHOTO_MODE);
				new AsyncGetPhoto(this).execute(UrlList.get(i));
			}else if (getIntent().getExtras().containsKey(MainActivity.SLIDE_MODE)) {
				
				mode = getIntent().getStringExtra(MainActivity.SLIDE_MODE);
				 mHandlerTask.run(); 
				}
		}
	}
	Runnable mHandlerTask = new Runnable()
	{
	     @Override 
	     public void run() {
	    	 i++;
	    	 if(i==UrlList.size())
	    		 i=0;
	    	 new AsyncGetPhoto(PhotoActivity.this).execute(UrlList.get(i));
	          mHandler.postDelayed(mHandlerTask, 2000);
	     }
	};
	
	
	@Override
	public void setPhotoView(Drawable d) {
		// TODO Auto-generated method stub
		
		iv.setImageDrawable(d);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub

		boolean result = false;
		try {
			
			float diffX = e2.getX() - e1.getX();
			
			if (Math.abs(diffX) > SWIPE_THRESHOLD
					&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
				if (diffX > 0) {
					onSwipeRight();
				} else {
					onSwipeLeft();
				}
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(mode.equals(MainActivity.PHOTO_MODE))
		return gesture.onTouchEvent(event);
		else
			return super.onTouchEvent(event);
	}

	public void onSwipeRight() {
		i++;
		if (UrlList != null) {
			if(i >UrlList.size()-1)
				i=0;
			if (getDrawableFromMemCache(i) == null) {
					new AsyncGetPhoto(this).execute(UrlList.get(i));
				}else
					setPhotoView(getDrawableFromMemCache(i));
		}

	}

	public void onSwipeLeft() {
		i--;
		if (UrlList != null) {
			
			if(i<0)
				i = UrlList.size()-1;
			if (getDrawableFromMemCache(i) == null) {
			new AsyncGetPhoto(this).execute(UrlList.get(i));
			}else
				setPhotoView(getDrawableFromMemCache(i));
		}
	}

	@Override
	public String getMode() {
		// TODO Auto-generated method stub
		
		return mode;
	}

	@Override
	public void addDrawabletoCache(Drawable d) {
		// TODO Auto-generated method stub
		if (getDrawableFromMemCache(i) == null) {
	        mMemoryCache.put(i, d);
	    }
	}
	public Drawable getDrawableFromMemCache(int key) {
	    return mMemoryCache.get(key);
	}
}
