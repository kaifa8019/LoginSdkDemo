package com.ecook.loginsdkdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admobile.onekeylogin.core.YuYanOneKeyLogin;
import com.admobile.onekeylogin.support.YuYanOneKeyLoginSDK;
import com.admobile.onekeylogin.support.callback.OnTokenResultCallback;
import com.admobile.onekeylogin.support.callback.SDKInitResultCallback;
import com.admobile.onekeylogin.support.ui.AuthCustomViewConfig;

public class MainActivity extends AppCompatActivity {
    private final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    private YuYanOneKeyLogin mOneKeyLogin;
    private boolean isLoginInitSuccess;
    private boolean isLogin = false;
    public static final String TAG = "MainActivity";

    private TextView mTvToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvToken = findViewById(R.id.tv_token);

        //请求权限
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0x11);


        //注意在未登录情况下才去初始化一键登录SDK，避免频繁预取号
        if (!isLogin) {
            YuYanOneKeyLoginSDK.init(this, "申请的appid", new SDKInitResultCallback() {
                @Override
                public void onSuccess(YuYanOneKeyLogin oneKeyLogin) {
                    //初始化成功后才能去调用一键登录
                    isLoginInitSuccess = true;
                    mOneKeyLogin = oneKeyLogin;
                    initLoginConfig();
                    show("SDK初始化成功");
                }

                @Override
                public void onFailed(String error) {
                    isLoginInitSuccess = false;
                    show("SDK初始化失败 " + error);
                }
            });
        }
    }

    private void initLoginConfig() {
        mOneKeyLogin.setDebugMode(BuildConfig.DEBUG);
//        mOneKeyLogin.setAuthUIConfig(new AuthPageUiConfig()
//                /*--------导航栏-------*/
//                //导航栏主题色
//                .setNavColor(Color.BLUE)
//                //导航栏文字标题
//                .setNavText("导航栏标题")
//                //导航栏文字颜色
//                .setNavTextColor(Color.RED)
//                //导航栏字体大小(sp)
//                .setNavTextSize(20)
//                //导航栏返回键图片(drawable目录下)
//                .setNavReturnImgPath("ic_launcher")
//                /*--------slogan-------*/
//                //slogan文字内容
//                .setSloganText("标语内容")
//                //slogan文字颜色
//                .setSloganTextColor(Color.GREEN)
//                //slogan文字大小(sp)
//                .setSloganTextSize(20)
//                //slogan相对于导航栏的位移(dp)
//                .setSloganOffsetY(200)
//                //slogan相对于底部的位移(dp)
//                .setSloganOffsetY_B(240)
//                /*--------logo-------*/
//                //logo图片(drawable)
//                .setLogoImgPath("ic_launcher")
//                //logo图片显隐藏
//                .setLogoHidden(false)
//                // logo 图片宽度(dp)
//                .setLogoWidth(50)
//                // logo图片高度(dp)
//                .setLogoHeight(50)
//                //logo距离顶部导航栏的距离(dp)
//                .setLogoOffsetY(100)
//                //logo距离底部距离(dp)
//                .setLogoOffsetY_B(500)
//                /*--------掩码栏-------*/
//                //掩码字体颜色
//                .setNumberColor(Color.BLUE)
//                //掩码字体大小(sp)
//                .setNumberSize(35)
//                //掩码距离顶部导航栏的位移(dp)
//                .setNumFieldOffsetY(220)
//                //掩码距离底部的位移(dp)
//                .setNumFieldOffsetY_B(100)
//                /*--------登录按钮-------*/
//                .setLogBtnText("登录按钮文字")
//                //登录按钮字体颜色
//                .setLogBtnTextColor(Color.WHITE)
//                //登录按钮背景图片路径
//                .setLogBtnBackgroundPath("shape_bg")
//                //登录按钮距离顶部位移
//                .setLogBtnOffsetY(300)
//                //登录按钮距离底部位移
//                .setLogBtnOffsetY_B(400)
//                /*--------切换到其他方式-------*/
//                //设置按钮点击事件
//                .setSwitchClicker(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                })
//                //设置按钮是否可见
//                .setSwitchAccHidden(false)
//                //设置文字内容
//                .setSwitchAccText("切换到其他登录方式 内容")
//                //设置文字颜色
//                .setSwitchAccTextColor(Color.BLUE)
//                //设置按钮文字大小
//                .setSwitchAccTextSize(22)
//                //设置按钮距离顶部位移
//                .setSwitchOffsetY(400)
//                //设置按钮距离底部位移
//                .setSwitchOffsetY_B(300)
//                /*--------协议栏-------*/
//                //第一条隐私协议
//                .setAppPrivacyOne("第一条隐私协议", "https://www.aliyun.com/ss/?k=%E4%B8%80%E9%94%AE%E7%99%BB%E5%BD%95SDK")
//                //第二条隐私协议
//                .setAppPrivacyTwo("第二条隐私协议", "https://github.com/sucese/android-open-framework-analysis")
//                //基础文字颜色，隐私协议的颜色
//                .setAppPrivacyColor(Color.BLUE, Color.RED)
//                //设置隐私条款相对于顶部的位移
//                .setPrivacyOffsetY(500)
//                //设置隐私条款相对于底部的位移
//                .setPrivacyOffsetY_B(200)
//                //隐私条款勾选状态
//                .setPrivacyState(true)
//                //复选框隐藏状态
//                .setCheckboxHidden(false)
//                //复选框选中状态的图片路径
//                .setCheckedImgPath("shape_bg")
//                //复选框未选中状态的图片路径
//                .setUncheckedImgPath("ic_launcher"));
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                oneKeyLogin();
                break;
            default:
                break;
        }
    }

    /**
     * 一键登录
     */
    private void oneKeyLogin() {
        if (!isLoginInitSuccess) {
            return;
        }
        View addView = LayoutInflater.from(this).inflate(R.layout.include_layout_login, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 0, 200);
        addView.setLayoutParams(layoutParams);

        TextView tvQQ = addView.findViewById(R.id.tv_qq);
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show("qq登录");
                mOneKeyLogin.finishAuthPage();
            }
        });

        TextView tvWeChat = addView.findViewById(R.id.tv_wechat);
        tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show("微信登录");
                mOneKeyLogin.finishAuthPage();
            }
        });

        mOneKeyLogin.addAuthCustomViewConfig("loginTypeView",
                new AuthCustomViewConfig(addView, AuthCustomViewConfig.RootViewId.ROOT_VIEW_ID_BODY));
        mOneKeyLogin.getLoginToken(5000, new OnTokenResultCallback() {
            @Override
            public void onShowAuthPageSuccess() {
                //注：中国移动在授权页展示时不会回调
                LogUtils.d(TAG, "onShowAuthPageSuccess");
            }

            @Override
            public void onTokenSuccess(String token) {
                LogUtils.d(TAG, "onTokenSuccess " + token);
                mTvToken.setText(token);
                show("token获取成功");
            }

            @Override
            public void onTokenFailed(String error) {
                show(error);
            }

            @Override
            public void onCancel() {
                show("取消登录");
            }
        });
    }

    private void show(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOneKeyLogin != null) {
            mOneKeyLogin.onDestroy();
        }
    }


}
