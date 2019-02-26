package com.example.administrator.lawapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.utils.LogUtil;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class WrongFragment extends BaseTrainFragment {
    private TextView textView;

    @Override
    public View initView() {
        textView=new TextView(context);

        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtil.e("错题页面初始化了");
        textView.setText("bbbb");
    }
}
