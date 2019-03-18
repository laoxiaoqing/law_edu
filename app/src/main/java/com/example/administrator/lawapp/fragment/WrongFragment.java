package com.example.administrator.lawapp.fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.base.BaseTrainPager;
import com.example.administrator.lawapp.bean.TopicTypeBean;
import com.example.administrator.lawapp.trainpager.PapePager;
import com.example.administrator.lawapp.trainpager.WrongPager;
import com.example.administrator.lawapp.utils.CacheUtils;
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
 * Describe 错题查看
 */
public class WrongFragment extends BaseTrainFragment {
    private View view;
    private ImageView ivBack;
    private TextView tvTitle;
    private PullToRefreshListView pullRefreshList;
    public TopicTypeBean Bean;
    public List<TopicTypeBean.DataBean> topicTypeList;
    public MywPullRefresh myadapter;
    private FrameLayout fl_train;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.wrong_fragment, null);
        findViews();

        return view;
    }

    private void findViews() {
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                LogUtil.e("点返回按钮");
            }
        });
        tvTitle.setText("错题浏览");
        pullRefreshList = (PullToRefreshListView) view.findViewById(R.id.pullrefreshlist);
        pullRefreshList.setOnItemClickListener(new MyOnItemClickListener());
        fl_train = (FrameLayout) view.findViewById(R.id.fl_train);
    }



    private void getDataFromNet() {
        String user_id = CacheUtils.getString(context, "user_id");
        LogUtil.e("username:" + user_id);
        RequestParams params = new RequestParams(Constants.TOPIC_WRONG_URL + user_id);//写url
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
        LogUtil.e("整理数据");
        Gson gson = new Gson();
        Bean = gson.fromJson(json, TopicTypeBean.class);
        topicTypeList = Bean.getData();
        myadapter = new MywPullRefresh();
        pullRefreshList.setAdapter(myadapter);
        LogUtil.e("处理数据" + topicTypeList.get(0).getTopic_type_name());

        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getDataFromNet();
            }
        });
    }
    @Override
    protected void initData() {
        super.initData();
        LogUtil.e("错题页面初始化了");
        getDataFromNet();
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            fl_train.removeAllViews();
            LogUtil.e("position:"+position);
            BaseTrainPager baseTrainPager = new WrongPager(context,topicTypeList.get(position-1).getTopic_type_id(),topicTypeList.get(position-1).getTopic_type_name());
            View rootView = baseTrainPager.rootView;
            baseTrainPager.initData();
            fl_train.addView(rootView);
        }
    }


    private class MywPullRefresh extends BaseAdapter {
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
            LogUtil.e("cccc");
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WrongFragment.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_wrong, null);
                viewHolder = new WrongFragment.ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name2);
                viewHolder.num = (TextView) convertView.findViewById(R.id.num2);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (WrongFragment.ViewHolder) convertView.getTag();
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
