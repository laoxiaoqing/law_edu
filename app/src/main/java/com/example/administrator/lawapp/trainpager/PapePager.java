package com.example.administrator.lawapp.trainpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.TrainActivity;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicPapersBean;
import com.example.administrator.lawapp.fragment.PaperFragment;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class PapePager extends BaseTrainPager {
    private int papers_id;
    @ViewInject(R.id.viewpager)
    public ViewPager viewPager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.iv_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_answer)
    private ImageView ivAnswer;
    private TopicPapersBean bean;
    private ArrayList<PaperContentPager> paperContentPagers;
    private PaperContentPager paperContentPager;
    private List<TopicPapersBean.DataBean> list;
    private MyAnswerAdapter myAnswerAdapter;

    public PapePager(Context context, int papers_id) {
        super(context);
        this.papers_id = papers_id;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.paper_pager, null);
        x.view().inject(PapePager.this, view);
        ivAnswer.setVisibility(View.VISIBLE);
        ivAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(paperContentPagers.size());//跳到最后一页
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText("结果报告");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainActivity trainActivity = (TrainActivity) context;
                trainActivity.switchFrament(new PaperFragment());
            }
        });
        LogUtil.e("练习内容练习内容练习内容练习内容");
        getDataFromNet();
    }

    private void getDataFromNet() {
        String username = CacheUtils.getString(context, "username");
        LogUtil.e("username:" + username);
        LogUtil.e("papers_id:"+papers_id);
        RequestParams params = new RequestParams(Constants.TOPIC_PAPER_URL  + papers_id);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("papers_url:"+Constants.TOPIC_PAPER_URL  + papers_id);
                LogUtil.e(result);
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

    private void manageData(String json) {
        Gson gson = new Gson();
        bean = gson.fromJson(json, TopicPapersBean.class);
        list = bean.getData();
        LogUtil.e("right:"+list.get(0).getRight_key());
        LogUtil.e("user:"+list.get(0).getUser_key());


        paperContentPagers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            paperContentPagers.add(new PaperContentPager(context, list.get(i), true,i,list,viewPager));//选择题
        }
        paperContentPagers.add(new PaperContentPager(context, list.get(0),false,0,list,viewPager));//答题卡
        myAnswerAdapter = new MyAnswerAdapter();
        viewPager.setAdapter(myAnswerAdapter);
    }

    private class MyAnswerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return paperContentPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            paperContentPager = paperContentPagers.get(position);
            View rootView = paperContentPager.rootView;
            paperContentPager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
