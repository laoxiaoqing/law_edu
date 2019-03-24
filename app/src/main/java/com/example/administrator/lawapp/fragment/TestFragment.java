package com.example.administrator.lawapp.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseCategoryPager;
import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicTypeBean;
import com.example.administrator.lawapp.trainpager.TestPager;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/2/25
 * Describe 专项练习
 */
public class TestFragment extends BaseTrainFragment {

    /*private TextView tv1;
    private TextView tv2;
    private TextView tvCource;
    private TextView tvTopic;*/
    private PullToRefreshListView pullRefreshList;
    private ImageView iv_back;
    private TextView tv_title;
    private View view;
    private MyPullRefresh myadapter;
    private TopicTypeBean Bean;
    private List<TopicTypeBean.DataBean> topicTypeList;
    private FrameLayout fl_train;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.test_fragemt, null);
        findViews();
        getDataFromNet();
        return view;
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.TOPIC_TYPE_URL + "");//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageData(result);
                pullRefreshList.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                pullRefreshList.onRefreshComplete();
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
        Bean = gson.fromJson(json, TopicTypeBean.class);
        topicTypeList = Bean.getData();
        myadapter = new MyPullRefresh();
        pullRefreshList.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtil.e("题目页面初始化了");
    }

    private void findViews() {
        /*tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tvCource = (TextView) view.findViewById(R.id.tv_cource);
        tvTopic = (TextView) view.findViewById(R.id.tv_topic);*/
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_train = (FrameLayout) view.findViewById(R.id.fl_train);
        pullRefreshList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        tv_title.setText("知识点练习");
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        pullRefreshList.setOnItemClickListener(new MyOnItemClickListener());
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (topicTypeList.get(position-1).getNum()>10){
                //3.添加新内容
                fl_train.removeAllViews();
                BaseTrainPager baseTrainPager = new TestPager(context,topicTypeList.get(position-1).getTopic_type_id());
                View rootView = baseTrainPager.rootView;
                baseTrainPager.initData();
                fl_train.addView(rootView);
            }else{
                Toast.makeText(context,"题目数量未够开题",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class MyPullRefresh extends BaseAdapter {

        @Override
        public int getCount() {
            return topicTypeList.size();
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
            TestFragment.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_test, null);
                viewHolder = new TestFragment.ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.num = (TextView) convertView.findViewById(R.id.num);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (TestFragment.ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(topicTypeList.get(position).getTopic_type_name());
            String a = topicTypeList.get(position).getNum() + "";
            viewHolder.num.setText("共 " + a + " 道题");
            return convertView;
        }
    }

    static class ViewHolder {
        TextView name;
        TextView num;
    }
}
