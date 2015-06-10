package com.jeden.tel.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.jeden.tel.tools.DataBean;
import com.jeden.tel.tools.Tool;

/**
 * 短信广播的监听
 * 
 * @author jeden
 *
 */
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
		            String[] telstemp = DataBean.getInstance().getBlackList();
	            	for(String temp:telstemp)
	            	{
	            		if(temp.contains(SMSAddress))
	            		{
	            			// 添加拦截信息
	            			DataBean.getInstance().addMsgItem(SMSAddress + "," + SMSContent);
	            			
	            			MainListener.getInstance().refreshFragmentList(FragmentMsg.FLAGS);
	            			
	    	            	abortBroadcast();
	            		}
	            	}
	            }
			}
		}
	}
}
