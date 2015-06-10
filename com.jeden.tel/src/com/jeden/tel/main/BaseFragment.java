package com.jeden.tel.main;

import com.jeden.tel.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public abstract class BaseFragment extends Fragment 
{
	// Fragment布局中的列表
	public ListView listview = null;
	
	// 列表的适配器
	public ListViewAdapter adapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
		initView(rootView);
		
		MainListener.getInstance().registerChangeListener(getFragmentFlag(), this);
		
		return rootView;
	}
	
	@Override
	public void onDestroyView() 
	{
		MainListener.getInstance().unRegisterChangeListener(getFragmentFlag());
		super.onDestroyView();
	}

	/**
	 * 初始化组件信息
	 * 
	 * @param rootView
	 * 			Fragment的根视图
	 */
	public abstract void initView(View rootView);
	
	/**
	 * 刷新listview视图上的数据
	 */
	public void refreshListView()
	{
		if(adapter == null)
		{
			return;
		}
		
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 底部按钮点击事件处理
	 */
	public abstract void bottomBtnClick();
	
	/**
	 * 获取子类标示
	 * 
	 * @return 标志
	 */
	public abstract String getFragmentFlag();
}
