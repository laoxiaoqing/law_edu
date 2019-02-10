package com.example.administrator.lawapp.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.utils.LogUtil;

public class ContentFragment extends BaseFragment {
    private TextView textView;
    private ViewPager viewPager;
    private RadioGroup rg_main;
    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图初始化了");
        View view = View.inflate(context, R.layout.content_fragment,null);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");
        rg_main.check(R.id.rb_home);
    }
}
