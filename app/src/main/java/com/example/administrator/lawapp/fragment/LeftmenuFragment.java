package com.example.administrator.lawapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.utils.LogUtil;

import org.w3c.dom.Text;

public class LeftmenuFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图初始化了");
        textView = new TextView(context);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");
        textView.setText("左侧菜单页面");
    }
}
