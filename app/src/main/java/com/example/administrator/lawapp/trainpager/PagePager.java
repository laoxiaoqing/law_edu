package com.example.administrator.lawapp.trainpager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.utils.LogUtil;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class PagePager extends BaseTrainPager {
    private TextView textView;
    public PagePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("练习内容练习内容练习内容练习内容");
        textView.setText("试卷");
    }
}
