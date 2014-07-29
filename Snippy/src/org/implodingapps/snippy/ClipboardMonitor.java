package org.implodingapps.snippy;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("NewApi") //TODO: Fix this
public class ClipboardMonitor extends Service 
{
	private Resources res;
	private ClipboardManager clippy;
	private NotificationManager nm;
	
	private OnPrimaryClipChangedListener cclistener;
	
	final int NOTIF_ID = 1;
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressLint("NewApi") //TODO: Fix this
	@Override
	public void onCreate() //Note: Will only ever be called once
	{
		/*DeBUG*/
		Log.d("Snippy", "ClipboardMonitor service started");
		
		//Instantiate Variables
		res = getResources();
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		clippy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		
		cclistener = new OnPrimaryClipChangedListener()
		{
			@Override
			public void onPrimaryClipChanged() //This is a callback (if the clip is changed, this is called
			{
				/*DeBUG*/Log.d("Snippy", "New copypasta!" + clippy.getPrimaryClip());
				/*DeBUG*/
			}
		};
		
		//Start a persistent notification
		Notification.Builder nbuilder = new Notification.Builder(this);
		
		nbuilder
			.setPriority(-2) //TODO: Build in safeties for ICS or abandon it
			.setOngoing(true)
			.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.attach)) //TODO: Use actual final icons
			.setContentTitle(res.getString(R.string.Clipboard_Monitor_notification_title))
            .setContentText(res.getString(R.string.Clipboard_Monitor_notification_text));

		Notification n = nbuilder.build();
		//TODO: Build in safeties for ICS or abandon it
		nm.notify(NOTIF_ID, n);
	
		//Registers a OnPrimaryClipChanged Listener
		clippy.addPrimaryClipChangedListener(cclistener);
		
		//Add a toast notification if the contents of the clipboard are changed
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
		Log.d("Snippy", "ClipboardMonitor service ended");
		
		nm.cancel(NOTIF_ID);
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
