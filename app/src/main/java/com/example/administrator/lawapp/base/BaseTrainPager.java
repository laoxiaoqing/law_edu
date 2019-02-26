package com.example.administrator.lawapp.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public abstract class BaseTrainPager {
    /**
     * 上下文
     */
    public final Context context;
    /**
     * 代表个详情页面的视图
     */
    public View rootView;

    public BaseTrainPager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 抽象方法，强制孩子实现该方法
     *
     * @return
     */
    public abstract View initView();

    public void initData() {

    }
}
