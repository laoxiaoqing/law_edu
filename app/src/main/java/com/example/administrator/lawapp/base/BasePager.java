package com.example.administrator.lawapp.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.MainActivity;


/**
 * 基类或者公共类
 */
public class BasePager {
    /**
     * 上下文
     */
    public final Context context;//MainActivity
    //视图，代表各个不同的页面
    public View rootView;
    /**
     * 标题
     */
    public TextView tv_title;
    /**
     * 侧滑菜单
     */
    public ImageButton ib_menu;
    /**
     * 子布局
     */
    public FrameLayout fl_content;

    public BasePager(Context context) {
        this.context = context;
        //构造方法执行，视图就被初始化了
        rootView = initView();
    }

    public View initView() {
        //基类的页面
        View view = View.inflate(context, R.layout.base_pager, null);
        tv_title = view.findViewById(R.id.tv_title);
        ib_menu = view.findViewById(R.id.ib_menu);
        fl_content = view.findViewById(R.id.fl_content);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关开切换
            }
        });
        return view;
    }

    /**
     * 初始化数据;当孩子需要初始化数据;或者绑定数据;
     * 联网请求数据并且绑定的时候，重写
     */
    public void initData() {

    }
}
