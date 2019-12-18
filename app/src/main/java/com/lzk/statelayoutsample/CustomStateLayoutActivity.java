package com.lzk.statelayoutsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lzk.statelayout.view.StateLayout;

/**
 * 设置自定义状态布局文件（以自定义错误布局文件为例）
 *  //自定义状态布局文件，仍然会回调OnStateListener，OnReloadListener接口
 *  //在布局文件中的StateLayout(app:state_error_layout属性)设置自定义的错误布局文件
 */
public class CustomStateLayoutActivity extends AppCompatActivity implements StateLayout.OnReloadListener{

    private StateLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_state_layout);
        mStateLayout = findViewById(R.id.custom_state_layout);
        mStateLayout.setOnReloadListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateLayout.showError();
            }
        },2000);

        //获取自定义错误布局的实例
        View mCustomErrorView = mStateLayout.getErrorView();
        if (mCustomErrorView != null){
            //获取子View
            Button button = mCustomErrorView.findViewById(R.id.custom_error_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CustomStateLayoutActivity.this,"点击了自定义错误布局中的按钮",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onStateReload() {
        Toast.makeText(this,"点击了自定义错误布局,我是接口回调",Toast.LENGTH_SHORT).show();
    }
}
