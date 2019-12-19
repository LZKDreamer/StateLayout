# StateLayout
[![](https://jitpack.io/v/LZKDreamer/StateLayout.svg)](https://jitpack.io/#LZKDreamer/StateLayout)   
状态View,包含加载页面，空页面，错误页面，网络错误页面。内含状态默认页面，也支持**自定义状态页面**。

# 预览图
![预览图GIF](https://github.com/LZKDreamer/StateLayout/blob/master/screenshot/demo.gif)

# 引入
Step 1. 在项目的build.gradle中添加
```
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
```
Step 2. 在app的build.gradle中添加
```
dependencies {
    implementation 'com.github.LZKDreamer:StateLayout:1.0'
}
```  
***最新版本Latest Version*** [![](https://jitpack.io/v/LZKDreamer/StateLayout.svg)](https://jitpack.io/#LZKDreamer/StateLayout)   

# 使用  
* XML

```
<com.lzk.statelayout.view.StateLayout
        android:id="@+id/main_state_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <!--添加真正要显示的内容，如一个RecyclerView-->
    </com.lzk.statelayout.view.StateLayout>
```
- XML自定义属性

 | 自定义属性 | 说明 | 
 |  ----  | ----  |
 | app:state_error_layout="@layout/layout_custom_error" | 自定义错误布局文件 | 
 | app:state_loading_layout="@layout/layout_custom_error" | 自定义加载布局文件 | 
 | app:state_empty_layout="@layout/layout_custom_error" | 自定义空布局文件 | 
 | app:state_net_error_layout="@layout/layout_custom_error" | 自定义网络错误布局文件 | 
 | app:state_loading_bar_color="@color/colorAccent" | LoadingBar的颜色 | 
 | app:state_loading_tip="@string/state_loading" | 加载提示文字(例如:正在加载) | 
 | app:state_error_tip="@string/state_error" | 错误提示文字(例如:加载失败) | 
 | app:state_empty_tip="@string/state_empty" | 空提示文字(例如:这里什么都没有) | 
 | app:state_net_error_tip="@string/state_empty" | 网络错误提示文字(例如:网络未连接,请检查网络) | 
 | app:state_retry_text="@string/click_screen_and_retry" | 重试按钮提示文字(例如:点击屏幕重试) | 
 | app:state_retry_drawable="@drawable/bg_retry" | 重试按钮的drawable文件,添加圆角什么的 | 
 | app:state_retry_text_color="@color/colorWhite" | 重试文字颜色 | 
 | app:state_retry_text_size="14sp" | 重试文字大小(sp) | 
 | app:state_empty_img="@drawable/ic_default" | 空页面图片 | 
 | app:state_error_img="@drawable/ic_default" | 错误页面图片 | 
 | app:state_net_error_img="@drawable/ic_default" | 网络错误页面图片 | 
 | app:state_tip_text_color="@color/colorPrimary" | 提示文字的颜色 | 
 | app:state_tip_text_size="16sp" | 提示文字的大小(sp) | 
	
* 回调

    - 如果空页面、错误页面、网络错误页面的重试事件是一样的则使用OnReloadListener
```
public interface OnReloadListener{
        void onStateReload();
    }
```

    - 如果空页面、错误页面、网络错误页面的重试事件需要分别处理则使用OnStateListener
```
public interface OnStateListener{
        void onStateEmpty();
        void onStateError();
        void onStateNetError();
    }
```

* java
```
        mStateLayout = findViewById(R.id.main_state_layout);
        //设置监听回调
        //mStateLayout.setOnReloadListener(this);
        mStateLayout.setOnStateListener(this);
        
        //方法
        注意:若showXXX(String tip)不传入提示文字则显示默认的文字
        mStateLayout.showLoading();//显示加载布局
        mStateLayout.showLoading(String tip);
        mStateLayout.showEmpty();//显示空布局
        mStateLayout.showEmpty(String tip);
        mStateLayout.showError();//显示错误布局
        mStateLayout.showError(String tip);
        mStateLayout.showNetError();//显示网络错误布局
        mStateLayout.showNetError(String tip); 
        //显示你的内容布局
        mStateLayout.showPageContent();
        
```
* 其它方法

```
 /**
     * 设置错误提示图片
     * @param drawable
     */
    public void setErrorImage(Drawable drawable);

    /**
     * 设置网络错误图片
     * @param drawable
     */
    public void setNetErrorImage(Drawable drawable)

    /**
     * 设置空页面提示图片
     * @param drawable
     */
    public void setEmptyImage(Drawable drawable)

    /**
     * 获取加载布局的实例
     * @return
     */
    public View getLoadingView()

    /**
     * 获取空布局的实例
     * @return
     */
    public View getEmptyView()

    /**
     * 获取错误布局的实例
     * @return
     */
    public View getErrorView()

    /**
     * 获取网络错误布局的实例
     * @return
     */
    public View getNetErrorView()
```

