package com.jeden.tel.main;

import java.util.HashMap;

public class MainListener 
{
	// 单例
	private static MainListener instance = new MainListener();
	
	// 监听的Fragment的集合
	private HashMap<String, BaseFragment> map = new HashMap<String, BaseFragment>();
	
	private MainListener()
	{
		
	}
	
	public static MainListener getInstance()
	{
		return instance;
	}

	/**
	 * 注册Fragment的监听
	 * 
	 * @param key
	 * 			区别Fragment的标示
	 * @param bf
	 * 			Fragment实例
	 */
	public void registerChangeListener(String key, BaseFragment bf)
	{
		map.put(key, bf);
	}
	
	/**
	 * 解绑Fragment的监听
	 * 
	 * @param key
	 * 			Fragment的标示
	 */
	public void unRegisterChangeListener(String key)
	{
		map.remove(key);
	}
	
	/**
	 * 刷新Fragment中的listview的显示内容
	 * 
	 * @param key
	 * 			Fragment的标示
	 */
	public void refreshFragmentList(String key)
	{
		BaseFragment bf = map.get(key);
		if(bf != null)
		{
			bf.refreshListView();
		}
	}
}
