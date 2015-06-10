package com.jeden.tel.main;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class FragmentBlack extends BaseFragment implements OnItemClickListener
{
	public static final String FLAGS = "FRAGMENT_BLACK";
	@Override
	public void initView(View rootView)
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

	@Override
	public void bottomBtnClick() 
	{
		FragmentDialog dialog = FragmentDialog.getInstance();
		dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}

	@Override
	public String getFragmentFlag()
	{
		return FLAGS;
	}
}
