package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.fragment.ContentFragment;
import com.example.administrator.lawapp.fragment.LeftmenuFragment;
import com.example.administrator.lawapp.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.设置主页面
        setContentView(R.layout.activity_main);
        //2.左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);
        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        //slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        //4.设置模式：左侧菜单+主页， 左侧菜单+主页面+右侧菜单，主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //6.设置主页占据宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,200));
        //初始化Fragment
        initFragment();
    }

    private void initFragment() {
        //1.得到FragmentManger
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开始事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //3.替换
        fragmentTransaction.replace(R.id.main_content,new ContentFragment(), MAIN_CONTENT_TAG);//主页
        fragmentTransaction.replace(R.id.left_menu,new LeftmenuFragment(), LEFTMENU_TAG);//主页
        //4.提交
        fragmentTransaction.commit();
        //5
    }
}
