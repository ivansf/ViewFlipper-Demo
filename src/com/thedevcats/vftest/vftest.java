package com.thedevcats.vftest;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class vftest extends Activity implements OnClickListener {
	
	ViewFlipper ll;
	private static final int SWIPE_MIN_DISTANCE = 320;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		// let's hide the title and status bar. This should be called before adding content.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                	Toast.makeText(vftest.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        };

		/**
		 * Creating all the Views we will add to the ViewFlipper
		 */
        TextView tv1 = new TextView(this);
        tv1.layout(50, 50, 50, 50);
        tv1.setVisibility(View.VISIBLE);
        tv1.setText("testing this thing");
        tv1.setOnClickListener(vftest.this);
        tv1.setOnTouchListener(gestureListener);
        
        LayoutInflater inflater = this.getLayoutInflater();
        WebView iwb = (WebView) inflater.inflate(R.layout.webview, null);
        iwb.loadUrl("http://upload.wikimedia.org/wikipedia/commons/a/ad/Gretag-Macbeth_ColorChecker.jpg");
        iwb.setOnClickListener(vftest.this);
        iwb.setOnTouchListener(gestureListener);
        
        WebView wb = new WebView(this);
        wb.layout(50, 50, 350, 50);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new HelloWebViewClient());
        wb.loadUrl("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png");
        wb.setOnClickListener(vftest.this);
        wb.setOnTouchListener(gestureListener);
        
        
        WebView wb1 = new WebView(this);
        wb1.layout(50, 50, 350, 50);
        wb1.getSettings().setJavaScriptEnabled(true);
        wb1.setWebViewClient(new HelloWebViewClient());
        wb1.setInitialScale(50);
        wb1.loadUrl("http://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Plazadearma.jpg/800px-Plazadearma.jpg");
        wb1.setOnClickListener(vftest.this);
        wb1.setOnTouchListener(gestureListener);
        
        
        WebView wb2 = new WebView(this);
        wb2.layout(50, 50, 350, 50);
        wb2.getSettings().setJavaScriptEnabled(true);
        wb2.setWebViewClient(new HelloWebViewClient());
        wb2.loadUrl("http://upload.wikimedia.org/wikipedia/en/8/82/Img_HistMatch_SourceAfter.jpg");
        wb2.setOnClickListener(vftest.this);
        wb2.setOnTouchListener(gestureListener);
        
        
        WebView wb3 = new WebView(this);
        wb3.layout(50, 50, 350, 50);
        wb3.getSettings().setJavaScriptEnabled(true);
        wb3.setWebViewClient(new HelloWebViewClient());
        wb3.loadUrl("http://google.com");
        wb3.setOnClickListener(vftest.this);
        wb3.setOnTouchListener(gestureListener);

		// Adding the views to the layout
        ll = (ViewFlipper) findViewById(R.id.layout);
        ll.addView(tv1);
        ll.addView(iwb);
        ll.addView(wb);
        ll.addView(wb1);
        ll.addView(wb2);
        ll.addView(wb3);

		/**
		 * defining default animations.
		 * These animations can be found in res/anim/*
		 *
		 * Animations speed can be changed in the animation files.
		 */
        ll.setInAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_in));
        ll.setOutAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_left_out));

		// you can make it auto flip
        //ll.startFlipping();
    }

	@Override
	public void onClick(View view) {
		// add some magic here if you want.
	}

	/**
	 * Extending a gesture listener to make it respond to long swipes.
	 * The swipe minimun distances can be set at the top.
	 */
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                	// right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(vftest.this, "Left Swipe", Toast.LENGTH_SHORT).show();
					ll.setInAnimation(AnimationUtils.loadAnimation(vftest.this, R.anim.push_left_in));
					ll.setOutAnimation(AnimationUtils.loadAnimation(vftest.this, R.anim.push_left_out));
                    ll.showPrevious();
                    
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(vftest.this, "Right Swipe", Toast.LENGTH_SHORT).show();
					ll.setInAnimation(AnimationUtils.loadAnimation(vftest.this, R.anim.push_right_in));
					ll.setOutAnimation(AnimationUtils.loadAnimation(vftest.this, R.anim.push_right_out));
                    ll.showNext();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }
    
    
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}