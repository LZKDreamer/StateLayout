package com.lzk.statelayout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lzk.statelayout.R;
import com.lzk.statelayout.widget.LoadingBar;

/**
 * 状态页面Layout
 * @author LiaoZhongKai
 * @date 2019/12/16.
 */
public class StateLayout extends FrameLayout implements View.OnClickListener {

    /*
    * 状态值
    * */
    private static final int STATE_LOADING = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_NET_ERROR = 3;
    private static final int STATE_NORMAL = 4;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    /*
    * 加载页面
    * */
    private View mLoadingView;
    private String mLoadingTipStr;
    private TextView mLoadingTipTv;

    /*空页面*/
    private View mEmptyView;
    private Drawable mEmptyDrawable;
    private String mEmptyTipStr;
    private ImageView mEmptyImg;
    private TextView mEmptyTipTv;

    /*错误页面*/
    private View mErrorView;
    private Drawable mErrorDrawable;
    private String mErrorTipStr;
    private ImageView mErrorImg;
    private TextView mErrorTipTv;

    /*网络错误*/
    private View mNetErrorView;
    private Drawable mNetErrorDrawable;
    private ImageView mNetErrorImg;
    private TextView mNetErrorTipTv;


    /*当前状态*/
    private int mCurrentState = STATE_LOADING;

    /*默认图片*/


    /*默认提示文字*/
    private String mNetErrorTipStr;

    /*默认提示文字颜色、大小*/
    private int mTipColor;
    private int mTipSize;

    /*默认重试文字、颜色、大小、样式*/
    private String mRetryStr;
    private int mRetryColor;
    private int mRetrySize;
    private Drawable mRetryDrawable;

    /*LoadingBar的颜色、高度*/
    private LoadingBar mLoadingBar;
    private int mLoadingBarColor;

    /*重试TextView数组*/
    private TextView[] mRetryTvArray;

    /*回调接口*/
    private OnReloadListener mOnReloadListener;
    private OnStateListener mOnStateListener;


    /*自定义状态View*/
    /*标记是否是自定义状态View*/
    private boolean isCustomLoading;
    private boolean isCustomEmpty;
    private boolean isCustomError;
    private boolean isCustomNetError;
    /*自定义布局ResId*/
    private int mCustomLoadingResId;
    private int mCustomEmptyResId;
    private int mCustomErrorResId;
    private int mCustomNetErrorResId;

