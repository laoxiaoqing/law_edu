package com.example.administrator.lawapp.pager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.LoginActivity;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.utils.LogUtil;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 主页面
 */
public class MePager extends BasePager {
    @ViewInject(R.id.tv_logun)
    private TextView tvLogun;
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
        x.view().inject(MePager.this,view);
        tvLogun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("aaaaa");
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.finish();
            }
        });
        fl_content.removeAllViews();
        fl_content.addView(view);
    }
}
