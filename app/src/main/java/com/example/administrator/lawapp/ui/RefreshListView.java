package com.example.administrator.lawapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.lawapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 吴青晓 on 2019/2/22
 * Describe:自定义下拉刷新listView
 */
public class RefreshListView extends ListView {
    /**
     * 顶部轮播图部分
     */
    private  View bannerView;
    /**
     * 下拉刷新顶部和顶部轮播图
     */
    private LinearLayout ll_headerView;
    /**
     * 下拉刷新控件
     */
    private View ll_header_down;
    private ImageView iv_header;
    private TextView tv_stauts;
    private ProgressBar pb_status;
    private TextView tv_time;
    /**
     * 下拉刷新控件的高
     */
    private int pull_down_height;
    /**
     * 设置默认起始坐标
     */
    private float startY = -1;
    /**
     * 下拉刷新状态0表示
     */
    public static final int PULL_DOWN_REFRESH = 0;
    /**
     * 手松刷新状态1表示
     */
    public static final int RELEASE_REFRESH = 1;
    /**
     * 正在刷新状态2表示
     */
    public static final int REFRESHING = 2;
    /**
     * 当前状态
     */
    private int curentStatus = PULL_DOWN_REFRESH;
    /**
     * 上下图片切换
     */
    private Animation upAnimation;
    private Animation downAnimation;
    /**
     * 加载更多视图控件
     */
    private View footerView;
    /**
     * 加载底部控件的高
     */
    private int footerViewHeight;
    private OnRefreshListener mOnRefreshListener;
    /**
     * 是否已经加载更多
     */
    private boolean footerMore = false;
    /**
     * listView在Y轴上的坐标
     */
    private int listViewOnScreenY =-1;


    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 添加顶部轮播图
     * @param bannerView
     */
    public void addBannerView(View bannerView) {
        if (bannerView!=null){
            this.bannerView=bannerView;
            ll_headerView.addView(bannerView);
        }

    }


    /**
     * 监听控件的刷新
     */
    public interface OnRefreshListener {
        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();

        /**
         * 加载底部的时候回调这个方法
         */
        public void onLoadMore();
    }

    /**
     * 设置监听刷新,由外界设置
     */
    public void setmOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context, R.layout.refresh_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        //listview添加footer
        addFooterView(footerView);
        //监听listView的滚动
        setOnScrollListener(new MyOnScrollListener());
    }

    class MyOnScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当静止或者惯性滚动的时候
            // 惯性：SCROLL_STATE_FLING
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
                //并且是最后一条可见
                if (getLastVisiblePosition() >= getCount() - 1) {
                    //1.显示加载更多布局
                    footerView.setPadding(0, 0, 0, 0);
                    //2.状态改变
                    footerMore = true;
                    //3.回调接口
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    private void initAnimation() {
        upAnimation = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(180, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        ll_headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);
        //下拉刷新控件
        ll_header_down = ll_headerView.findViewById(R.id.ll_header_down);
        iv_header = (ImageView) ll_headerView.findViewById(R.id.iv_header);
        tv_stauts = (TextView) ll_headerView.findViewById(R.id.tv_status);
        pb_status = (ProgressBar) ll_headerView.findViewById(R.id.pb_status);
        tv_time = (TextView) ll_headerView.findViewById(R.id.tv_time);

        ll_header_down.measure(0, 0);//测量
        pull_down_height = ll_header_down.getMeasuredHeight();
        //默认隐藏下拉刷新控件
        ll_header_down.setPadding(0, -pull_down_height, 0, 0);


        //添加头
        RefreshListView.this.addHeaderView(ll_headerView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录起始坐标
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }
                //判断顶部轮播图是否完全显示，只有完全显示才会有下拉刷新
                boolean isDisplayBanner = isDisplayBanner();//true为完全显示
                if (!isDisplayBanner){
                    //加载更多
                    break;
                }

                //如果是正在刷新，就不让再刷新了
                if (curentStatus == REFRESHING) {
                    break;
                }
                //来到新的坐标
                float endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {
                    int paddingTop = (int) (-pull_down_height + distanceY);
                    if (paddingTop < 0 && curentStatus != PULL_DOWN_REFRESH) {
                        //下拉刷新状态
                        curentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewStatus();
                    } else if (paddingTop > 0 && curentStatus != RELEASE_REFRESH) {
                        //手松状态
                        curentStatus = RELEASE_REFRESH;
                        //更新状态
                        refreshViewStatus();
                    }
                    ll_header_down.setPadding(0, paddingTop, 0, 0);//动态显示下拉刷新状态
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (curentStatus == PULL_DOWN_REFRESH) {
                    ll_header_down.setPadding(0, -pull_down_height, 0, 0);//完全隐藏
                } else if (curentStatus == RELEASE_REFRESH) {
                    //设置正在刷新状态
                    curentStatus = REFRESHING;
                    refreshViewStatus();
                    ll_header_down.setPadding(0, 0, 0, 0);//完全显示
                    //回调接口
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断是否完全显示顶部轮播图
     * @return
     */
    private boolean isDisplayBanner() {
        if (bannerView!=null){
//1.得到ListView在屏幕上的坐标
            int[] location = new int[2];

            if (listViewOnScreenY==-1){
                getLocationOnScreen(location);
                listViewOnScreenY = location[1];
            }
            //2.得到顶部轮播图在屏幕的坐标
            bannerView.getLocationOnScreen(location);
            int bannerViewOnScreenY = location[1];
            if (listViewOnScreenY<=bannerViewOnScreenY){
                return true;
            }else {
                return false;
            }
        }else{
            return true;
        }


    }

    private void refreshViewStatus() {
        switch (curentStatus) {
            case PULL_DOWN_REFRESH://下拉刷新状态
                iv_header.startAnimation(downAnimation);
                tv_stauts.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH://手松刷新状态
                iv_header.startAnimation(upAnimation);
                tv_stauts.setText("手松刷新...");
                break;
            case REFRESHING://正在刷新状态
                tv_stauts.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_header.clearAnimation();
                iv_header.setVisibility(GONE);
                break;
        }
    }

    /**
     * 当联网成功和失败回调该方法，刷新状态的还原
     *
     * @param msg
     */
    public void setRefreshFinish(boolean msg) {
        if (footerMore) {
            footerMore = false;
            //加载更多布局
            footerView.setPadding(0, -footerViewHeight, 0, 0);
        } else {
            tv_stauts.setText("下拉刷新...");
            curentStatus = PULL_DOWN_REFRESH;
            iv_header.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_header.setVisibility(VISIBLE);
            //隐藏下拉刷新控件
            ll_header_down.setPadding(0, -pull_down_height, 0, 0);
            if (msg) {
                //设置最新时间
                tv_time.setText("上次更新时间:" + getSystemTime());
            }
        }


    }

    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
