package com.jeden.tel.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

public class Tool 
{
	/**
	 * 获取根目录路径（如果有T卡优先T卡）
	 */
	public static String getRootFilePathHeader()
	{
		String path = null;
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED))
		{
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}
	
	/**
	 * 打印log并保存log文件
	 */
	public static void BfLog(String content)
	{
		Log.v("jeden", content);
		if(null == content || content.length() == 0)
		{
			content = "null";
		}
		Time time = new Time("GMT+8");    
        time.setToNow(); 
        
        content = time.year+"年"+time.month+"月"+time.monthDay+"日"+time.hour+"时"
        				+time.minute+"分"+time.second+"秒:"+content;
		saveStrToSDcard(content);
	}
	
	/**
	 * 将字串存储到 sdcard
	 * 
	 * @param content
	 */
	public static void saveStrToSDcard(String content)
	{
		String header = getRootFilePathHeader();
		if(header == null)
		{
			return;
		}
		String path =  header + "/tellog";
		
		File file = new File(path);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			if(file.length() >=1024*1024*1)
			{
				file.delete();
				try
				{
					file.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		BufferedWriter out=null;

		try
		{
			out = new BufferedWriter(new FileWriter(file,true));
			out.write(content);
			out.newLine(); 
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}