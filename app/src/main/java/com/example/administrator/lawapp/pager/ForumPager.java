package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.activity.EditorActivity;
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.adaper.MyRecyclerViewAdapter;
import com.example.administrator.lawapp.base.BasePager;
import com.example.administrator.lawapp.bean.AudioMoreBean;
import com.example.administrator.lawapp.bean.ForumBean;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.Constants;
import com.example.administrator.lawapp.utils.LogUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private List<ForumBean.DataBean> forumList;
    private List<ForumBean.DataBean> forumMoreList;
    //private List<ForumBean.DataBean.CommentListBean> commentList;
    private LayoutInflater mInflater;
    private PopupWindow window;
    private PopupWindow editWindow;
    private String user_id;
    private int pagenum = 2;
    private int pagesize = 20;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private List<ForumBean.DataBean> forumMoreBean;


    public ForumPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //1.设置标题
        tv_title.setText("社区");
        iv_forum.setVisibility(View.VISIBLE);
        iv_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditorActivity.class);
                String userid=CacheUtils.getString(context,"user_id");
                intent.putExtra("user_id",userid);
                context.startActivity(intent);
            }
        });
        //2.联网请求得到数据创建视图
        View view = View.inflate(context, R.layout.forum_pager, null);
        x.view().inject(ForumPager.this, view);
        getDataFromNet();
        fl_content.removeAllViews();
        fl_content.addView(view);
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.FORUM_PAGER_URL + "?pagenum=1&pagesize=" + pagesize + "&topic_type=1");//写url
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                //设置适配器
                manageData(result);
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("请求onCancelled" + cex.getMessage());
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                LogUtil.e("请求结束");
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }

    private void manageData(String json) {
        pb_loading.setVisibility(View.GONE);
        Gson gson = new Gson();
        ForumBean forumBean = gson.fromJson(json, ForumBean.class);
        forumList = forumBean.getData();
        myAdapter = new MyForumAdapter();
        pullToRefreshListView.setAdapter(myAdapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagenum = 2;

                LogUtil.e("下拉刷新");
                getDataFromNet();
                LogUtil.e("下拉刷新");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(context, "Pull Up!", Toast.LENGTH_SHORT).show();
                if (forumMoreList != null) {
                    pagenum++;
                }
                getMoreFromNet();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new MyOnItemClickListener());

    }
    private void getMoreFromNet() {
        RequestParams params = new RequestParams(Constants.FORUM_PAGER_URL + "?pagenum="+pagenum+"&pagesize=" + pagesize + "&topic_type=1");//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                LogUtil.e(Constants.AUDITORIUM_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize);
                //设置适配器
                manageDataMore(result);
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                //隐藏下拉刷新控件--不更新时间，显示联网请求失败
                pullToRefreshListView.onRefreshComplete();
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

    private void manageDataMore(String result) {
        ForumBean forumBean = parsedJson(result);
        forumMoreList = forumBean.getData();
        forumList.addAll(forumMoreList);
        myAdapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
    }

    private ForumBean parsedJson(String result) {
        Gson gson = new Gson();
        ForumBean forumBean = gson.fromJson(result, ForumBean.class);
        return forumBean;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            List<ForumBean.DataBean.CommentListBean> commentList = forumList.get(position).getCommentList();
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
                viewHolder.rv_comments = convertView.findViewById(R.id.rv_comments);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ForumPager.ViewHolder) convertView.getTag();
            }
            if (commentList == null || commentList.size() == 0) {
                viewHolder.rv_comments.setVisibility(View.GONE);
            } else {
                viewHolder.rv_comments.setVisibility(View.VISIBLE);
                /*myComments = new MyComments(commentList);
                viewHolder.lv_comments.setAdapter(myComments);*/

                //设置适配器
                myRecyclerViewAdapter=new MyRecyclerViewAdapter(context,commentList);
                viewHolder.rv_comments.setAdapter(myRecyclerViewAdapter);

                //LayoutManager
                viewHolder.rv_comments.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            }
            user_id=CacheUtils.getString(context,"user_id");
            if (forumList.get(position).getUser_id()==Integer.parseInt(user_id)){
                LogUtil.e("显示删除");
                viewHolder.delete.setVisibility(View.VISIBLE);
            }else {
                LogUtil.e("不显示删除");
                viewHolder.delete.setVisibility(View.GONE);
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
            viewHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendReply(forumList.get(position).getForum_id(), 0,"");
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteData(position,forumList.get(position).getForum_id());
                }
            });
            return convertView;
        }
    }

    private void deleteData(final int position, int forum_id) {
        RequestParams params = new RequestParams(Constants.FORUM_PAGER_URL+"/del/"+forum_id);//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                forumList.remove(position);
                myAdapter.notifyDataSetChanged();
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

    static class ViewHolder {
        ImageView iv_head;
        TextView tv_username;
        TextView tv_content;
        TextView tv_createTime;
        TextView delete;
        ImageView more;
        RecyclerView rv_comments;
    }

    //弹出键盘
    public void sendReply(final int topic_id, final int to_user_id, final String to_user_name) {
        View editView = View.inflate(context, R.layout.replay_input, null);
        editWindow = new PopupWindow(editView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editWindow.setOutsideTouchable(true);
        editWindow.setFocusable(true);
        editWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        final EditText replyEdit = (EditText) editView.findViewById(R.id.reply);
        replyEdit.setFocusable(true);
        replyEdit.requestFocus();
        // 以下两句不能颠倒
        editWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        // 显示键盘
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        editWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
            }
        });
        //发送消息
        Button button = editView.findViewById(R.id.send_msg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (to_user_id == 0) {
                    sendReplyNetData(topic_id, 0, replyEdit.getText().toString().trim(),to_user_name);
                } else {
                    sendReplyNetData(topic_id, to_user_id, replyEdit.getText().toString().trim(),to_user_name);
                }
            }
        });
        if (window != null) window.dismiss();
    }

    public void sendReplyNetData(int topic_id, final Integer to_user_id, final String content, String to_user_name) {
        String userid = CacheUtils.getString(context, "user_id");
        RequestParams params = new RequestParams(Constants.FORUM_comment_URL);//写url
        final ForumBean.DataBean.CommentListBean bean= new ForumBean.DataBean.CommentListBean();
        if (to_user_id == 0) {
            LogUtil.e("to_user_id1:"+to_user_id);
            params.setBodyContent("{\"topic_id\":\"" + topic_id
                    + "\",\"topic_type\":\"" + 1
                    + "\",\"content\":\"" + content
                    + "\",\"comment_user_id\":\"" + Integer.parseInt(userid)
                    + "\"}");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            bean.setComment_date(df.format(new Date()));
            bean.setContent(content);
            String head = CacheUtils.getString(context,"user_head");
            String name = CacheUtils.getString(context,"user_name");
            bean.setUser_head(Constants.IMAGEPATH+head);
            bean.setUser_name(name);
        } else {
            LogUtil.e("to_user_id2:"+to_user_id);
            params.setBodyContent("{\"topic_id\":\"" + topic_id
                    + "\",\"topic_type\":\"" + 1
                    + "\",\"content\":\"" + content
                    + "\",\"comment_user_id\":\"" + Integer.parseInt(userid)
                    + "\",\"to_user_id\":\"" + to_user_id
                    + "\"}");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            bean.setComment_date(df.format(new Date()));
            bean.setContent(content);
            String head = CacheUtils.getString(context,"user_head");
            String name = CacheUtils.getString(context,"user_name");
            String user_id = CacheUtils.getString(context,"user_id");
            bean.setUser_head(Constants.IMAGEPATH+head);
            bean.setUser_name(name);
            bean.setUser_name1(to_user_name);
            bean.setComment_user_id(Integer.parseInt(user_id));
            bean.setTopic_type(1);
        }
        final ForumBean.DataBean.CommentListBean bean2=bean;
        params.setConnectTimeout(3000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);
                Toast.makeText(context,"发送成功",Toast.LENGTH_SHORT).show();
                if (to_user_id == 0) {
                    LogUtil.e("fffffff");
                    myRecyclerViewAdapter.addData(0,bean2);
                }/*else {
                    LogUtil.e("aaaaaabbb");
                    myRecyclerViewAdapter.addData(0,bean2);
                }*/
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();
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

    private void manageData2(String json) {
        Gson gson = new Gson();
        LogUtil.e("json"+json);
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.e("点击第" + position + "个");
        }
    }

}
