package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.VideoMoreBean;
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

public class VideoActivity extends Activity {
    private PullToRefreshListView pull_listview;
    private List<VideoMoreBean.DataBean> videoBeanList;
    private List<VideoMoreBean.DataBean> videoMoreBeanList = null;
    private int pagenum = 2;
    private int pagesize = 8;
    private MyPullRefresh myadapter;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView = findViewById(R.id.tv_title);
        textView.setText("看法视频");
        pull_listview = findViewById(R.id.pull_refresh_list);
        pull_listview.setOnItemClickListener(new MyOnItemClickListener());
        myadapter = new MyPullRefresh();
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.VIDEO_URL + "?pagenum=1&pagesize=8");//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(VideoActivity.this, Constants.VIDEO_URL + "?pagenum=1&pagesize=8", result);
                LogUtil.e("请求成功" + result);
                pagenum = 2;
                manageData(result);
                videoMoreBeanList = null;
                pull_listview.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                //隐藏下拉刷新控件--不更新时间，显示联网请求失败
                pull_listview.onRefreshComplete();
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

    private void getMoreFromNet() {
        RequestParams params = new RequestParams(Constants.VIDEO_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize);//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(VideoActivity.this, Constants.VIDEO_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize, result);
                LogUtil.e("请求成功" + result);
                LogUtil.e(Constants.VIDEO_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize);
                //设置适配器
                manageDataMore(result);
                pull_listview.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("请求失败" + ex.getMessage());
                //隐藏下拉刷新控件--不更新时间，显示联网请求失败
                pull_listview.onRefreshComplete();
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

    private void manageData(String result) {
        VideoMoreBean videoMoreBean = parsedJson(result);
        videoBeanList = videoMoreBean.getData();
        LogUtil.e("进入处理数据");
        LogUtil.e(videoBeanList.get(0).getVideo_title());
        pull_listview.setAdapter(myadapter);
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagenum = 2;
                Toast.makeText(VideoActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(VideoActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
                if (videoMoreBeanList != null) {
                    LogUtil.e("videoMoreBeanListvideoMoreBeanListvideoMoreBeanListvideoMoreBeanListvideoMoreBeanListvideoMoreBeanList");
                    pagenum++;
                }
                getMoreFromNet();
            }
        });
        /*pull_listview.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(VideoActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void manageDataMore(String result) {
        VideoMoreBean videoMoreBean = parsedJson(result);
        videoMoreBeanList = videoMoreBean.getData();
        videoBeanList.addAll(videoMoreBeanList);
        myadapter.notifyDataSetChanged();
        pull_listview.onRefreshComplete();
        LogUtil.e(videoMoreBeanList.get(0).getVideo_title());
    }

    private VideoMoreBean parsedJson(String json) {
        Gson gson = new Gson();
        VideoMoreBean videoMoreBean = gson.fromJson(json, VideoMoreBean.class);
        return videoMoreBean;
    }

    private class MyPullRefresh extends BaseAdapter {
        @Override
        public int getCount() {
            return videoBeanList.size();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(VideoActivity.this, R.layout.item_video, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_video_item = convertView.findViewById(R.id.tv_video_item);
                viewHolder.tv_video_time = convertView.findViewById(R.id.tv_video_time);
                viewHolder.iv_video_item = convertView.findViewById(R.id.iv_video_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_video_item.setText(videoBeanList.get(position).getVideo_title());
            viewHolder.tv_video_time.setText(videoBeanList.get(position).getAdd_time());
            String iv_videoUrl1 = Constants.BASE_URL + videoBeanList.get(position).getVideo_picture();
            x.image().bind(viewHolder.iv_video_item, iv_videoUrl1);
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_video_item;
        TextView tv_video_time;
        ImageView iv_video_item;
    }

    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 1;
            //跳转页面
            Intent intent = new Intent(VideoActivity.this, DetailActivity.class);
            intent.putExtra("type", "video");
            intent.putExtra("dataId", videoBeanList.get(realPosition).getVideo_id() + "");
            startActivity(intent);
        }
    }
}
