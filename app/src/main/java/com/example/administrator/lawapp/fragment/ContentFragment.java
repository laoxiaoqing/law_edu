package com.example.administrator.lawapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.utils.LogUtil;

public class ContentFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图初始化了");
        View view = View.inflate(context, R.layout.content_fragment,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");

    }
}
