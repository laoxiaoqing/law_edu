package com.example.administrator.lawapp.trainpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.TrainActivity;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicBean;
import com.example.administrator.lawapp.fragment.AnswerFragment;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe
 */
public class TestContentPager extends BaseTrainPager {
    private Map map;
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
    private TopicBean.DataBean dataBean;
    private MyOnCheckedChangeListener rbChange;
    private boolean realPosition = false;
    private List<String> list;
    private MyGridViewAdapter gridViewadapter;
    private ImageView imageView;


    public TestContentPager(Context context, List<String> size) {
        super(context);


    }

    public TestContentPager(Context context,
                            TopicBean.DataBean dataBean,
                            int position,
                            ViewPager viewPager,
                            List<String> list,
                            Map<Integer, String> map) {
        super(context);
        this.position = position;
        this.dataBean = dataBean;
        this.viewPager = viewPager;
        this.list = list;
        this.map = map;
    }


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.test_content, null);
        x.view().inject(TestContentPager.this, view);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet();
                TrainActivity trainActivity = (TrainActivity) context;
                trainActivity.switchFrament(new AnswerFragment());
            }
        });
        rbChange = new MyOnCheckedChangeListener();
        rbOption.setOnCheckedChangeListener(rbChange);
        gridViewadapter = new MyGridViewAdapter();
        gridView.setAdapter(gridViewadapter);
        //ivBack.setVisibility(View.VISIBLE);
        return view;
    }

    private void getDataFromNet() {
        //获得用户做的题目及答案
        String username=CacheUtils.getString(context,"username");
        LogUtil.e("username:"+username);
        RequestParams params = new RequestParams(Constants.TOPIC_ANSWER_URL+"");//写url
        params.setBodyContent("{\"username\":\"" + username
                + "\",\"map\":\"" + map + "\"}");
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                manageData(result);
                LogUtil.e("成功");
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

    @Override
    public void initData() {
        super.initData();
        if (dataBean != null) {

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
            //LogUtil.e("a");
            map.put(position, "a");
        } else if (rbB.getId() == checkedId) {
            ///LogUtil.e("b");
            map.put(position, "b");
        } else if (rbC.getId() == checkedId) {
            //LogUtil.e("c");
            map.put(position, "c");
        } else if (rbD.getId() == checkedId) {
            //LogUtil.e("d");
            map.put(position, "d");
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
                Integer key = (Integer) entry.getKey();
                String value = (String) entry.getValue();
                LogUtil.e("Key = " + key + ", Value = " + value);
                if (position == key) {
                    imageView.setActivated(true);//选择了答案就高亮
                }
            }

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
/*switch (key.toString()) {
                    case "0":
                        imageView.setEnabled(true);
                        LogUtil.w("0");
                        break;
                    case "1":
                        imageView.setEnabled(true);
                        LogUtil.w("1");
                        break;
                    case "2":
                        imageView.setEnabled(true);
                        LogUtil.w("2");
                        break;
                    case "3":
                        imageView.setEnabled(true);
                        LogUtil.w("3");
                        break;
                    case "4":
                        imageView.setEnabled(true);
                        LogUtil.w("4");
                        break;
                    case "5":
                        imageView.setEnabled(true);
                        LogUtil.w("5");
                        break;
                    case "6":
                        imageView.setEnabled(true);
                        LogUtil.w("6");
                        break;
                    case "7":
                        imageView.setEnabled(true);
                        LogUtil.w("7");
                        break;
                    case "8":
                        imageView.setEnabled(true);
                        LogUtil.w("8");
                        break;
                    case "9":
                        imageView.setEnabled(true);
                        LogUtil.w("9");
                        break;
                    default:
                        break;
                }*/