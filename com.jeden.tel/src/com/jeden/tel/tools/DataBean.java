package com.jeden.tel.tools;

import com.jeden.tel.main.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class DataBean 
{
	private String[] black = null;
	
	private String[] telhestory = null;
	
	private String[] msghestory = null;
	
	private String tempBlack = null;
	
	private String tempTel = null;
	
	private String tempMsg = null;
	
	private static DataBean instance = new DataBean();
	
	private Context context = null;
	
	public static DataBean getInstance()
	{
		return instance;
	}
	
	public void init(Context context)
	{
		this.context = context;
		
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		tempBlack = sp.getString(Config.SHARE_BLACK_NAME, "");
		black = tempBlack.split(";");
		
		tempTel = sp.getString(Config.SHARE_TEL_NAME, "");
		telhestory = tempTel.split(";");
		
		tempMsg = sp.getString(Config.SHARE_MSG_NAME, "");
		msghestory = tempMsg.split(";");
	}
	
	public String getBlackString()
	{
		return tempBlack;
	}
	
	public String getTelString()
	{
		return tempTel;
	}
	
	public String getMsgString()
	{
		return tempMsg;
	}
	
	public String[] getBlackList()
	{
		return black;
	}
	
	public String[] getTelHestory()
	{
		return telhestory;
	}
	
	public String[] getMsgHestory()
	{
		return msghestory;
	}
	
	public String getBlackItem(int index)
	{
		return black[index];
	}
	
	public String getTelItem(int index)
	{
		return telhestory[index];
	}
	
	public String getMsgItem(int index)
	{
		return msghestory[index];
	}
	
	public void addBlackItem(String item)
	{
		if(item == null || item.indexOf(",") < 0 || tempBlack.contains(item))
		{
			return;
		}
		
		if(!item.contains(";"))
		{
			item += ";";
		}
		
		tempBlack += item;
		black = tempBlack.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_BLACK_NAME, tempBlack).commit();
	}
	
	public void addTelItem(String item)
	{
		if(item == null || item.indexOf(",") < 0)
		{
			return;
		}
		
		if(!item.contains(";"))
		{
			item += ";";
		}
		
		tempTel += item;
		telhestory = tempTel.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_TEL_NAME, tempTel).commit();
	}
	
	public void addMsgItem(String item)
	{
		if(item == null || item.indexOf(",") < 0)
		{
			return;
		}
		
		if(!item.contains(";"))
		{
			item += ";";
		}
		
		tempMsg += item;
		msghestory = tempMsg.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_MSG_NAME, tempMsg).commit();
	}
	
	public void delBlackItem(int index)
	{
		if(black.length <= index)
		{
			return;
		}
		
		String item = black[index];
		tempBlack = tempBlack.replace(item + ";", "");
		black = tempBlack.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_BLACK_NAME, tempBlack).commit();
	}
	
	public void delTel()
	{
		tempTel = "";
		telhestory = tempTel.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_TEL_NAME, tempTel).commit();
	}
	
	public void delMsg()
	{
		tempMsg = "";
		msghestory = tempMsg.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_MSG_NAME, tempMsg).commit();
	}
}
