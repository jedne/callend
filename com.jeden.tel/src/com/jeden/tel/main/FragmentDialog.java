package com.jeden.tel.main;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;
import com.jeden.tel.tools.Tool;

public class FragmentDialog extends DialogFragment implements OnClickListener, OnItemClickListener
{
	private Button addBtn = null;
	
	private EditText edit = null;
	
	private ListView listview = null;
	
	private static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;  
	
	private static final String _ID = ContactsContract.Contacts._ID;  
	
	private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;  
	
	private static final String CONTACT_ID = ContactsContract.Data.CONTACT_ID; 
	
	private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER; 
	
	private static final Uri PHONES_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; 
	
	private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;  

	private String[] contacts = null;
	
	public static FragmentDialog getInstance()
	{
		FragmentDialog fragment = new FragmentDialog();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
		super.onCreate(savedInstanceState);
		
		// 初始化联系人信息
		// TODO
		initContacts();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_dialog_layout, container, false);
		initView(rootView);
		return rootView;
	}
	
	/**
	 * 初始化布局
	 * 
	 * @param rootView
	 * 			Fragment的根视图
	 */
	public void initView(View rootView)
	{
		addBtn = (Button) rootView.findViewById(R.id.add_btn);
		addBtn.setOnClickListener(this);
		edit = (EditText) rootView.findViewById(R.id.input_view);
		listview = (ListView) rootView.findViewById(R.id.tel_listview);
		listview.setAdapter(new ListViewAdapter(getActivity(), contacts));
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View view) 
	{
		if(view == addBtn)
		{
			String phone = edit.getText().toString();
			if(phone == null || phone.length() != 11)
			{
				return;
			}
			
			DataBean.getInstance().addBlackItem("自定义," + phone);
			
			MainListener.getInstance().refreshFragmentList(FragmentBlack.FLAGS);
			
			Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();
			this.dismiss();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		DataBean.getInstance().addBlackItem(contacts[position]);

		MainListener.getInstance().refreshFragmentList(FragmentBlack.FLAGS);
		
		Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();
		this.dismiss();
	}
	
	/**
	 * 初始化联系人信息
	 */
	public void initContacts()
	{
		StringBuilder sb = new StringBuilder();
		
		ContentResolver resolver = getActivity().getContentResolver();  
        Cursor c = resolver.query(CONTACTS_URI, null, null, null, null);  
        while (c.moveToNext()) 
        {  
            int _id = c.getInt(c.getColumnIndex(_ID));  
            String displayName = c.getString(c.getColumnIndex(DISPLAY_NAME));  
            
            sb.append(displayName + ",");
            Tool.BfLog("FragmentDialog initContacts displayName=" + displayName);
            
            String selection = CONTACT_ID + "=" + _id; 
              
            //获取手机号  
            int hasPhoneNumber = c.getInt(c.getColumnIndex(HAS_PHONE_NUMBER));  
            if (hasPhoneNumber > 0)
            {  
                Cursor  phc = resolver.query(PHONES_URI, null, selection, null, null);  
                while (phc.moveToNext())
                {  
                    String phoneNumber = phc.getString(phc.getColumnIndex(PHONE_NUMBER));
                    
                    sb.append(phoneNumber + ";");
                    if(!phc.isLast())
                    {
                    	sb.append(displayName + ",");
                    }
                }  
                phc.close();  
            }
        }  
        c.close();  
        
        Tool.BfLog("FragmentDialog initContacts sb=" + sb.toString());
        contacts = sb.toString().split(";");
	}
}
