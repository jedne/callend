package com.jeden.tel.main;

import android.view.View;
import android.widget.ListView;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class FragmentTel extends BaseFragment
{
	public static final String FLAGS = "FRAGMENT_TEL";
	@Override
	public void initView(View rootView)
	{
		listview = (ListView)rootView.findViewById(R.id.content_listview);
		adapter = new ListViewAdapter(getActivity(), ListViewAdapter.STATE_TEL);
		listview.setAdapter(adapter);
	}

	@Override
	public void bottomBtnClick() 
	{
		DataBean.getInstance().delTel();
		refreshListView();
	}

	@Override
	public String getFragmentFlag() 
	{
		return FLAGS;
	}
}