    public StateLayout(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context,AttributeSet attrs){
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        /*自定义布局文件*/
        mCustomLoadingResId = array.getResourceId(R.styleable.StateLayout_state_loading_layout,NO_ID);
        mCustomEmptyResId = array.getResourceId(R.styleable.StateLayout_state_empty_layout,NO_ID);
        mCustomErrorResId = array.getResourceId(R.styleable.StateLayout_state_error_layout,NO_ID);
        mCustomNetErrorResId = array.getResourceId(R.styleable.StateLayout_state_net_error_layout,NO_ID);

        /*加载页面*/
        mLoadingTipStr = array.getString(R.styleable.StateLayout_state_loading_tip);

        /*空页面*/
        mEmptyDrawable = array.getDrawable(R.styleable.StateLayout_state_empty_img);
        mEmptyTipStr = array.getString(R.styleable.StateLayout_state_empty_tip);

        /*错误页面*/
        mErrorDrawable = array.getDrawable(R.styleable.StateLayout_state_error_img);
        mErrorTipStr = array.getString(R.styleable.StateLayout_state_error_tip);

        /*网络错误页面*/
        mNetErrorDrawable = array.getDrawable(R.styleable.StateLayout_state_net_error_img);
        mNetErrorTipStr = array.getString(R.styleable.StateLayout_state_net_error_tip);


        /*图片*/
        mRetryDrawable = array.getDrawable(R.styleable.StateLayout_state_retry_drawable);
        /*文字*/
        mRetryStr = array.getString(R.styleable.StateLayout_state_retry_text);
        /*颜色*/
        mTipColor = array.getColor(R.styleable.StateLayout_state_tip_text_color,
                ContextCompat.getColor(context,R.color.state_text_color));
        mRetryColor = array.getColor(R.styleable.StateLayout_state_retry_text_color,
                ContextCompat.getColor(context,R.color.state_text_color));
        /*文字大小*/
        mTipSize = array.getDimensionPixelSize(R.styleable.StateLayout_state_tip_text_size,
                14);
        mRetrySize = array.getDimensionPixelSize(R.styleable.StateLayout_state_retry_text_size,
                14);

        /*LoadingBar*/
        mLoadingBarColor = array.getColor(R.styleable.StateLayout_state_loading_bar_color, Color.parseColor("#1ead7e"));

        array.recycle();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        /*状态View*/
        /*加载*/
        if (mCustomLoadingResId != NO_ID){
            isCustomLoading = true;
            mLoadingView = mLayoutInflater.inflate(mCustomLoadingResId,this,false);
        }else {
            isCustomLoading = false;
            mLoadingView = mLayoutInflater.inflate(R.layout.layout_loading_view,this,false);
        }

        /*空*/
        if (mCustomEmptyResId != NO_ID){
            isCustomEmpty = true;
            mEmptyView = mLayoutInflater.inflate(mCustomEmptyResId,this,false);
        }else {
            isCustomEmpty = false;
            mEmptyView = mLayoutInflater.inflate(R.layout.layout_empty_view,this,false);
        }
        /*错误*/
        if (mCustomErrorResId != NO_ID){
            isCustomError = true;
            mErrorView = mLayoutInflater.inflate(mCustomErrorResId,this,false);
        }else {
            isCustomError = false;
            mErrorView = mLayoutInflater.inflate(R.layout.layout_error_view,this,false);
        }
        /*网络错误*/
        if (mCustomNetErrorResId != NO_ID){
            isCustomNetError = true;
        }else {
            isCustomNetError = false;
            mNetErrorView = mLayoutInflater.inflate(R.layout.layout_network_error_view,this,false);
        }
        addView(mEmptyView);
        addView(mErrorView);
        addView(mNetErrorView);
        addView(mLoadingView);
        initView();
        initData();
        initClickListener();
    }

    private void initView(){
        /*设置初始状态*/
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mNetErrorView.setVisibility(GONE);
        showPageContentView(false);

        mRetryTvArray = new TextView[3];
        if (!isCustomLoading){
            mLoadingTipTv = mLoadingView.findViewById(R.id.state_loading_tip_tv);
            mLoadingBar = mLoadingView.findViewById(R.id.state_loading_bar);
        }
        if (!isCustomEmpty){
            mEmptyImg = mEmptyView.findViewById(R.id.state_empty_iv);
            mEmptyTipTv = mEmptyView.findViewById(R.id.state_empty_tip_tv);
            mRetryTvArray[0] = mEmptyView.findViewById(R.id.state_retry_tv);
        }
        if (!isCustomError){
            mErrorImg = mErrorView.findViewById(R.id.state_error_iv);
            mErrorTipTv = mErrorView.findViewById(R.id.state_error_tip_tv);
            mRetryTvArray[1] = mErrorView.findViewById(R.id.state_retry_tv);
        }
        if (!isCustomNetError){
            mNetErrorImg = mNetErrorView.findViewById(R.id.state_net_error_iv);
            mNetErrorTipTv = mNetErrorView.findViewById(R.id.state_net_error_tv);
            mRetryTvArray[2] =  mNetErrorView.findViewById(R.id.state_retry_tv);
        }
    }

