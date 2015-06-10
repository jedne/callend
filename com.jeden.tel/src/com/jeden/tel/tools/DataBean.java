package com.jeden.tel.tools;

import com.jeden.tel.main.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class DataBean 
{
	// 黑名单信息
	private String[] black = null;
	
	// 来电拦截记录
	private String[] telhistory = null;
	
	// 短信拦截记录
	private String[] msghistory = null;
	
	// 黑名单信息
	private String tempBlack = null;
	
	// 来电拦截记录
	private String tempTel = null;
	
	// 短信拦截记录
	private String tempMsg = null;
	
	// 单例
	private static DataBean instance = new DataBean();
	
	// 上下文
	private Context context = null;
	
	private DataBean()
	{
	}
	
	public static DataBean getInstance()
	{
		return instance;
	}
	
	/**
	 * 初始化数据信息
	 * 
	 * @param context
	 * 			上下文环境
	 */
	public void init(Context context)
	{
		this.context = context;
		
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		
		tempBlack = sp.getString(Config.SHARE_BLACK_NAME, "");
		black = tempBlack.split(";");
		
		tempTel = sp.getString(Config.SHARE_TEL_NAME, "");
		telhistory = tempTel.split(";");
		
		tempMsg = sp.getString(Config.SHARE_MSG_NAME, "");
		msghistory = tempMsg.split(";");
	}
	
	/**
	 * 获取黑名单（字符串）
	 * 
	 * @return 完整的黑名单信息
	 */
	public String getBlackString()
	{
		return tempBlack;
	}
	
	/**
	 * 获取拦截到的电话信息（字符串）
	 * 
	 * @return 完整的电话记录信息
	 */
	public String getTelString()
	{
		return tempTel;
	}
	
	/**
	 * 获取拦截到的短信信息（字符串）
	 * 
	 * @return 完整的短信信息
	 */
	public String getMsgString()
	{
		return tempMsg;
	}
	
	/**
	 * 获取黑名单（数组）
	 * 
	 * @return 完整的黑名单信息
	 */
	public String[] getBlackList()
	{
		return black;
	}
	
	/**
	 * 获取拦截到的电话信息（数组）
	 * 
	 * @return 完整的电话记录信息
	 */
	public String[] getTelHistory()
	{
		return telhistory;
	}
	
	/**
	 * 获取拦截到的短信信息（数组）
	 * 
	 * @return 完整的短信信息
	 */
	public String[] getMsgHistory()
	{
		return msghistory;
	}
	
	/**
	 * 获取黑名单中的一个
	 * 
	 * @param index
	 * 			数组中的位置
	 * @return 一个黑名单的信息
	 */
	public String getBlackItem(int index)
	{
		return black[index];
	}
	
	/**
	 * 获取来电信息中的一个
	 * 
	 * @param index
	 * 			数组中的位置
	 * @return	一个来电的信息
	 */
	public String getTelItem(int index)
	{
		return telhistory[index];
	}
	
	/**
	 * 获取短信信息中的一个
	 * 
	 * @param index
	 * 			数组中的位置
	 * @return 一个短信信息
	 */
	public String getMsgItem(int index)
	{
		return msghistory[index];
	}
	
	/**
	 * 添加到黑名单
	 * 
	 * @param item
	 * 			一个黑名单信息
	 */
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
	
	/**
	 * 添加到来电信息
	 * 
	 * @param item
	 * 			一个来电信息
	 */
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
		telhistory = tempTel.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_TEL_NAME, tempTel).commit();
	}
	
	/**
	 * 添加到短信信息
	 * 
	 * @param item
	 * 			一个短信信息
	 */
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
		msghistory = tempMsg.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_MSG_NAME, tempMsg).commit();
	}
	
	/**
	 * 删除一条黑名单信息
	 * 
	 * @param index
	 * 			在数组中的位置
	 */
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
	
	/**
	 * 删除所有来电信息
	 */
	public void delTel()
	{
		tempTel = "";
		telhistory = tempTel.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_TEL_NAME, tempTel).commit();
	}
	
	/**
	 * 删除所有短信信息
	 */
	public void delMsg()
	{
		tempMsg = "";
		msghistory = tempMsg.split(";");
		SharedPreferences sp = context.getSharedPreferences(Config.SHARE_NAME, 
				MainActivity.MODE_PRIVATE);
		sp.edit().putString(Config.SHARE_MSG_NAME, tempMsg).commit();
	}
}
