package com.jeden.tel.main;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.jeden.tel.R;
import com.jeden.tel.tools.DataBean;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private Button title_msg, title_tel, title_black;
	
	private ImageView image;
	
	private ViewPager viewpager;
	
	private ArrayList<Fragment> fragmentlist;
	
	private int screenW;
	
	private MarginLayoutParams imageLayoutParams;
	
	public Button bottomBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        initTitle();
        
        initImage();
        
        initViewPager();
    }
    
    /**
     * ��ʼ�����
     */
    public void init()
    {
    	// ������̨���񣬼�������
    	Intent sintent = new Intent(this, MainService.class);
    	sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
    	startService(sintent);
    	
    	// ��ʼ������
    	DataBean.getInstance().init(this);
    }

    /**
	 * ��ʼ������
	 */
	private void initTitle()
	{
		title_tel = (Button) findViewById(R.id.title_tel);
		title_msg = (Button) findViewById(R.id.title_msg);
		title_black = (Button) findViewById(R.id.title_black);
		bottomBtn = (Button) findViewById(R.id.bottom_btn);
		
		title_tel.setOnClickListener(new BtnListener(0));
		title_msg.setOnClickListener(new BtnListener(1));
		title_black.setOnClickListener(new BtnListener(2));
		bottomBtn.setOnClickListener(this);
	}
	
	public class BtnListener implements View.OnClickListener
	{
		private int index = 0;
		
		public BtnListener(int i)
		{
			index = i;
		}

		@Override
		public void onClick(View v) 
		{
			viewpager.setCurrentItem(index);
			setBottomBtnState(index);
		}
	}
	
	/**
	 * ��ʼ��������
	 */
	private void initImage()
	{
		image = (ImageView) findViewById(R.id.title_cursor);
		image.setBackgroundColor(Color.parseColor("#A0A0A0"));
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		screenW = wm.getDefaultDisplay().getWidth();
        imageLayoutParams = (MarginLayoutParams)image.getLayoutParams();
        imageLayoutParams.width = screenW/3;
        imageLayoutParams.leftMargin = 0;
        image.setLayoutParams(imageLayoutParams);
	}
	
	/**
	 * ��ʼ������ҳ��
	 */
	private void initViewPager()
	{
		viewpager = (ViewPager) findViewById(R.id.content_view);
		
		fragmentlist = new ArrayList<Fragment>();
		fragmentlist.add(FragmentTel.getInstance());
		fragmentlist.add(FragmentMsg.getInstance());
		fragmentlist.add(FragmentBlack.getInstance());
		
		viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentlist));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new myOnPagerChangeListener());
		
		setBottomBtnState(0);
	}

	public class myOnPagerChangeListener implements OnPageChangeListener
	{
		int item;
		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
			imageLayoutParams.leftMargin = (int )(screenW/3 * arg0  + arg1 * screenW / 3);
			image.setLayoutParams(imageLayoutParams);
		}

		@Override
		public void onPageSelected(int arg0)
		{
			setBottomBtnState(arg0);
		}
	}
	
    /**
     * ���Ӻ�����������ռ�¼
     */
    public void addOrDelete()
    {
    	int index = viewpager.getCurrentItem();
    	
    	if(index == 2)
    	{
    		// ������Ϣ
    		FragmentDialog dialog = FragmentDialog.getInstance();
    		dialog.show(getSupportFragmentManager(), "dialog");
    	}
    	else if(index == 1)
    	{
    		DataBean.getInstance().delMsg();
    		refreshFragment(index);
    	}
    	else if(index == 0)
    	{
    		DataBean.getInstance().delTel();
    		refreshFragment(index);
    	}
    }
    
	public void onClick(View v) 
	{
		if(v == bottomBtn)
		{
			addOrDelete();
		}
	}
	
	public void setBottomBtnState(int index)
	{
		if(index == 2)
		{
			bottomBtn.setText("����");
		}
		else
		{
			bottomBtn.setText("���");
		}
	}
	
	public void refreshFragment(int index)
	{
		if(index == 0)
		{			
			((FragmentTel)fragmentlist.get(index)).refreshListView();
		}
		else if(index == 1)
		{
			((FragmentMsg)fragmentlist.get(index)).refreshListView();
		}
		else if(index == 2)
		{
			((FragmentBlack)fragmentlist.get(index)).refreshListView();
		}
	}
}