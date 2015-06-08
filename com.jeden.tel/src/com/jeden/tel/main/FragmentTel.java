package com.jeden.tel.main;

import com.jeden.tel.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentTel extends Fragment
{
	private ListView listview = null;
	
	private ListViewAdapter adapter = null;
	
	public static FragmentTel getInstance()
	{
		FragmentTel fragment = new FragmentTel();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
		initView(rootView);
		return rootView;
	}
	
	private void initView(View rootView)
	{
		listview = (ListView)rootView.findViewById(R.id.content_listview);
		adapter = new ListViewAdapter(getActivity(), ListViewAdapter.STATE_TEL);
		listview.setAdapter(adapter);
	}
	
	public void refreshListView()
	{
		adapter.notifyDataSetChanged();
	}
}
