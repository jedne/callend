package com.jeden.tel.main;

import android.view.View;
import android.widget.ListView;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class FragmentMsg extends BaseFragment 
{
	public static final String FLAGS = "FRAGMENT_MSG";
	
	public static FragmentMsg getInstance()
	{
		FragmentMsg fragment = new FragmentMsg();
		return fragment;
	}
	
	@Override
	public void initView(View rootView)
	{
		listview = (ListView)rootView.findViewById(R.id.content_listview);
		adapter = new ListViewAdapter(getActivity(), ListViewAdapter.STATE_MSG);
		listview.setAdapter(adapter);
	}

	@Override
	public void bottomBtnClick()
	{
		DataBean.getInstance().delMsg();
		refreshListView();
	}

	@Override
	public String getFragmentFlag()
	{
		return FLAGS;
	}
}