    private void initData(){
        /*加载*/
        if (!isCustomLoading){
            if (!TextUtils.isEmpty(mLoadingTipStr)){
                mLoadingTipTv.setText(mLoadingTipStr);
            }
            mLoadingTipTv.setTextSize(mTipSize);
            mLoadingTipTv.setTextColor(mTipColor);
            mLoadingBar.setBarColor(mLoadingBarColor);
        }
        /*空*/
        if (!isCustomEmpty){
            if (mEmptyDrawable != null){
                mEmptyImg.setImageDrawable(mEmptyDrawable);
            }
            if (!TextUtils.isEmpty(mEmptyTipStr)){
                mEmptyTipTv.setText(mEmptyTipStr);
            }
            mEmptyTipTv.setTextSize(mTipSize);
            mEmptyTipTv.setTextColor(mTipColor);
        }

        /*错误*/
        if (!isCustomError){
            if (mErrorDrawable != null){
                mErrorImg.setImageDrawable(mErrorDrawable);
            }
            if (!TextUtils.isEmpty(mErrorTipStr)){
                mErrorTipTv.setText(mErrorTipStr);
            }
            mErrorTipTv.setTextSize(mTipSize);
            mErrorTipTv.setTextColor(mTipColor);
        }

        /*网络错误*/
        if (!isCustomNetError){
            if (mNetErrorDrawable != null){
                mNetErrorImg.setImageDrawable(mNetErrorDrawable);
            }
            if (!TextUtils.isEmpty(mNetErrorTipStr)){
                mNetErrorTipTv.setText(mNetErrorTipStr);
            }
            mNetErrorTipTv.setTextSize(mTipSize);
            mNetErrorTipTv.setTextColor(mTipColor);
        }

        /*重试*/
        for (TextView retryTv:mRetryTvArray){
            if (retryTv != null){
                retryTv.setTextSize(mRetrySize);
                retryTv.setTextColor(mRetryColor);
                if (mRetryDrawable != null){
                    retryTv.setBackground(mRetryDrawable);
                }
                if (!TextUtils.isEmpty(mRetryStr)){
                    retryTv.setText(mRetryStr);
                }
            }
        }
    }

    private void initClickListener(){
        mEmptyView.setOnClickListener(this);
        mErrorView.setOnClickListener(this);
        mNetErrorView.setOnClickListener(this);
    }

    private void setCurrentView(){
        switch (mCurrentState){
            case STATE_LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mNetErrorView.setVisibility(GONE);
                showPageContentView(false);
                break;
            case STATE_EMPTY:
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mNetErrorView.setVisibility(GONE);
                showPageContentView(false);
                break;
            case STATE_ERROR:
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(VISIBLE);
                mNetErrorView.setVisibility(GONE);
                showPageContentView(false);
                break;
            case STATE_NET_ERROR:
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mNetErrorView.setVisibility(VISIBLE);
                showPageContentView(false);
                break;
            case STATE_NORMAL:
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mNetErrorView.setVisibility(GONE);
                showPageContentView(true);
                break;
        }
    }

