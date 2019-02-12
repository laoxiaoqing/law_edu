package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.utils.LogUtil;

/**
 * 主页面
 */
public class HomePager extends BasePager {
    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("主页面数据初始化了");
        //1.设置标题
        tv_title.setText("主页面");
        //2.联网请求得到数据创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        //3.把子视图添加到BasePager的FrameLayout
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("主页面内容");
    }
}
