package com.example.administrator.lawapp.fragment;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.utils.LogUtil;

public class ContentFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图初始化了");
        textView = new TextView(context);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");
        textView.setText("正文Fragment页面");
    }
}
