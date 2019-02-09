package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.SplashActivity;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout start_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_point_light;
    private int leftmax;        //两点间距
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        start_point_group = (LinearLayout) findViewById(R.id.start_point_group);
        iv_point_light = (ImageView) findViewById(R.id.iv_point_light);
        int[] ids = new int[]{
                R.drawable.start_page_1,
                R.drawable.start_page_2,
                R.drawable.start_page_3
        };
        widthdpi = DensityUtil.dip2px(this, 10);
        Log.e("", widthdpi + "----");
        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(imageView);
            //创建三个点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            //单位：像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi, widthdpi);
            point.setLayoutParams(params);
            if (i != 0) {
                //距离左边有十个像素，不包括第一个
                params.leftMargin = widthdpi;
            }
            start_point_group.addView(point);
        }
        //设置ViewPager的适配器
        viewPager.setAdapter(new MyPagerAdapter());
        //根据View的声生命周期，当视图执行到onLayout或者onDraw的时候，视图的高和宽，边距都有了
        iv_point_light.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        //得到屏幕滑动的百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置按钮点击事件
        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.保存曾经进入过的主页面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);
                //2.跳转到主页面
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                //3.关闭引导页面
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滚动了会回调这个方法
         * @param position             当前滑动页面的位置
         * @param postionoffset        页面滑动的百分比
         * @param positionoffsetpixels 滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float postionoffset, int positionoffsetpixels) {
            //两点间移动的距离 = 屏幕滑动百分比 * 间距
            int leftmargin = (int) (postionoffset * leftmax);

            //Log.e("MyOnPageChangeListener", "position==" + position + ",postionoffset==" + postionoffset + ",positionoffsetpixels" + positionoffsetpixels);

            //两点滑动距离对应的坐标 = 原来的起始位置 + 两点间距
            leftmargin = (int) (position * leftmax + (postionoffset * leftmax));
            //params.leftMargin = 两点间滑动对应的坐标
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_point_light.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_point_light.setLayoutParams(params);

        }

        /**
         * 当页面被选中的时候，回调这个方法
         *
         * @param i 被选中页面的对应的位置
         */
        @Override
        public void onPageSelected(int i) {
            if (i == imageViews.size() - 1) {
                //最后一个页面
                btn_start_main.setVisibility(View.VISIBLE);
            } else {
                btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当Viewpager页面滑动状态发生变化的时候
         *
         * @param i
         */
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {

            //执行不止一次,这条语句让MyOnGlobalLayoutListener执行一次
            iv_point_light.getViewTreeObserver().removeGlobalOnLayoutListener(MyOnGlobalLayoutListener.this);
            //间距 = 第一个距离左边的距离 - 第0个点距离左边的距离
            leftmax = start_point_group.getChildAt(1).getLeft() - start_point_group.getChildAt(0).getLeft();
            Log.i("onGlobalLayout", "leftmax==" + leftmax);
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回页数
         *
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 判断
         *
         * @param view 当面创建的视图
         * @param o    下面instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            boolean bb = view == o;
            return bb;
        }

        /**
         * 作用 ，getView
         *
         * @param container viewpager
         * @param position  要创建页面的位置  一开始创建两个页面
         * @return 返回和创建当前页面有关系的值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器中
            container.addView(imageView);
            return imageView;
            //return super.instantiateItem(container, position);
        }

        /**
         * 销毁页面
         *
         * @param container viewpager
         * @param position  要销毁页面的位置
         * @param object    要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);必须去掉
            container.removeView((View) object);
        }
    }
}
