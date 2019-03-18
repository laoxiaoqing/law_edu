package com.example.administrator.lawapp.pager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.administrator.lawapp.activity.MainActivity;
import com.example.administrator.lawapp.base.BasePager;
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
    private int pagesize = 20;
    private List<ForumBean.DataBean> forumList;
    private MyComments myComments;//评论的适配器
    //private List<ForumBean.DataBean.CommentListBean> commentList;
    private LayoutInflater mInflater;
    private PopupWindow window;
    private PopupWindow editWindow;


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
               /* pagenum = 2;
                Toast.makeText(AudioActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();*/
                LogUtil.e("下拉刷新");
                getDataFromNet();
                LogUtil.e("下拉刷新");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(context, "Pull Up!", Toast.LENGTH_SHORT).show();
                /*if (AudioMoreBeanList != null) {
                    pagenum++;
                }
                getMoreFromNet();*/
                pullToRefreshListView.onRefreshComplete();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new MyOnItemClickListener());

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
                viewHolder.lv_comments = convertView.findViewById(R.id.lv_comments);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ForumPager.ViewHolder) convertView.getTag();
            }
            if (commentList == null || commentList.size() == 0) {
                viewHolder.lv_comments.setVisibility(View.GONE);
            } else {
                viewHolder.lv_comments.setVisibility(View.VISIBLE);
                myComments = new MyComments(commentList);
                viewHolder.lv_comments.setAdapter(myComments);
                viewHolder.lv_comments.setOnItemClickListener(new MyCommentsItemListener());
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
                    sendReply(forumList.get(position).getForum_id(), 0);
                }
            });
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

    private class MyComments extends BaseAdapter {


        private final List<ForumBean.DataBean.CommentListBean> commentList;

        public MyComments(List<ForumBean.DataBean.CommentListBean> commentList) {
            this.commentList = commentList;
        }

        @Override
        public int getCount() {
            return commentList.size();
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
            ForumPager.ViewHolderComments viewHolderComments;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_comments, null);
                viewHolderComments = new ForumPager.ViewHolderComments();
                viewHolderComments.iv_head = convertView.findViewById(R.id.iv_head);
                viewHolderComments.user_name = convertView.findViewById(R.id.user_name);
                viewHolderComments.comments_time = convertView.findViewById(R.id.comments_time);
                viewHolderComments.comments_content = convertView.findViewById(R.id.comments_content);
                convertView.setTag(viewHolderComments);
            } else {
                viewHolderComments = (ForumPager.ViewHolderComments) convertView.getTag();
            }
            if (commentList.get(position).getUser_name1() == null || commentList.get(position).getUser_name1() == "") {
                viewHolderComments.user_name.setText(commentList.get(position).getUser_name());
            } else {
                viewHolderComments.user_name.setText(commentList.get(position).getUser_name() + " 回复 " + commentList.get(position).getUser_name1());
            }
            Glide.with(context)
                    .load(commentList.get(position).getUser_head())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.cases_upload)
                    .error(R.mipmap.default1)
                    .into(viewHolderComments.iv_head);
            //mInflater = LayoutInflater.from(mainActivity);
            String user_id = CacheUtils.getString(context, "user_id");
            //判断该评论是否是别人的
            if (commentList.get(position).getComment_user_id() != Integer.parseInt(user_id)) {
                viewHolderComments.comments_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e("postion:"+position);
                        LogUtil.e("主题id:"+commentList.get(position).getTopic_id());
                        LogUtil.e("该评论人id:"+commentList.get(position).getComment_user_id());
                        LogUtil.e("comment_id"+commentList.get(position).getComment_id());
                        LogUtil.e("内容："+commentList.get(position).getContent());
                        sendReply(commentList.get(position).getTopic_id(), commentList.get(position).getComment_user_id());
                    }
                });
            } else {
                viewHolderComments.comments_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            viewHolderComments.comments_content.setText(commentList.get(position).getContent());
            viewHolderComments.comments_time.setText(commentList.get(position).getComment_date());
            return convertView;
        }
    }
    //弹出键盘
    private void sendReply(final int topic_id, final int to_user_id) {
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
                    sendReplyNetData(topic_id, 0, replyEdit.getText().toString().trim());
                } else {
                    sendReplyNetData(topic_id, to_user_id, replyEdit.getText().toString().trim());
                }
            }
        });
        if (window != null) window.dismiss();
    }

    private void sendReplyNetData(int topic_id, Integer to_user_id, String content) {
        String userid = CacheUtils.getString(context, "user_id");
        LogUtil.e("发送消息1");
        RequestParams params = new RequestParams(Constants.FORUM_comment_URL);//写url
        if (to_user_id == 0) {
            LogUtil.e("发送消息2");
            params.setBodyContent("{\"topic_id\":\"" + topic_id
                    + "\",\"topic_type\":\"" + 1
                    + "\",\"content\":\"" + content
                    + "\",\"comment_user_id\":\"" + Integer.parseInt(userid)
                    + "\"}");
            LogUtil.e("发送消息3");
        } else {
            LogUtil.e("发送消息4");
            params.setBodyContent("{\"topic_id\":\"" + topic_id
                    + "\",\"topic_type\":\"" + 1
                    + "\",\"content\":\"" + content
                    + "\",\"comment_user_id\":\"" + Integer.parseInt(userid)
                    + "\",\"to_user_id\":\"" + to_user_id
                    + "\"}");
            LogUtil.e("发送消息5");
        }
        params.setConnectTimeout(3000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求成功" + result);

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

    static class ViewHolderComments {
        ImageView iv_head;
        TextView user_name;
        TextView comments_time;
        TextView comments_content;
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.e("点击第" + position + "个");
        }
    }

    private class MyCommentsItemListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.e("内部类" + position + "个");
        }
    }
}
