package com.jeden.tel.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class ListViewAdapter extends BaseAdapter
{
	public static final int STATE_BLACK = 0;
	
	public static final int STATE_MSG = 1;
	
	public static final int STATE_TEL = 2;
	
	public Context context = null;
	
	public String[] contents = null;
	
	private LayoutInflater mInflater;
	
	private int state = -1;
	
	public ListViewAdapter(Context context, int state)
	{
		this.context = context;
		this.state = state;
		init();
	}
	
	public ListViewAdapter(Context context, String[] contents)
	{
		this.context = context;
		this.contents = contents;
		init();
	}
	
	private void init()
	{
		mInflater = LayoutInflater.from(context);
		
		if(state == STATE_BLACK)
		{
			contents = DataBean.getInstance().getBlackList();
		}
		else if(state == STATE_MSG)
		{
			contents = DataBean.getInstance().getMsgHestory();
		}
		else if(state == STATE_TEL)
		{
			contents = DataBean.getInstance().getTelHestory();
		}
		
		if(contents.length == 1 && contents[0].equals(""))
		{
			contents = new String[0];
		}
	}
	
	@Override
	public int getCount() 
	{
		return contents.length;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewholder = null;
		
		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.fragment_listview_item, null);
			viewholder = new ViewHolder();
			viewholder.titleView = (TextView)convertView.findViewById(R.id.item_title);
			viewholder.contentView = (TextView)convertView.findViewById(R.id.item_msg);
			convertView.setTag(viewholder);
		}
		else
		{
			viewholder = (ViewHolder)convertView.getTag(); 
		}
		
		// ÉèÖÃÄÚÈÝ
		String[] msg = contents[position].split(",");
		
		if(msg.length > 1)
		{
			viewholder.titleView.setText(msg[0]);
			viewholder.contentView.setText(msg[1]);
		}
		return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() 
	{
		init();
		super.notifyDataSetChanged();
	}

	private static class ViewHolder
	{
		TextView titleView;
		TextView contentView;
	}
}
