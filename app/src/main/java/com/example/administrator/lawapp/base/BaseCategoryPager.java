package com.example.administrator.lawapp.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 吴青晓 on 2019/2/17
 * Describe
 */
public abstract class BaseCategoryPager {
    public final Context context;
    //代表详情页面的视图
    public View rootView;

    public BaseCategoryPager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 抽象方法，强制孩子实现该方法，每个页面实现不同的效果
     *
     * @return
     */
    public abstract View initView();

    /**
     * 子页面需要绑定数据，联网请求数据等时候，重写该方法
     */
    public void initData(int categoryId, int position) {


    }
}
