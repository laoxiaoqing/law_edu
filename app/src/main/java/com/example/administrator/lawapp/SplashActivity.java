package com.example.administrator.lawapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.*;
import android.widget.RelativeLayout;
import com.example.administrator.lawapp.activity.GuideActivity;
import com.example.administrator.lawapp.utils.CacheUtils;

public class SplashActivity extends Activity {
    /**
     * 静态常亮
     */
    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splash_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
        //渐变动画，缩放动画，旋转动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        //aa.setDuration(500);//持续播放时间
        aa.setFillAfter(true);
        //动画
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //sa.setDuration(500);
        sa.setFillAfter(true);
        //旋转动画
        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        //ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        //同时播放动画
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.setDuration(1000);
        rl_splash_root.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());
    }
    class MyAnimationListener implements Animation.AnimationListener{
        //当动画开始播放的时候回调这个方法
        @Override
        public void onAnimationStart(Animation animation) {

        }
        //当动画播放结束的时候回调这个方法
        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否进入过主页面
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            if(isStartMain){
                //如果进入过主页面，直接进入主页
            }else{
                //如果没有，则进入引导页面
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
            }
            //关闭Splash页面
            finish();
        }
        //当动画重复回调这个方法
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
