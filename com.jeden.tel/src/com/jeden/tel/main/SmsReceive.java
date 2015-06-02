package com.jeden.tel.main;

import com.jeden.tel.tools.Tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SmsReceive extends BroadcastReceiver
{
	public static final String ACTION_SMS_RECEIVE = "android.provider.Telephony.SMS_RECEIVED";
	
	public void onReceive(Context context, Intent intent) 
	{
		String actionName = intent.getAction();
		if (actionName.equals(ACTION_SMS_RECEIVE))
		{
			StringBuffer SMSAddress = new StringBuffer();
			StringBuffer SMSContent = new StringBuffer();
			
			Bundle bundle = intent.getExtras();
			if (bundle != null) 
			{
				Object[] myOBJpdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[myOBJpdus.length];
				for (int i = 0; i < myOBJpdus.length; i++) 
				{
					messages[i] = SmsMessage
							.createFromPdu((byte[]) myOBJpdus[i]);
				}
				for (SmsMessage message : messages) 
		        {
		            SMSAddress.append(message  
		                    .getDisplayOriginatingAddress());  
		            SMSContent.append(message.getDisplayMessageBody());  
		            Tool.BfLog( "收到的短信：："+"来信号码：" + SMSAddress + "\n短信内容："  
		                    + SMSContent);
		            SharedPreferences sp = context.getSharedPreferences("jeden_tel",
			    			MainActivity.MODE_PRIVATE);
		            String tels = sp.getString("tels", null);
		            if(tels != null)
		            {
		            	String[] telstemp = tels.split(",");
		            	for(String temp:telstemp)
		            	{
		            		if(temp.contains(SMSAddress))
		            		{
		            			String hestory = sp.getString("hestory", null);
		            			hestory = hestory + "\n 收到："+SMSAddress + "发的短信："+SMSContent;
		            			sp.edit().putString("hestory", hestory).commit();
		    	            	abortBroadcast();
		            		}
		            	}
		            }
	            }
			}
		}
	}
}
