package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 220));
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //初始化Fragment
        initFragment();
    }

    private void initFragment() {
        //1.得到FragmentManger
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开始事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //3.替换
        fragmentTransaction.replace(R.id.main_content, new ContentFragment(), MAIN_CONTENT_TAG);//主页
        fragmentTransaction.replace(R.id.left_menu, new LeftmenuFragment(), LEFTMENU_TAG);//左侧菜单
        //4.提交
        fragmentTransaction.commit();
        //5
    }

    /**
     * 得到左侧菜单Fragment
     *
     * @return
     */
    public LeftmenuFragment getLeftmenuFragment() {
        //1.得到FragmentManger
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (LeftmenuFragment) fragmentManager.findFragmentByTag(LEFTMENU_TAG);
    }

    /**
     * 得到正文的Fragment
     *
     * @return
     */
    public ContentFragment getContentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (ContentFragment) fragmentManager.findFragmentByTag(MAIN_CONTENT_TAG);
    }

    //按两次退出
    private boolean exit=false;//标识是够可以退出
    //延迟两秒将exit=false
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                exit=false;
            }
        }
    };
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            if (!exit){
                exit=true;
                Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
                //发消息延迟两秒将exit =false
                handler.sendEmptyMessageDelayed(1,2000);
                return true;//不退出
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
