package com.example.administrator.lawapp.trainpager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.TrainActivity;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.PapersTopicBean;
import com.example.administrator.lawapp.bean.TopicBean;
import com.example.administrator.lawapp.fragment.PaperFragment;
import com.example.administrator.lawapp.fragment.TestFragment;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class TestContentPager extends BaseTrainPager {
    private boolean b;
    private Map<String, Map<String, String>> map;
    private ViewPager viewPager;
    private int position;
    @ViewInject(R.id.question)
    private TextView question;
    @ViewInject(R.id.done)
    private TextView done;
    @ViewInject(R.id.paperGrid)
    private GridView gridView;
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
    @ViewInject(R.id.btn_commit)
    private Button btnCommit;
    @ViewInject(R.id.pb_status)
    private ProgressBar pbStatus;
    private TopicBean.DataBean dataBean;
    private MyOnCheckedChangeListener rbChange;
    private boolean realPosition = false;
    private List<String> list;
    private MyGridViewAdapter gridViewadapter;
    private ImageView imageView;

    private FrameLayout fl_train;


    public TestContentPager(Context context) {
        super(context);
    }

    public TestContentPager(Context context,
                            TopicBean.DataBean dataBean,
                            int position,
                            ViewPager viewPager,
                            List<String> list,
                            Map<String, Map<String, String>> map,
                            boolean b) {
        super(context);
        this.position = position;//记录当前位置
        this.dataBean = dataBean;//题目数据
        this.viewPager = viewPager;//当前viewpager
        this.list = list;//
        this.map = map;//记录做题答案
        this.b = b;//分辨是不是做题页，还是确认答案页
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.test_content, null);
        x.view().inject(TestContentPager.this, view);
        //提交答案到数据库，跳到成绩页面
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("是否提交考卷？")
                        .setMessage("是否退出做题？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getDataFromNet();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create();
                alertDialog.show();
                pbStatus.setVisibility(View.VISIBLE);
            }
        });
        rbChange = new MyOnCheckedChangeListener();
        rbOption.setOnCheckedChangeListener(rbChange);
        gridViewadapter = new MyGridViewAdapter();
        gridView.setAdapter(gridViewadapter);
        return view;
    }

    public void getDataFromNet() {//给后台传送用户答案
        //获得用户做的题目及正确答案
        String user_id = CacheUtils.getString(context, "user_id");
        LogUtil.e("username:" + user_id);
        RequestParams params = new RequestParams(Constants.TOPIC_ANSWER_URL/*+"?username="+username+"&map="+map*/);//写url
        params.setBodyContent("{\"user_id\":\"" + user_id
                + "\",\"papers_id\":\"" + dataBean.getPapers_id()
                + "\",\"map\":\"" + map + "\"}");
        params.setConnectTimeout(3000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("成功");
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
        PapersTopicBean papersTopicBean = gson.fromJson(json, PapersTopicBean.class);
        LogUtil.e("getPapers_id:" + papersTopicBean.getData().get(0).getPapers_id());
        LogUtil.e("getRight_key:" + papersTopicBean.getData().get(0).getRight_key());
        LogUtil.e("getUser_key:" + papersTopicBean.getData().get(0).getUser_key());
        LogUtil.e("getPapers_topic_id:" + papersTopicBean.getData().get(0).getPapers_topic_id());
        LogUtil.e("getTopic_id:" + papersTopicBean.getData().get(0).getTopic_id());
        pbStatus.setVisibility(View.GONE);



        /*TrainActivity trainActivity = (TrainActivity) context;
        trainActivity.switchFrament(new PaperFragment());*/
        /**
         *
         *   待开发
         *
         *
         */
        TrainActivity trainActivity = (TrainActivity) context;
        //trainActivity.switchFrament(new PaperFragment());
        fl_train=(FrameLayout)trainActivity.findViewById(R.id.fl_train);
        fl_train.removeAllViews();
        LogUtil.e("position:"+position);
        LogUtil.e("试卷号："+dataBean.getPapers_id());
        BaseTrainPager baseTrainPager = new PapePager(trainActivity,dataBean.getPapers_id());
        View rootView = baseTrainPager.rootView;
        baseTrainPager.initData();
        fl_train.addView(rootView);
        LogUtil.e("判断是否完成");
    }

    @Override
    public void initData() {
        super.initData();
        if (b) {//判定是做题页还是确认答案页
            question.setText(dataBean.getTopic_content());
            done.setText("(" + (position + 1) + "/10)");
            rbA.setText(dataBean.getA_option() + "");
            rbB.setText(dataBean.getB_option() + "");
            rbC.setText(dataBean.getC_option() + "");
            rbD.setText(dataBean.getD_option() + "");
        } else {
            rbOption.setVisibility(View.GONE);
            paperGrid.setVisibility(View.VISIBLE);
            question.setText("请确认答案");
            btnCommit.setVisibility(View.VISIBLE);
            done.setText("已完成(" + list.size() + "/10)");
            gridViewadapter.notifyDataSetChanged();
        }

    }


    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (realPosition == false) {
                selectBtnCount(checkedId);
            }
            recordAns(checkedId);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            realPosition = true;
        }
    }

    private void recordAns(int checkedId) {
        if (rbA.getId() == checkedId) {
            Map<String, String> map2 = new HashMap<>();
            map2.put(dataBean.getTopic_id() + "", "a");
            map.put(position + "", map2);
        } else if (rbB.getId() == checkedId) {
            Map<String, String> map2 = new HashMap<>();
            map2.put(dataBean.getTopic_id() + "", "b");
            map.put(position + "", map2);
        } else if (rbC.getId() == checkedId) {
            Map<String, String> map2 = new HashMap<>();
            map2.put(dataBean.getTopic_id() + "", "c");
            map.put(position + "", map2);
        } else if (rbD.getId() == checkedId) {
            Map<String, String> map2 = new HashMap<>();
            map2.put(dataBean.getTopic_id() + "", "d");
            map.put(position + "", map2);

        }
    }

    private int selectBtnCount(int checkedId) {
        if (rbA.getId() == checkedId) {
            list.add("a");
        } else if (rbB.getId() == checkedId) {
            list.add("b");
        } else if (rbC.getId() == checkedId) {
            list.add("c");
        } else if (rbD.getId() == checkedId) {
            list.add("d");
        }
        return list.size();
    }

    private class MyGridViewAdapter extends BaseAdapter {
        public Integer[] mImagesId = {
                R.drawable.read1,
                R.drawable.read2,
                R.drawable.read3,
                R.drawable.read4,
                R.drawable.read5,
                R.drawable.read6,
                R.drawable.read7,
                R.drawable.read8,
                R.drawable.read9,
                R.drawable.read10
        };

        @Override
        public int getCount() {
            return mImagesId.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        //获取图片id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                imageView = new ImageView(context);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setActivated(false);
            imageView.setImageResource(mImagesId[position]);
            Iterator entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Map<String, String> value = (Map<String, String>) entry.getValue();
                LogUtil.e("Key = " + key + ", Value = " + value);
                if (position == Integer.parseInt(key)) {
                    imageView.setActivated(true);//选择了答案就高亮
                }
            }
            LogUtil.e("aaa:map");
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(position, true);//跳到相应的viewpager
                }
            });
            return imageView;
        }
    }
}