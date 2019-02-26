package com.example.administrator.lawapp.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BaseTrainFragment;

/**
 * Created by 吴青晓 on 2019/2/26
 * Describe
 */
public class AnswerFragment extends BaseTrainFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;
    }

    @Override
    protected void initData() {

        super.initData();
        textView.setText("aaa");

    }
}
