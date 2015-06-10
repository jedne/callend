package com.jeden.tel.main;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
	
	private ArrayList<BaseFragment> fragmentlist;
	
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
     * 初始化组件
     */
    public void init()
    {
    	// 启动后台服务，监听短信
    	Intent sintent = new Intent(this, MainService.class);
    	sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
    	startService(sintent);
    	
    	// 初始化数据
    	DataBean.getInstance().init(this);
    }

    /**
	 * 初始化标题
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
	
	/**
	 * 标签面板，按钮点击事件监听
	 * 
	 * @author jeden
	 *
	 */
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
	 * 初始化滚动条
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
	 * 初始化滑动页面
	 */
	private void initViewPager()
	{
		viewpager = (ViewPager) findViewById(R.id.content_view);
		
		fragmentlist = new ArrayList<BaseFragment>();
		fragmentlist.add(new FragmentTel());
		fragmentlist.add(new FragmentMsg());
		fragmentlist.add(new FragmentBlack());
		
		viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentlist));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new myOnPagerChangeListener());
		
		// 设置底部按钮的初始状态
		setBottomBtnState(0);
	}

	/**
	 * viewpager的切换监听
	 * 
	 * @author jeden
	 *
	 */
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
     * 底部按钮点击事件处理
     */
    public void addOrDelete()
    {
    	int index = viewpager.getCurrentItem();
    	
    	fragmentlist.get(index).bottomBtnClick();
    }
    
	public void onClick(View v) 
	{
		if(v == bottomBtn)
		{
			addOrDelete();
		}
	}
	
	/**
	 * 设置底部按钮的状态
	 * 
	 * @param index
	 * 			当前所在的页面
	 */
	public void setBottomBtnState(int index)
	{
		if(index == 2)
		{
			bottomBtn.setText("添加");
		}
		else
		{
			bottomBtn.setText("清空");
		}
	}
}
