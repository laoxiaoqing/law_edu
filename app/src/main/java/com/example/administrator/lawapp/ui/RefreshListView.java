package com.example.administrator.lawapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.lawapp.R;

/**
 * Created by 吴青晓 on 2019/2/22
 * Describe:自定义下拉刷新listView
 */
public class RefreshListView extends ListView {
    /**
     * 下拉刷新和顶部和顶部轮播图
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
    private int headerView_height;
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

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
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
        headerView_height = ll_header_down.getMeasuredHeight();
        //默认隐藏下拉刷新控件
        ll_header_down.setPadding(0, -headerView_height, 0, 0);


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
                //如果是正在刷新，就不让再刷新了
                if (curentStatus == REFRESHING) {
                    break;
                }
                //来到新的坐标
                float endY = ev.getY();
                float distanceY = endY - startY;
                if (distanceY > 0) {
                    int paddingTop = (int) (-headerView_height + distanceY);
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
                    ll_header_down.setPadding(0, -headerView_height, 0, 0);//完全隐藏
                } else if (curentStatus == RELEASE_REFRESH) {
                    curentStatus = REFRESHING;//设置正在刷新状态
                    ll_header_down.setPadding(0, 0, 0, 0);//完全显示
                    refreshViewStatus();
                }
                break;
        }
        return super.onTouchEvent(ev);
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
}
