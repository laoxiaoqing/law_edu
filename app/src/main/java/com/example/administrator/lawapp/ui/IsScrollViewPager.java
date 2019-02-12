package com.example.administrator.lawapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 吴青晓 on ${DATA}
 * Describe: 自定义不可以滑动的viewPager
 */
public class IsScrollViewPager extends ViewPager {
    /**
     * 通常在代码中实例化的时候用该方法
     * @param context
     */
    public IsScrollViewPager(@NonNull Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类的时候，实例化该类用构造方法，这个方法不能少，少的会崩溃，（系统规定需要两个构造方法）
     * @param context
     * @param attrs
     */
    public IsScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写触摸时间，消费掉
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
