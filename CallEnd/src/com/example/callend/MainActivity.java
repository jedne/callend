package com.example.callend;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends Activity
{
	// 记录黑名单的集合  
    ArrayList<String> blockList = new ArrayList<String>();  
    // 声明电话管理器对象  
    TelephonyManager tManager;  
    // 声明监听通话状态的监听器  
    CustomPhoneCallListener cpListener;  
  
    public class CustomPhoneCallListener extends PhoneStateListener
    {  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) 
        {  
            switch (state)
            {  
            case TelephonyManager.CALL_STATE_IDLE:  
                break;  
            case TelephonyManager.CALL_STATE_OFFHOOK:  
                break;  
            case TelephonyManager.CALL_STATE_RINGING:  
            	Log.v("jeden", "来电话了");
                // 如果该号码属于黑名单  
                if (isBlock(incomingNumber))
                {  
                    try 
                    {  
                        Method method = Class.forName(  
                                "android.os.ServiceManager").getMethod(  
                                "getService", String.class);  
                        // 获取远程TELEPHONY_SERVICE的IBinder对象的代理  
                        IBinder binder = (IBinder) method.invoke(null,  
                                new Object[] { TELEPHONY_SERVICE });  
                        // 将IBinder对象的代理转换为ITelephony对象  
                        ITelephony telephony = ITelephony.Stub  
                                .asInterface(binder);  
                        // 挂断电话  
                        telephony.endCall(); 
                        Log.v("jeden", "拦截成功:"+incomingNumber);
                    } 
                    catch (Exception e) 
                    {  
                        e.printStackTrace();  
                    }  
  
                }  
                break;  
            }  
            super.onCallStateChanged(state, incomingNumber);  
        }  
    }  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 初始化黑名单集合  
        initBlockList();  
        // 获取系统的TelephonyManager管理器  
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
        cpListener = new CustomPhoneCallListener();  
        // 通过TelephonyManager监听通话状态的改变  
        tManager.listen(cpListener, PhoneStateListener.LISTEN_CALL_STATE); 
	}
	
	/** 
     * 初始化黑名单集合 
     */  
    private void initBlockList()
    {  
        blockList.add("18321380268");  
        blockList.add("15555215554");  
        blockList.add("15680768383");  
        blockList.add("15680768284");  
        blockList.add("15680768385");  
        blockList.add("15680768386");  
        blockList.add("15680768387");  
        blockList.add("15680768388");  
        blockList.add("15680768389");  
    }  
    
	/** 
     * 判断某个电话号码是否在黑名单之内 
     *  
     * @param phone 
     *            来电号码 
     * @return 
     */  
    public boolean isBlock(String phone)
    {  
        for (String s1 : blockList) 
        {  
            if (s1.equals(phone))
            {  
                return true;  
            }  
        }  
        return false;  
    } 
}
