package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.bean.ForumBean;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 主页面
 */
public class ForumPager extends BasePager {
    @ViewInject(R.id.pull_refresh_list)
    private PullToRefreshListView pullToRefreshListView;
    @ViewInject(R.id.pb_loading)
    private ProgressBar pb_loading;
    private MyForumAdapter myAdapter;
    private int pagesize=20;
    private List<ForumBean.DataBean> forumList;

    public ForumPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("社区页面数据初始化了");
        //1.设置标题
        tv_title.setText("社区");
        //2.联网请求得到数据创建视图
        View view = View.inflate(context, R.layout.forum_pager, null);
        x.view().inject(ForumPager.this, view);
        getDataFromNet();
        fl_content.removeAllViews();
        fl_content.addView(view);
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.FORUM_PAGER_URL+"?pagenum=1&pagesize="+pagesize+"&topic_type=1");//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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

    private void manageData(String json) {
        pb_loading.setVisibility(View.GONE);
        Gson gson = new Gson();
        ForumBean forumBean = gson.fromJson(json, ForumBean.class);
        forumList=forumBean.getData();
        myAdapter = new MyForumAdapter();
        pullToRefreshListView.setAdapter(myAdapter);

    }

    private class MyForumAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return forumList.size();
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
            ForumPager.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_forum, null);
                viewHolder = new ForumPager.ViewHolder();
                viewHolder.iv_head = convertView.findViewById(R.id.iv_head);
                viewHolder.tv_username = convertView.findViewById(R.id.tv_username);
                viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
                viewHolder.tv_createTime = convertView.findViewById(R.id.tv_createTime);
                viewHolder.delete = convertView.findViewById(R.id.delete);
                viewHolder.more = convertView.findViewById(R.id.more);
                viewHolder.lv_comments = convertView.findViewById(R.id.lv_comments);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ForumPager.ViewHolder) convertView.getTag();
            }
            viewHolder.tv_username.setText(forumList.get(position).getUser_name());
            viewHolder.tv_content.setText(forumList.get(position).getForum_content());
            viewHolder.tv_createTime.setText(forumList.get(position).getForum_date());
            Glide.with(context)
                    .load(forumList.get(position).getUser_head())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.cases_upload)
                    .error(R.mipmap.cases_upload)
                    .into(viewHolder.iv_head);
            return convertView;
        }
    }
    static class ViewHolder {
        ImageView iv_head;
        TextView tv_username;
        TextView tv_content;
        TextView tv_createTime;
        TextView delete;
        ImageView more;
        ListView lv_comments;

    }
}
