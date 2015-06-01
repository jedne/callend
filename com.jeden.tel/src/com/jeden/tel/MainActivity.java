package com.jeden.tel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
	public String tels;
	public String hestory;
	
	public Button add;
	public Button delete;
	
	public Button clear;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init()
    {
    	SharedPreferences sp = this.getSharedPreferences("jeden_tel",
    			MainActivity.MODE_PRIVATE);
    	// 获取已经添加的电话
		tels = sp.getString("tels", "");
    	// 获取已经拦截到的内容
		hestory = sp.getString("hestory", "");
		
		// 显示
		EditText teledit = (EditText)findViewById(R.id.tels);
		EditText hestoryeidt = (EditText)findViewById(R.id.hestory);
		teledit.setText(tels);
		hestoryeidt.setText(hestory);
		
    	// 注册监听事件
    	add = (Button) findViewById(R.id.add_tel);
    	delete = (Button) findViewById(R.id.cancel_tel);
    	clear = (Button) findViewById(R.id.clear_tel);
    	
    	add.setOnClickListener(this);
    	delete.setOnClickListener(this);
    	clear.setOnClickListener(this);
    }
    public void addOrDelete(boolean delete)
    {
    	EditText addedit = (EditText)findViewById(R.id.add_edit);
    	String teltemp = addedit.getText().toString();
    	if(delete)
    	{
    		int star = tels.indexOf(teltemp);
    		int end = star + teltemp.length()+1;
    		if(star >=0)
    		{    			
    			String one = tels.substring(0,star);
    			String two = tels.substring(end);
    			tels = one + two;
    		}
    	}
    	else
    	{
    		tels = tels + teltemp + ",\n";
    	}
    	SharedPreferences sp = this.getSharedPreferences("jeden_tel",
    			MainActivity.MODE_PRIVATE);
    	sp.edit().putString("tels", tels).commit();
    	EditText teledit = (EditText)findViewById(R.id.tels);
    	teledit.setText(tels);
    }
    
	public void onClick(View v) 
	{
		if(v == add)
		{
			addOrDelete(false);
		}
		else if(v == delete)
		{
			addOrDelete(true);
		}
		else if(v == clear)
		{
			SharedPreferences sp = this.getSharedPreferences("jeden_tel",
	    			MainActivity.MODE_PRIVATE);
			sp.edit().putString("hestory", "").commit();
			EditText hestoryedit = (EditText)findViewById(R.id.hestory);
			hestoryedit.setText("");
		}
	}

    
}
