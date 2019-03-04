package com.example.administrator.lawapp.trainpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.TrainActivity;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicBean;
import com.example.administrator.lawapp.fragment.AnswerFragment;
import com.example.administrator.lawapp.fragment.TestFragment;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class TestPager extends BaseTrainPager {
    private boolean b=false;
    private int topic_type_id;
    @ViewInject(R.id.viewpager)
    public ViewPager viewPager;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.iv_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_answer)
    private ImageView ivAnswer;

    private TopicBean bean;
    private List<TopicBean.DataBean> topicBean;
    private final List<String> list = new ArrayList<>();

    public Map<String, Map<String, String>> map = new LinkedHashMap();//存放  用户做题的答案
    /**
     * 题目页面的集合
     */
    private ArrayList<TestContentPager> testContentPagers;
    private TestContentPager testContentPager;

    public TestPager(Context context) {
        super(context);
    }

    public TestPager(Context context, int topic_type_id) {
        super(context);
        this.topic_type_id = topic_type_id;
    }

    public TestPager(Context context, int papers_id, boolean b) {
        super(context);
        this.topic_type_id = papers_id;
        this.b = b;
    }

    public ViewPager getViewpager() {
        return viewPager;
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.topic_detail, null);
        x.view().inject(TestPager.this, view);
        ivAnswer.setVisibility(View.VISIBLE);
        ivAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(testContentPagers.size());//跳到最后一页
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("知识点专项练习");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("退出试卷？")
                        .setMessage("是否退出做题？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TrainActivity trainActivity = (TrainActivity) context;
                                trainActivity.switchFrament(new TestFragment());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create();
                alertDialog.show();
            }
        });
        LogUtil.e("练习内容练习内容练习内容练习内容");
        LogUtil.e("type_id==" + topic_type_id);
        getDataFromNet();
    }

    private void getDataFromNet() {
        String username = CacheUtils.getString(context, "username");
        LogUtil.e("username:" + username);
        String  url = "";
        if (b){
            url=Constants.PAPGERS_TOPIC_URL+topic_type_id;
        }else {
            url=Constants.TOPIC_NUM_URL + "?num=10&topictypeid=" + topic_type_id + "&username=" + username;
        }
        RequestParams params =
                new RequestParams(url);//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                manageData(result);
                LogUtil.e(result);
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
                LogUtil.e(Constants.TOPIC_NUM_URL + "?num=10&topictypeid=" + topic_type_id);
            }
        });
    }

    private void manageData(String json) {
        Gson gson = new Gson();
        bean = gson.fromJson(json, TopicBean.class);
        LogUtil.e(bean.getData().get(0).getTopic_content());
        topicBean = bean.getData();
        testContentPagers = new ArrayList<>();
        for (int i = 0; i < topicBean.size(); i++) {
            testContentPagers.add(new TestContentPager(context, topicBean.get(i), i, viewPager, list, map, true));
        }
        testContentPagers.add(new TestContentPager(context, topicBean.get(0), 0, viewPager, list, map, false));//记录答案 答案界面
        //设置适配器
        viewPager.setAdapter(new MyTopicPagerAdapter());
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        testContentPagers.get(0).initData();
    }

    class MyTopicPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return testContentPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            testContentPager = testContentPagers.get(position);
            View rootView = testContentPager.rootView;
            //testContentPager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 只加载一个页面，滑到那个页面才加载数据
         *
         * @param i
         */
        @Override
        public void onPageSelected(int i) {
            testContentPagers.get(i).initData();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
