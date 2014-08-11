package org.implodingapps.snippy;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

@SuppressLint("NewApi") //TODO: Fix this
public class ClipboardMonitor extends Service 
{
	private Resources res;
	private WindowManager windowManager;
	
	private ClipboardManager clippy;	
	private OnPrimaryClipChangedListener cclistener;
	
	private ImageView trigger;
	private WindowManager.LayoutParams params;
	
	private TagCloudView mTagCloudView;
	
	final int NOTIF_ID = 1;
	int triggerPosition;
	String element;
	
	public ArrayList<Tag> createTags()
	{
		ArrayList<Tag> snippet_list = new ArrayList<Tag>();
		for (int i = 0; i < Singleton.snippets.size(); i++)
		{
			element = Singleton.snippets.get(i).parsedText;
			if (Singleton.snippets.get(i).parsedText.length() > 20)
			{
				element.substring(0, 17).concat("...");
			}
			snippet_list.add(new Tag(element, (int) (i / 3), i));
		}
		return snippet_list;
	}
	
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressLint("NewApi") //TODO: Fix this
	@Override
	public void onCreate() //Note: Will only ever be called once
	{	
		//TODO: Populate variables from settings 
		triggerPosition = 1;
		
		//Instantiate Variables
		res = getResources();
		clippy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		//Initialize Singleton
		Singleton.getInstance();
		
		//Start a persistent notification
		startOngoingNotification();
		
		//Start and instantiate the Clipboard Listener
		cclistener = new OnPrimaryClipChangedListener()
		{
			@Override
			public void onPrimaryClipChanged() //This is a callback (if the clip is changed, this is called
			{
				/*DeBUG*/Log.d("Snippy", "New copypasta!" + clippy.getPrimaryClip());
				Singleton.snippets.add(new Snippet(System.currentTimeMillis(), new ClipData(clippy.getPrimaryClip())));
				/*DeBUG*/Log.i("Snippy", "" + Singleton.snippets.get(Singleton.snippets.size() - 1).parsedText);
			}
		};
		
		clippy.addPrimaryClipChangedListener(cclistener);
		
		//Create the floating paste mechanism trigger thing (Shamelessly stolen from http://stackoverflow.com/questions/19846541/what-is-windowmanager-in-android )
		createTrigger();
		
		//Add a toast notification if the contents of the clipboard are changed
	}
	public void startOngoingNotification()
	{
		Notification.Builder nbuilder = new Notification.Builder(this);
		
		nbuilder
			.setPriority(-2) //TODO: Build in safeties for ICS or abandon it
			.setOngoing(true)
			.setSmallIcon(R.drawable.ic_launcher)//TODO: get real icon, this is just to fix a really weird bug
			.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.tumblbeast)) //TODO: Use actual final icons
			.setContentTitle(res.getString(R.string.Clipboard_Monitor_notification_title))
            .setContentText(res.getString(R.string.Clipboard_Monitor_notification_text));

		Notification n = nbuilder.build(); //TODO: Build in safeties for ICS or abandon it

		startForeground(NOTIF_ID, n);
	}
	
	public void createTrigger()
	{
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 0;
		
		trigger = new ImageView(this);
		trigger.setImageBitmap(createTriggerBitmap(triggerPosition));

		
		windowManager.addView(trigger, params);
		
		try {
			trigger.setOnTouchListener(new View.OnTouchListener() {
				private WindowManager.LayoutParams paramsF = params;
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:

						// Get current time in nano seconds.

//						initialX = paramsF.x;
//						initialY = paramsF.y;
//						initialTouchX = event.getRawX();
//						initialTouchY = event.getRawY();
						
						//trigger.setImageBitmap(createTriggerBitmap(5));
						createWordCloud(true);
						break;
					case MotionEvent.ACTION_UP:
						//Record the position the user released the screen
						
						
						//Restore to normal UI
						createWordCloud(false);
						trigger.setImageBitmap(createTriggerBitmap(triggerPosition));
						break;
					case MotionEvent.ACTION_MOVE:
//						paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
//						paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
						windowManager.updateViewLayout(trigger, paramsF);
						break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@SuppressWarnings("deprecation") //I don't care; don't talk to me.
	public void createWordCloud(boolean add)
	{
		if(add)
		{
			params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
					PixelFormat.TRANSLUCENT);
			
			//Step0: to get a full-screen View:
			//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//			WindowManager.LayoutParams.FLAG_FULLSCREEN);
					
			//Step1: get screen resolution:
			Display display = windowManager.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();
					
			mTagCloudView = new TagCloudView(this, width, height, createTags() ); //passing current context
			
			windowManager.addView(mTagCloudView, params);
		}
		
		if(!add){ windowManager.removeView(mTagCloudView);}
	}
	
	public Bitmap createTriggerBitmap(int position)
	{
		int statusBarHeight = (int) Math.ceil(25 * this.getResources().getDisplayMetrics().density);
		int sideBarHeight = (int) Math.ceil(500 * this.getResources().getDisplayMetrics().density);
		
		Bitmap original = BitmapFactory.decodeResource(res,R.drawable.transparent);
		Bitmap resized = null;
		
		params.x = 0;
		if(position == 1 || position == 3)
			params.gravity = Gravity.TOP | Gravity.LEFT;
		if(position == 2 || position == 4)
			params.gravity = Gravity.TOP | Gravity.RIGHT;
		if(position == 1 || position == 2)
		{
			params.y = 0;
			resized = Bitmap.createScaledBitmap(original, statusBarHeight, statusBarHeight, true);
		}
		if(position == 3 || position == 4)
		{
			params.y = statusBarHeight;
			resized = Bitmap.createScaledBitmap(original, statusBarHeight, sideBarHeight, true);
		}
		if(position == 5)
		{
			original = BitmapFactory.decodeResource(res,R.drawable.clippy);
			params.gravity = Gravity.TOP | Gravity.LEFT;
			params.y = 0;
			resized = Bitmap.createScaledBitmap(original, 1200, 1200, true);
		}
		
		//Bitmap resized = Bitmap.createScaledBitmap(original, statusBarHeight, statusBarHeight, true);
		
		Log.i("Snippy", statusBarHeight + "");
		return resized;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		return Service.START_NOT_STICKY;
	}
	
	@Override
    public void onDestroy() 
	{
		clippy.removePrimaryClipChangedListener(cclistener);
		
		/*DeBUG*/ Log.d("Snippy", "Dump: " + Singleton.snippets);
		
		if (trigger != null) windowManager.removeView(trigger);
		
		stopForeground(true);
		
        super.onDestroy();
    }

}
