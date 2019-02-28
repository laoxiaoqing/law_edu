package com.example.administrator.lawapp.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BaseTrainFragment;
import com.example.administrator.lawapp.bean.PapersBean;
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
 * Describe
 */
public class PaperFragment extends BaseTrainFragment {
    private ImageView ivBack;
    private TextView tvTitle;
    private PullToRefreshListView pullRefreshList;
    private View view;
    private int pagenum = 2;
    private int pagesize = 15;
    private PapersBean Bean;
    private List<PapersBean.DataBean> papersList;
    private List<PapersBean.DataBean> papersMoreList = null;
    private MyPullRefresh myadapter;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.papers_fragment, null);
        findViews();
        pullRefreshList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        pullRefreshList.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    private void findViews() {
        pullRefreshList = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
    }

    private void getDataFromNet() {
        String user_id = CacheUtils.getString(context, "user_id");
        LogUtil.e("username:" + user_id);
        RequestParams params = new RequestParams(Constants.PAPERSTOIC_URL + "?pagenum=1&pagesize=" + pagesize + "&user_id=" + user_id);//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageData(result);
                papersMoreList = null;
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
        Bean = gson.fromJson(json, PapersBean.class);
        papersList = Bean.getData();
        myadapter = new MyPullRefresh();
        pullRefreshList.setAdapter(myadapter);
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagenum = 2;
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (papersMoreList != null) {
                    pagenum++;
                }
                getMoreFromNet();
            }
        });

    }

    private void getMoreFromNet() {
        String user_id = CacheUtils.getString(context, "user_id");
        LogUtil.e("username:" + user_id);
        RequestParams params = new RequestParams(Constants.PAPERSTOIC_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize + "&user_id=" + user_id);//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                manageDataMore(result);
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

    private void manageDataMore(String json) {
        Gson gson = new Gson();
        Bean = gson.fromJson(json, PapersBean.class);
        papersMoreList = Bean.getData();
        papersList.addAll(papersMoreList);
        myadapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        super.initData();
        LogUtil.e("答卷页面初始化了");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                LogUtil.e("点返回按钮");
            }
        });
        tvTitle.setText("我练习的试卷");
        getDataFromNet();
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    private class MyPullRefresh extends BaseAdapter {
        @Override
        public int getCount() {
            return papersList.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            PaperFragment.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_paper, null);
                viewHolder = new PaperFragment.ViewHolder();
                viewHolder.name = convertView.findViewById(R.id.tv_paper_name);
                viewHolder.score = convertView.findViewById(R.id.score);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PaperFragment.ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(papersList.get(position).getTopic_type_name());
            viewHolder.score.setText(papersList.get(position).getPapers_score());
            return convertView;
        }
    }

    static class ViewHolder {
        TextView name;
        TextView score;
    }
}
