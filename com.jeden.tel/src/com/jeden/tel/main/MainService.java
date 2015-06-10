package com.jeden.tel.main;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MainService extends Service 
{
    private BroadcastReceiver smsReceive = new SmsReceive();
    
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// ×¢²á¶ÌÐÅ¼àÌý¹ã²¥
		registerSmsReceiver();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		// ½â°ó¶ÌÐÅ¼àÌý¹ã²¥
		unRegisterSmsReceiver();
		super.onDestroy();
	}
	
	/**
	 * ×¢²á¶ÌÐÅ¼àÌý¹ã²¥
	 */
	private void registerSmsReceiver()
	{
	    IntentFilter filter = new IntentFilter();  
        filter.addAction(SmsReceive.ACTION_SMS_RECEIVE);  
        filter.setPriority(Integer.MAX_VALUE);  
        registerReceiver(smsReceive, filter);  
	}
	
	/**
	 * ½â°ó¶ÌÐÅ¼àÌý
	 */
	private void unRegisterSmsReceiver()
	{
		unregisterReceiver(smsReceive);
	}
}
