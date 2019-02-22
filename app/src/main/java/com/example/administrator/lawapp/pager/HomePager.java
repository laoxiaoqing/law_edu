package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
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
    private RefreshListView lv_case;

    private List<HomePagerBean.DataBean.BannerBean> banner;
    private HomePagerBean.DataBean.VideoBean video;
    private HomePagerBean.DataBean.AuditoriumBean auditorium;
    private List<HomePagerBean.DataBean.CasesBean> cases;
    private MyLVCaseAdaper myLVCaseAdaper;

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
        //把顶部的部分视图，以头的方式添加到ListView中
        lv_case.addHeaderView(lvView);
        //3.把子视图添加到BasePager的FrameLayout
        ll_detailA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "aa", Toast.LENGTH_SHORT).show();
            }
        });
        ll_detailB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "bb", Toast.LENGTH_SHORT).show();
            }
        });
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


    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HOME_PAGER_URL);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context, Constants.HOME_PAGER_URL, result);
                LogUtil.e("请求成功" + result);
                //设置适配器
                manageData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());

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

    private int prePosition;//记录红点前一次位置

    /**
     * 解析json数据和显示数据
     *
     * @param json
     */
    private void manageData(String json) {
        HomePagerBean homePagerBean = parsedJson(json);
        LogUtil.e("轮播的标题==" + homePagerBean.getData().getBanner().get(0).getBanner_title());
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
        String iv_videoUrl = Constants.BASE_URL + video.getVideo_picture();
        String iv_audioUrl = Constants.BASE_URL + auditorium.getAuditorium_picture();
        x.image().bind(iv_video, iv_videoUrl);
        x.image().bind(iv_audio, iv_audioUrl);
        tv_video.setText(video.getVideo_title());
        tv_audio.setText(auditorium.getAuditorium_title());
        myLVCaseAdaper = new MyLVCaseAdaper();
        lv_case.setAdapter(myLVCaseAdaper);
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
            String name=cases.get(position).getCase_name();
            String imageUrl = Constants.BASE_URL+cases.get(position).getCase_pircture();
            //请求图片
            x.image().bind(viewHolder.iv_cases_item,imageUrl);
            viewHolder.tv_name_item.setText(name);
            return convertView;
        }

    }

    static class ViewHolder {
        TextView tv_name_item;
        ImageView iv_cases_item;
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
            x.image().bind(imageView, imageUrl);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }

    private HomePagerBean parsedJson(String json) {
        Gson gson = new Gson();
        HomePagerBean homePagerBean = gson.fromJson(json, HomePagerBean.class);
        return homePagerBean;
    }

}
