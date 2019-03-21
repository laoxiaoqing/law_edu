package com.example.administrator.lawapp.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.ForumBean;
import com.example.administrator.lawapp.pager.ForumPager;
import com.example.administrator.lawapp.utils.CacheUtils;
import com.example.administrator.lawapp.utils.LogUtil;

import java.util.List;

/**
 * Created by 吴青晓 on 2019/3/18
 * Describe
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler> {

    private List<ForumBean.DataBean.CommentListBean> commentList;
    private final Context context;

    public MyRecyclerViewAdapter(Context context, List<ForumBean.DataBean.CommentListBean> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    /**
     * 相当于getView方法中创建的view和ViewHolder
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.item_comments, null);
        return new MyViewHodler(itemView);
    }

    /**
     * 数据和view绑定
     *
     * @param myViewHodler
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHodler myViewHodler, final int position) {
        //根据位置得到对应的数据
        if (commentList.get(position).getUser_name1() == null || commentList.get(position).getUser_name1() == "") {
            myViewHodler.user_name.setText(commentList.get(position).getUser_name());
        } else {
            myViewHodler.user_name.setText(commentList.get(position).getUser_name() + " 回复 " + commentList.get(position).getUser_name1());
        }
        Glide.with(context)
                .load(commentList.get(position).getUser_head())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.cases_upload)
                .error(R.mipmap.default1)
                .into(myViewHodler.iv_head);
        String user_id = CacheUtils.getString(context, "user_id");
        //判断该评论是否是别人的
        if (commentList.get(position).getComment_user_id() != Integer.parseInt(user_id)) {
            myViewHodler.comments_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.e("postion:"+position);
                    LogUtil.e("主题id:"+commentList.get(position).getTopic_id());
                    LogUtil.e("该评论人id:"+commentList.get(position).getComment_user_id());
                    LogUtil.e("comment_id"+commentList.get(position).getComment_id());
                    LogUtil.e("内容："+commentList.get(position).getContent());
                    ForumPager forumPager = new ForumPager(context);
                    forumPager.sendReply(commentList.get(position).getTopic_id(), commentList.get(position).getComment_user_id(),commentList.get(position).getUser_name());
                }
            });
        } else {
            myViewHodler.comments_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        myViewHodler.comments_content.setText(commentList.get(position).getContent());
        myViewHodler.comments_time.setText(commentList.get(position).getComment_date());
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return commentList.size();
    }
    //添加数据
    public void addData(int position,ForumBean.DataBean.CommentListBean data){
        commentList.add(data);
        LogUtil.e("添加数据");
        notifyItemInserted(commentList.size()-1);
        LogUtil.e("添加数据2");
    }
    public void delData(int position){
        commentList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHodler extends RecyclerView.ViewHolder {
        ImageView iv_head;
        TextView user_name;
        TextView comments_time;
        TextView comments_content;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            comments_time = (TextView) itemView.findViewById(R.id.comments_time);
            comments_content = (TextView) itemView.findViewById(R.id.comments_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

}
