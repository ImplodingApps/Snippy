package org.implodingapps.snippy;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ClipboardMonitor extends Service 
{
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		Log.d("Snippy", "ClipboardMonitor service started");
		//Note: Will only ever be called once
		
		//PUT YOUR CODE HERE ABHI
		//Gets a Clipboard Manager Object
		final ClipboardManager clippy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	
		//Registers a OnPrimaryClipChanged Listener
		clippy.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener()
				{
					@Override
					public void onPrimaryClipChanged() //This is a callback (if the clip is changed, this is called
					{
						Log.d("Snippy", "New copypasta!" + clippy.getPrimaryClip());
						
					}
				}
		);
		
		//Add a toast notification if the contents of the clipboard are changed
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		return Service.START_NOT_STICKY;
	}
	
	@Override
    public void onDestroy() {
		Log.d("Snippy", "ClipboardMonitor service ended");
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}
