package com.example.administrator.lawapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.lawapp.R;
import com.example.administrator.lawapp.bean.AudioMoreBean;
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

import static android.media.CamcorderProfile.get;

public class AudioActivity extends Activity {
    public static final String READ_ARRAY_TIME = "read_array_time";
    private PullToRefreshListView pull_listview;
    private List<AudioMoreBean.DataBean> AudioBeanList;
    private List<AudioMoreBean.DataBean> AudioMoreBeanList = null;
    private int pagenum = 2;
    private int pagesize = 8;
    private MyPullRefresh myadapter;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_audio);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView = findViewById(R.id.tv_title);
        textView.setText("普法视听");
        pull_listview = findViewById(R.id.pull_refresh_list);
        //设置lisrview点击监听
        pull_listview.setOnItemClickListener(new MyOnItemClickListener());
        myadapter = new MyPullRefresh();
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.AUDITORIUM_URL + "?pagenum=1&pagesize=8");//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(AudioActivity.this, Constants.AUDITORIUM_URL + "?pagenum=1&pagesize=8", result);
                LogUtil.e("请求成功" + result);
                pagenum = 2;
                manageData(result);
                AudioMoreBeanList = null;
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
        RequestParams params = new RequestParams(Constants.AUDITORIUM_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize);//写url
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(AudioActivity.this, Constants.AUDITORIUM_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize, result);
                LogUtil.e("请求成功" + result);
                LogUtil.e(Constants.AUDITORIUM_URL + "?pagenum=" + pagenum + "&pagesize=" + pagesize);
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
        AudioMoreBean AudioMoreBean = parsedJson(result);
        AudioBeanList = AudioMoreBean.getData();
        LogUtil.e("进入处理数据");
        LogUtil.e(AudioBeanList.get(0).getAuditorium_title());
        pull_listview.setAdapter(myadapter);
        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagenum = 2;
                Toast.makeText(AudioActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
                getDataFromNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(AudioActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
                if (AudioMoreBeanList != null) {
                    pagenum++;
                }
                getMoreFromNet();
            }
        });
    }

    private void manageDataMore(String result) {
        AudioMoreBean AudioMoreBean = parsedJson(result);
        AudioMoreBeanList = AudioMoreBean.getData();
        AudioBeanList.addAll(AudioMoreBeanList);
        myadapter.notifyDataSetChanged();
        pull_listview.onRefreshComplete();
        LogUtil.e(AudioMoreBeanList.get(0).getAuditorium_title());
    }

    private AudioMoreBean parsedJson(String json) {
        Gson gson = new Gson();
        AudioMoreBean AudioMoreBean = gson.fromJson(json, AudioMoreBean.class);
        return AudioMoreBean;
    }

    private class MyPullRefresh extends BaseAdapter {
        @Override
        public int getCount() {
            return AudioBeanList.size();
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
            AudioActivity.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(AudioActivity.this, R.layout.item_audio, null);
                viewHolder = new AudioActivity.ViewHolder();
                viewHolder.tv_audio_item = convertView.findViewById(R.id.tv_audio_item);
                viewHolder.tv_audio_time = convertView.findViewById(R.id.tv_audio_time);
                viewHolder.iv_audio_item = convertView.findViewById(R.id.iv_audio_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AudioActivity.ViewHolder) convertView.getTag();
            }
            viewHolder.tv_audio_item.setText(AudioBeanList.get(position).getAuditorium_title());
            viewHolder.tv_audio_time.setText(AudioBeanList.get(position).getAuditorium_time());
            //String iv_videoUrl1 = Constants.BASE_URL + AudioBeanList.get(position).getAuditorium_picture();
            //x.image().bind(viewHolder.iv_audio_item, AudioBeanList.get(position).getAuditorium_picture());
            Glide.with(AudioActivity.this)
                    .load(AudioBeanList.get(position).getAuditorium_picture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.cases_upload)
                    .error(R.mipmap.cases_upload)
                    .into(viewHolder.iv_audio_item);
            String timeArray = CacheUtils.getString(AudioActivity.this, READ_ARRAY_TIME);
            if (timeArray.contains(AudioBeanList.get(position).getAuditorium_time())) {
                viewHolder.tv_audio_item.setTextColor(Color.GRAY);
            } else {
                //设置黑色
                viewHolder.tv_audio_item.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_audio_item;
        TextView tv_audio_time;
        ImageView iv_audio_item;
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position - 1;
            AudioMoreBean.DataBean data = AudioBeanList.get(realPosition);
            Toast.makeText(AudioActivity.this, "data==" + data.getAuditorium_title(), Toast.LENGTH_SHORT).show();
            //1.取出保存的时间集合
            String timeArray = CacheUtils.getString(AudioActivity.this, READ_ARRAY_TIME);
            //2.判断是否存在，如果不存在，才保存，并且刷新适配器
            if (!timeArray.contains(data.getAuditorium_time() + "")) {//
                CacheUtils.putString(AudioActivity.this,
                        READ_ARRAY_TIME, timeArray + data.getAuditorium_time());
                //刷新适配器
                myadapter.notifyDataSetChanged();//getcount -- >getView
            }

            //跳转页面
            Intent intent = new Intent(AudioActivity.this, DetailActivity.class);
            intent.putExtra("type", "audio");
            intent.putExtra("dataId", data.getAuditorium_id() + "");
            startActivity(intent);

        }
    }
}
