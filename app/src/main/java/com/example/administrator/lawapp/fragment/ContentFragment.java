package com.example.administrator.lawapp.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseFragment;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.pager.ForumPager;
import com.example.administrator.lawapp.pager.HomePager;
import com.example.administrator.lawapp.pager.LawPager;
import com.example.administrator.lawapp.pager.MePager;
import com.example.administrator.lawapp.utils.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {
    private TextView textView;
    //初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    /**
     * 装四个页面的集合
     */
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图初始化了");
        View view = View.inflate(context, R.layout.content_fragment, null);
        //viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        //1.把视图注入到框架中，让ContentFragment类和view关联起来
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        x.view().inject(ContentFragment.this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");
        //初始化四个页面，并且放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));//主页面
        basePagers.add(new LawPager(context));//案例
        basePagers.add(new ForumPager(context));//社区
        basePagers.add(new MePager(context));//我的
        //设置默认选中首页
        rg_main.check(R.id.rb_home);
        //设置ViewPager的适配器
        viewPager.setAdapter(new ContentFragmentAdapter());
        //设置RadioGroup 监听事件：为底部RadioGroup绑定状态改变监听事件
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        /**
         * @param group     RadioGroup
         * @param checkedId 被选中的RadioButton的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);//false没有动画
                    break;
                case R.id.rb_law:
                    viewPager.setCurrentItem(1,false);
                    break;
                case R.id.rb_forum:
                    viewPager.setCurrentItem(2,false);
                    break;
                case R.id.rb_me:
                    viewPager.setCurrentItem(3,false);
                    break;
            }
        }
    }

    class ContentFragmentAdapter extends PagerAdapter {
        /**
         * 页面总数
         *
         * @return
         */
        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 默认创建两个页面，最多三个
         *
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);//本质是各个页面的实例，，0为主页
            View rootView = basePager.rootView;//各个子页面
            //调用各个页面的initData()
            basePager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
