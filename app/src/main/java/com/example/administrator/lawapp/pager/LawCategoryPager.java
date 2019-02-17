package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseCategoryPager;
import com.example.administrator.lawapp.utils.LogUtil;

/**
 * Created by 吴青晓 on 2019/2/17
 * Describe
 */
public class LawCategoryPager extends BaseCategoryPager {
    public TextView textView;

    public LawCategoryPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);

        return textView;
    }


    @Override
    public void initData(int categoryId) {
        super.initData(categoryId);
        LogUtil.e("二级目录数据被初始化了");

        textView.setText("二级目录内容");
    }
}
