package com.example.administrator.lawapp.trainpager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicPapersBean;
import com.example.administrator.lawapp.utils.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/3/1
 * Describe ：具体的试卷正确答案和错误答案对比页面
 */
public class PaperContentPager extends BaseTrainPager {

    private ViewPager viewpager;
    private List<TopicPapersBean.DataBean> list;
    private boolean b;
    private TopicPapersBean.DataBean topicPaper;

    @ViewInject(R.id.question)
    private TextView question;
    @ViewInject(R.id.done)
    private TextView done;
    @ViewInject(R.id.rba)
    private RadioButton rbA;
    @ViewInject(R.id.rbb)
    private RadioButton rbB;
    @ViewInject(R.id.rbc)
    private RadioButton rbC;
    @ViewInject(R.id.rbd)
    private RadioButton rbD;
    @ViewInject(R.id.rb_option)
    private RadioGroup rbOption;
    @ViewInject(R.id.paperGrid)
    private GridView paperGrid;
    @ViewInject(R.id.pb_status)
    private ProgressBar pbStatus;
    @ViewInject(R.id.tv_user_key)
    private TextView tvUserKey;
    @ViewInject(R.id.tv_right_key)
    private TextView tvRightKey;
    @ViewInject(R.id.tv_analysis)
    private TextView tvAnalysis;
    @ViewInject(R.id.ll_tv)
    private LinearLayout llTv;
    private MyGridViewAdapter gridViewadapter;
    private int position;
    private ImageView imageView;

    public PaperContentPager(Context context) {
        super(context);
    }

    public PaperContentPager(Context context, TopicPapersBean.DataBean topicPaper, boolean b, int position, List<TopicPapersBean.DataBean> list, ViewPager viewPager) {
        super(context);
        this.topicPaper = topicPaper;
        this.b = b;
        this.position = position;
        this.list = list;
        this.viewpager = viewPager;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.paper_content, null);
        x.view().inject(PaperContentPager.this, view);
        gridViewadapter = new MyGridViewAdapter();
        paperGrid.setAdapter(gridViewadapter);
        LogUtil.e("获得gridview的数量：”" + paperGrid.getChildCount());
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        if (b) {//选择题
            question.setText(topicPaper.getTopic().getTopic_content());
            done.setText("(" + (position + 1) + "/" + list.size() + ")");
            if (!TextUtils.isEmpty(topicPaper.getUser_key())) {
                if (topicPaper.getUser_key().equals(topicPaper.getRight_key())) {
                    tvRightKey.setText(topicPaper.getRight_key());
                    tvUserKey.setText(topicPaper.getUser_key() + "(正确)");
                } else {
                    tvRightKey.setText(topicPaper.getRight_key());
                    tvUserKey.setText(topicPaper.getUser_key() + "(错误)");
                    tvUserKey.setTextColor(Color.RED);
                }
            } else {
                tvRightKey.setText(topicPaper.getRight_key());
                tvUserKey.setText("(错误)");
                tvUserKey.setTextColor(Color.RED);
            }

            chooseTopic();
            rbA.setText(topicPaper.getTopic().getA_option());
            rbB.setText(topicPaper.getTopic().getB_option());
            rbC.setText(topicPaper.getTopic().getC_option());
            rbD.setText(topicPaper.getTopic().getD_option());
            rbA.setClickable(false);
            rbB.setClickable(false);
            rbC.setClickable(false);
            rbD.setClickable(false);
            if(topicPaper.getTopic().getDescribe()==null||topicPaper.getTopic().getDescribe()==""){
                tvAnalysis.setText("暂无详解");
            }else {
                tvAnalysis.setText(topicPaper.getTopic().getDescribe() + "");
            }

        } else {//答题卡
            tvAnalysis.setVisibility(View.GONE);
            question.setText("");
            rbOption.setVisibility(View.GONE);
            paperGrid.setVisibility(View.VISIBLE);
            llTv.setVisibility(View.GONE);

        }
    }

    private void chooseTopic() {
        Drawable drawable = context.getResources().getDrawable(R.drawable.a2);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        Drawable drawable2 = context.getResources().getDrawable(R.drawable.b2);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
                drawable2.getMinimumHeight());
        Drawable drawable3 = context.getResources().getDrawable(R.drawable.c2);
        drawable3.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        Drawable drawable4 = context.getResources().getDrawable(R.drawable.d2);
        drawable4.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        if (!TextUtils.isEmpty(topicPaper.getUser_key())) {
            switch (topicPaper.getUser_key()) {
                case "a":
                    rbA.setCompoundDrawables(drawable, null, null, null);
                    break;
                case "b":
                    rbB.setCompoundDrawables(drawable2, null, null, null);
                    break;
                case "c":
                    rbC.setCompoundDrawables(drawable3, null, null, null);
                    break;
                case "d":
                    rbD.setCompoundDrawables(drawable4, null, null, null);
                default:
                    break;
            }
        }

        Drawable right1 = context.getResources().getDrawable(R.drawable.a4);
        right1.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        Drawable right2 = context.getResources().getDrawable(R.drawable.b4);
        right2.setBounds(0, 0, drawable2.getMinimumWidth(),
                drawable2.getMinimumHeight());
        Drawable right3 = context.getResources().getDrawable(R.drawable.c4);
        right3.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        Drawable right4 = context.getResources().getDrawable(R.drawable.d4);
        right4.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        switch (topicPaper.getRight_key()) {
            case "a":
                rbA.setCompoundDrawables(right1, null, null, null);
                break;
            case "b":
                rbB.setCompoundDrawables(right2, null, null, null);
                break;
            case "c":
                rbC.setCompoundDrawables(right3, null, null, null);
                break;
            case "d":
                rbD.setCompoundDrawables(right4, null, null, null);
        }
    }

    private class MyGridViewAdapter extends BaseAdapter {
        public Integer[] mImagesId = {
                R.drawable.result1,
                R.drawable.result2,
                R.drawable.result3,
                R.drawable.result4,
                R.drawable.result5,
                R.drawable.result6,
                R.drawable.result7,
                R.drawable.result8,
                R.drawable.result9,
                R.drawable.result10
        };

        @Override
        public int getCount() {
            return mImagesId.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                imageView = new ImageView(context);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setActivated(false);
            imageView.setImageResource(mImagesId[pos]);
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(pos).getUser_key())) {
                    if (list.get(pos).getUser_key().equals(list.get(pos).getRight_key())) {
                        imageView.setActivated(true);
                    }
                } else {
                    imageView.setActivated(false);//错误是false
                }
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewpager.setCurrentItem(pos);
                }
            });
            return imageView;

        }
    }
}
