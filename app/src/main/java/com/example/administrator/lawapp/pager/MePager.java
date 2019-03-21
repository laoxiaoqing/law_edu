package com.example.administrator.lawapp.pager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.LoginActivity;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
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
    @ViewInject(R.id.user_name)
    private TextView userName;
    @ViewInject(R.id.h_head)
    private ImageView hHead;
    public MePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("我的数据初始化了");
        //1.设置标题
        tv_title.setText("社区");
        String username = CacheUtils.getString(context,"user_name");
        String user_head = CacheUtils.getString(context,"user_head");
        //2.联网请求得到数据创建视图
        View view = View.inflate(context, R.layout.me_pager, null);
        x.view().inject(MePager.this,view);
        userName.setText(username);
        Glide.with(context)
                .load(Constants.IMAGEPATH+user_head)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.cases_upload)
                .error(R.mipmap.cases_upload)
                .into(hHead);
        tvLogun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                MainActivity mainActivity = (MainActivity) context;
                CacheUtils.putString(context,"user_id","");
                CacheUtils.putString(context,"user_head","");
                CacheUtils.putString(context,"user_tel","");
                CacheUtils.putString(context,"user_name","");
                CacheUtils.putString(context,"user_email","");
                CacheUtils.putString(context, "username", "");
                CacheUtils.putString(context, "password", "");
                /*mainActivity.finish();*/
            }
        });
        fl_content.removeAllViews();
        fl_content.addView(view);
    }
}
