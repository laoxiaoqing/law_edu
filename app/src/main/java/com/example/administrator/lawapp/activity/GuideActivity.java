package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.administrator.lawapp.R;

import java.util.ArrayList;

public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout start_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_point_light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        start_point_group = (LinearLayout) findViewById(R.id.start_point_group);
        iv_point_light= (ImageView) findViewById(R.id.iv_point_light);
        int[] ids = new int[]{
                R.drawable.start_page_1,
                R.drawable.start_page_2,
                R.drawable.start_page_3
        };
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            point.setLayoutParams(params);
            if (i !=0){
                //距离左边有十个像素，不包括第一个
                params.leftMargin = 10;
            }
            start_point_group.addView(point);
        }
        //设置ViewPager的适配器
        viewPager.setAdapter(new MyPagerAdapter());

        //根据View的声生命周期，当视图执行到onLayout或者onDraw的时候，
        // 视图的高和宽，边距都有了
        iv_point_light.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_point_light.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //间距
            int leftmax = start_point_group.getChildAt(1).getLeft() - start_point_group.getChildAt(0).getLeft();

        }
    }

    class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回页数
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
