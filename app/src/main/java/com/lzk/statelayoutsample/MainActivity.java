package com.lzk.statelayoutsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lzk.statelayout.view.StateLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StateLayout.OnReloadListener, StateLayout.OnStateListener {

    private Button mLoadingBtn,mEmptyBtn,mErrorBtn,mNetErrorBtn,mPageContetnBtn,mSetErrorImgBtn,mSetCustomErrorlayoutBtn;
    private StateLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingBtn = findViewById(R.id.main_loading_btn);
        mEmptyBtn = findViewById(R.id.main_empty_btn);
        mErrorBtn = findViewById(R.id.main_error_btn);
        mNetErrorBtn = findViewById(R.id.main_net_error_btn);
        mPageContetnBtn = findViewById(R.id.main_page_content_btn);
        mSetErrorImgBtn = findViewById(R.id.main_set_error_img_btn);
        mSetCustomErrorlayoutBtn = findViewById(R.id.main_set_custom_error_layout_btn);

        mStateLayout = findViewById(R.id.main_state_layout);
        //设置监听回调
        //mStateLayout.setOnReloadListener(this);
        mStateLayout.setOnStateListener(this);

        mLoadingBtn.setOnClickListener(this);
        mEmptyBtn.setOnClickListener(this);
        mErrorBtn.setOnClickListener(this);
        mNetErrorBtn.setOnClickListener(this);
        mPageContetnBtn.setOnClickListener(this);
        mSetErrorImgBtn.setOnClickListener(this);
        mSetCustomErrorlayoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_loading_btn:
                mStateLayout.showLoading();
                break;
            case R.id.main_empty_btn:
                mStateLayout.showEmpty();
                break;
            case R.id.main_error_btn:
                mStateLayout.showError();
                break;
            case R.id.main_net_error_btn:
                mStateLayout.showNetError("网络开小差了");
                break;
            case R.id.main_page_content_btn:
                mStateLayout.showPageContent();
                break;
            case R.id.main_set_error_img_btn:
                mStateLayout.showError();
                mStateLayout.setErrorImage(ContextCompat.getDrawable(this,R.drawable.ic_default_error));
//                mStateLayout.setEmptyImage(ContextCompat.getDrawable(this,R.drawable.ic_default_error));
//                mStateLayout.setNetErrorImage(ContextCompat.getDrawable(this,R.drawable.ic_default_error));
                break;
            case R.id.main_set_custom_error_layout_btn:
                Intent intent = new Intent(this,CustomStateLayoutActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStateReload() {
        Toast.makeText(this,"点击重试回调接口",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStateEmpty() {
        Toast.makeText(this,"点击空回调接口",Toast.LENGTH_SHORT).show();
        mStateLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateLayout.showEmpty();
            }
        },2000);
    }

    @Override
    public void onStateError() {
        Toast.makeText(this,"点击错误回调接口",Toast.LENGTH_SHORT).show();
        mStateLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateLayout.showError();
            }
        },2000);
    }

    @Override
    public void onStateNetError() {
        Toast.makeText(this,"点击网络错误回调接口",Toast.LENGTH_SHORT).show();
        mStateLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateLayout.showNetError();
            }
        },2000);
    }
}
