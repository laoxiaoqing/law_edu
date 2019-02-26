package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.utils.LogUtil;

/**
 * 主页面
 */
public class MePager extends BasePager {
    public MePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("我的数据初始化了");
        //1.设置标题
        tv_title.setText("社区");
        //2.联网请求得到数据创建视图
        View view = View.inflate(context, R.layout.me_pager, null);
        fl_content.removeAllViews();
        fl_content.addView(view);
    }
}