    private void showPageContentView(boolean show){
        for (int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);
            if (childView != mLoadingView && childView != mEmptyView && childView != mErrorView &&
                childView != mNetErrorView){
                childView.setVisibility(show ? VISIBLE:GONE);
            }
        }
    }

    /**
     * 显示加载View
     */
    public void showLoading(){
        showLoading(null);
    }

    /**
     * 显示加载View
     * @param tip 提示文字
     */
    public void showLoading(String tip){
        if (mCurrentState == STATE_LOADING || isCustomLoading){
            return;
        }

        mCurrentState = STATE_LOADING;
        setCurrentView();
        if (!isCustomLoading){
            if (!TextUtils.isEmpty(tip)){
                mLoadingTipTv.setText(tip);
            }else {
                mLoadingTipTv.setText(TextUtils.isEmpty(mLoadingTipStr)
                        ?getResources().getString(R.string.state_loading):mLoadingTipStr);
            }
        }
    }

    /**
     * 显示空View
     */
    public void showEmpty(){
        showEmpty(null);
    }

    /**
     * 显示空View
     * @param tip 提示文字
     */
    public void showEmpty(String tip){
        if (mCurrentState == STATE_EMPTY || isCustomEmpty){
            return;
        }

        mCurrentState = STATE_EMPTY;
        setCurrentView();
        if (!isCustomEmpty){
            if (!TextUtils.isEmpty(tip)){
                mEmptyTipTv.setText(tip);
            }else {
                mEmptyTipTv.setText(TextUtils.isEmpty(mEmptyTipStr)
                        ?getResources().getString(R.string.state_empty):mEmptyTipStr);
            }
        }
    }

    public void showError(){
        showError(null);
    }

    /**
     * 显示错误View
     * @param tip 提示文字
     */
    public void showError(String tip){
        if (mCurrentState == STATE_ERROR){
            return;
        }

        mCurrentState = STATE_ERROR;
        setCurrentView();
        if (!isCustomError){
            if (!TextUtils.isEmpty(tip)){
                mErrorTipTv.setText(tip);
            }else {
                mErrorTipTv.setText(TextUtils.isEmpty(mErrorTipStr)
                        ?getResources().getString(R.string.state_error):mErrorTipStr);
            }
        }
    }

    /**
     * 显示网络错误View
     */
    public void showNetError(){
        showNetError(null);
    }

    /**
     * 显示网络错误View
     * @param tip 提示文字
     */
    public void showNetError(String tip){
        if (mCurrentState == STATE_NET_ERROR || isCustomNetError){
            return;
        }

        mCurrentState = STATE_NET_ERROR;
        setCurrentView();
        if (!isCustomNetError){
            if (!TextUtils.isEmpty(tip)){
                mNetErrorTipTv.setText(tip);
            }else {
                mNetErrorTipTv.setText(TextUtils.isEmpty(mNetErrorTipStr)
                        ?getResources().getString(R.string.state_network_error):mNetErrorTipStr);
            }
        }
    }

    /**
     * 显示内容View
     */
    public void showPageContent(){
        if (mCurrentState == STATE_NORMAL){
            return;
        }

        mCurrentState = STATE_NORMAL;
        setCurrentView();
    }

    @Override
    public void onClick(View v) {
        if (mOnReloadListener != null){
            mOnReloadListener.onStateReload();
        }
        switch (mCurrentState){
            case STATE_EMPTY:
                if (mOnStateListener != null){
                    mOnStateListener.onStateEmpty();
                }
                break;
            case STATE_ERROR:
                if (mOnStateListener != null){
                    mOnStateListener.onStateError();
                }
                break;
            case STATE_NET_ERROR:
                if (mOnStateListener != null){
                    mOnStateListener.onStateNetError();
                }
                break;
        }
    }

    /**
     * 设置点击重试的点击事件
     * @param listener
     */
    public void setOnReloadListener(OnReloadListener listener){
        mOnReloadListener = listener;
    }

    public interface OnReloadListener{
        void onStateReload();
    }

    /**
     * 设置点击重试的点击事件
     * 空/加载失败/网络错误的点击事件可分别处理
     * @param listener
     */
    public void setOnStateListener(OnStateListener listener){
        mOnStateListener = listener;
    }

    public interface OnStateListener{
        void onStateEmpty();
        void onStateError();
        void onStateNetError();
    }

    /**
     * 设置错误提示图片
     * @param drawable
     */
    public void setErrorImage(Drawable drawable){
        if (drawable != null && !isCustomError){
            mErrorImg.setImageDrawable(drawable);
        }
    }

    /**
     * 设置网络错误图片
     * @param drawable
     */
    public void setNetErrorImage(Drawable drawable){
        if (drawable != null && !isCustomNetError){
            mNetErrorImg.setImageDrawable(drawable);
        }
    }

    /**
     * 设置空页面提示图片
     * @param drawable
     */
    public void setEmptyImage(Drawable drawable){
        if (drawable != null && !isCustomEmpty){
            mEmptyImg.setImageDrawable(drawable);
        }
    }

    /**
     * 获取加载布局的实例
     * @return
     */
    public View getLoadingView(){
        return mLoadingView;
    }

    /**
     * 获取空布局的实例
     * @return
     */
    public View getEmptyView(){
        return mEmptyView;
    }

    /**
     * 获取错误布局的实例
     * @return
     */
    public View getErrorView(){
        return mErrorView;
    }

    /**
     * 获取网络错误布局的实例
     * @return
     */
    public View getNetErrorView(){
        return mNetErrorView;
    }
}
