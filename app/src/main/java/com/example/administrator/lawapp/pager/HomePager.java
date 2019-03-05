package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.AudioActivity;
import com.example.administrator.lawapp.activity.DetailActivity;
import com.example.administrator.lawapp.activity.TrainActivity;
import com.example.administrator.lawapp.activity.VideoActivity;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.bean.HomePagerBean;
import com.example.administrator.lawapp.ui.RefreshListView;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 主页面
 */
public class HomePager extends BasePager {

    @ViewInject(R.id.tv_moreA)
    private TextView tv_moreA;
    @ViewInject(R.id.tv_moreB)
    private TextView tv_moreB;
    @ViewInject(R.id.ll_detailA)
    private LinearLayout ll_detailA;
    @ViewInject(R.id.ll_detailB)
    private LinearLayout ll_detailB;
    @ViewInject(R.id.viewpager_banner)
    private ViewPager viewpager_banner;
    @ViewInject(R.id.tv_title_banner)
    private TextView tv_title_banner;
    @ViewInject(R.id.ll_point_group)
    private LinearLayout ll_point_group;
    @ViewInject(R.id.iv_audio)
    private ImageView iv_audio;
    @ViewInject(R.id.iv_video)
    private ImageView iv_video;
    @ViewInject(R.id.tv_audio)
    private TextView tv_audio;
    @ViewInject(R.id.tv_video)
    private TextView tv_video;
    @ViewInject(R.id.lv_case)
    private RefreshListView lv_case;//显示cases内容的listview
    @ViewInject(R.id.iv_test_home)
    private ImageView iv_test_home;
    @ViewInject(R.id.iv_wrong_home)
    private ImageView iv_wrong_home;
    @ViewInject(R.id.iv_page_home)
    private ImageView iv_page_home;
    private int prePosition;//记录红点前一次位置
    public Boolean isLoadMore = false;//记录是否是第一次
    private List<HomePagerBean.DataBean.BannerBean> banner;
    private HomePagerBean.DataBean.VideoBean video;
    private HomePagerBean.DataBean.AuditoriumBean auditorium;
    private List<HomePagerBean.DataBean.CasesBean> cases;
    private MyLVCaseAdaper myLVCaseAdaper;
    private int pagenum = 2;
    private int pagesize = 5;
    private List<HomePagerBean.DataBean.CasesBean> casesMore;

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
        View lvView = View.inflate(context, R.layout.home_display_child, null);
        View view = View.inflate(context, R.layout.home_display, null);
        x.view().inject(HomePager.this, lvView);
        x.view().inject(HomePager.this, view);
        x.Ext.setDebug(false);
        //把顶部的部分视图，以头的方式添加到ListView中
        //lv_case.addHeaderView(lvView);
        lv_case.addBannerView(lvView);
        //设置监听下拉刷新
        lv_case.setmOnRefreshListener(new MyOnRefreshListener());
        lv_case.setOnItemClickListener(new MyOnItemClickListener());
        myOnClickListener();
        fl_content.addView(view);
        //4.绑定数据
        //缓存获取数据
        String saveJson = CacheUtils.getString(context, Constants.HOME_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)) {
            //解析数据和处理显示数据
            manageData(saveJson);
        }
        getDataFromNet();
    }

    private void myOnClickListener() {
        ll_detailA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("type", "video");
                intent.putExtra("dataId", video.getVideo_id() + "");
                context.startActivity(intent);
            }
        });
        ll_detailB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("type", "audio");
                intent.putExtra("dataId", auditorium.getAuditorium_id() + "");
                context.startActivity(intent);
            }
        });
        tv_moreA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                context.startActivity(intent);
            }
        });
        tv_moreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AudioActivity.class);
                context.startActivity(intent);
            }
        });
        iv_test_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrainActivity.class);
                intent.putExtra("position", "test");
                context.startActivity(intent);
            }
        });
        iv_wrong_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrainActivity.class);
                intent.putExtra("position", "wrong");
                context.startActivity(intent);
            }
        });
        iv_page_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrainActivity.class);
                intent.putExtra("position", "page");
                context.startActivity(intent);
            }
        });
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HOME_PAGER_URL);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context, Constants.HOME_PAGER_URL, result);
                LogUtil.e("请求成功" + result);
                //设置适配器
                manageData(result);
                //隐藏下拉刷新控件--更新时间，显示联网请求成功，重新显示数据
                lv_case.setRefreshFinish(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                //隐藏下拉刷新控件--不更新时间，显示联网请求失败
                lv_case.setRefreshFinish(true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("请求onCancelled" + cex.getMessage());

            }

            @Override
            public void onFinished() {
                LogUtil.e("请求结束");

            }
        });
    }

    private void getMoreFromNet() {
        RequestParams params =
                new RequestParams(Constants.HOME_PAGER_CASES_URL
                        + "?pagenum=" + pagenum
                        + "&pagesize=" + pagesize);
        params.setConnectTimeout(4000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("加载更多数据==" + result);
                LogUtil.e("pagenum==" + pagenum);
                lv_case.setRefreshFinish(false);
                //解析数据
                isLoadMore = true;
                manageData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("加载数据失败==" + ex.getMessage());
                LogUtil.e("pagenum==" + pagenum);
                Toast.makeText(context, "没有了", Toast.LENGTH_SHORT).show();
                lv_case.setRefreshFinish(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("加载更多数据onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("加载更多数据onFinished");
            }
        });

    }

    private void manageData(String json) {
        HomePagerBean homePagerBean = parsedJson(json);
        //LogUtil.e("轮播的标题==" + homePagerBean.getData().getBanner().get(0).getBanner_title());
        if (!isLoadMore) {//是否加载更多
            LogUtil.v("pagenum============================" + pagenum);
            pagenum = 2;
            banner = homePagerBean.getData().getBanner();
            video = homePagerBean.getData().getVideo();
            auditorium = homePagerBean.getData().getAuditorium();
            cases = homePagerBean.getData().getCases();
            LogUtil.e("cases==" + cases.get(1).getCase_name());
            //viewpager_banner.requestDisallowInterceptTouchEvent(true);请求父层视图不拦截，当前控件的事件
            //设置viewpager的适配器
            viewpager_banner.setAdapter(new ViewpagerBannerAdapter());
            addpoint();//添加点
            //联网请求图片
            //String iv_videoUrl = Constants.BASE_URL + video.getVideo_picture();
            //String iv_audioUrl = Constants.BASE_URL + auditorium.getAuditorium_picture();
            x.image().bind(iv_video, video.getVideo_picture());
            x.image().bind(iv_audio, auditorium.getAuditorium_picture());
            tv_video.setText(video.getVideo_title());
            tv_audio.setText(auditorium.getAuditorium_title());
            myLVCaseAdaper = new MyLVCaseAdaper();
            lv_case.setAdapter(myLVCaseAdaper);
        } else {//加载更多
            isLoadMore = false;
            if (homePagerBean.getData().getCases().get(0) != null) {
                pagenum++;
            }
            //添加到原来的集合中
            cases.addAll(homePagerBean.getData().getCases());
            //刷新适配器
            myLVCaseAdaper.notifyDataSetChanged();
        }

    }

    private HomePagerBean parsedJson(String json) {
        Gson gson = new Gson();
        HomePagerBean homePagerBean = gson.fromJson(json, HomePagerBean.class);
        return homePagerBean;
    }

    //添加点
    private void addpoint() {
        ll_point_group.removeAllViews();//移除所有的红点
        for (int i = 0; i < banner.size(); i++) {
            ImageView imageView = new ImageView(context);
            //背景选择器
            imageView.setBackgroundResource(R.drawable.banner_point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(15), DensityUtil.dip2px(15));
            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }
            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);
        }
        viewpager_banner.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title_banner.setText(banner.get(prePosition).getBanner_title());
    }

    static class ViewHolder {
        TextView tv_name_item;
        ImageView iv_cases_item;
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener {

        @Override
        public void onPullDownRefresh() {
            Toast.makeText(context, "我在下拉", Toast.LENGTH_SHORT).show();
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {
            //Toast.makeText(context, "加载更多", Toast.LENGTH_SHORT).show();
            getMoreFromNet();
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            //1.设置文本
            tv_title_banner.setText(banner.get(i).getBanner_title());
            //2.对应页面的点高亮
            //把之前的变灰
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            //把当前的变红
            ll_point_group.getChildAt(i).setEnabled(true);
            prePosition = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class ViewpagerBannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return banner.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.mipmap.banner_upload);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = Constants.BASE_URL + banner.get(position).getBanner_image_url();
            //联网请求图片
            x.image().bind(imageView, banner.get(position).getBanner_image_url());
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }

    class MyLVCaseAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return cases.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_cases_pager, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_name_item = convertView.findViewById(R.id.tv_name_item);
                viewHolder.iv_cases_item = convertView.findViewById(R.id.iv_cases_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //跟位置得到数据
            String name = cases.get(position).getCase_name();
            String imageUrl = Constants.BASE_URL + cases.get(position).getCase_pircture();
            //请求图片
            LogUtil.e("图片路径："+cases.get(position).getCase_pircture());
            x.image().bind(viewHolder.iv_cases_item, cases.get(position).getCase_pircture());
            viewHolder.tv_name_item.setText(name);
            return convertView;
        }

    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 1;
            //跳转页面
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("type", "cases");
            intent.putExtra("dataId", cases.get(realPosition).getCase_id() + "");
            //LogUtil.e("id======"+cases.get(position).getCase_id());
            context.startActivity(intent);
        }
    }
}
