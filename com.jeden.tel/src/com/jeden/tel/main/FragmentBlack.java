package com.jeden.tel.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class FragmentBlack extends Fragment implements OnItemClickListener
{
	private ListView listview = null;
	
	private ListViewAdapter adapter = null;
	
	public static FragmentBlack getInstance()
	{
		FragmentBlack fragment = new FragmentBlack();
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
		adapter = new ListViewAdapter(getActivity(), ListViewAdapter.STATE_BLACK);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// 弹出确认对话框
		//TODO
		Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
		DataBean.getInstance().delBlackItem(position);
		refreshListView();
	}
	
	public void refreshListView()
	{
		adapter.notifyDataSetChanged();
	}
}
