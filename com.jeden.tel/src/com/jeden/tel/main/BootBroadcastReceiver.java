package com.jeden.tel.main;

import com.jeden.tel.tools.Tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver
{
	// 系统开机广播
	private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";  
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Tool.BfLog("Boot this system , BootBroadcastReceiver onReceive()");  
		 
		// 接到开机广播
        if (intent.getAction().equals(ACTION_BOOT))
        {  
        	Intent sintent = new Intent(context, MainService.class);
        	sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        	context.startService(sintent);
        } 
	}
}
