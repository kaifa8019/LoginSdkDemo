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
import com.admobile.onekeylogin.support.callback.OnOneKeyLoginCallback;
import com.admobile.onekeylogin.support.callback.SDKInitResultCallback;
import com.admobile.onekeylogin.support.callback.XmlViewDelegate;
import com.admobile.onekeylogin.support.ui.AuthCustomViewConfig;
import com.admobile.onekeylogin.support.ui.AuthCustomXmlConfig;
import com.admobile.onekeylogin.support.ui.AuthPageUiConfig;

/**
 * 一键登录
 *
 * @author parting_soul
 * @date 2019-09-04
 */
public class OneKeyLoginActivity extends AppCompatActivity {
    private final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    private YuYanOneKeyLogin mOneKeyLogin;
    private boolean isLoginInitSuccess;
    private boolean isLogin = false;
    public static final String TAG = "MainActivity";

    private TextView mTvToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_onekey_login);
        mTvToken = findViewById(R.id.tv_token);

        //可选权限 建议开发者申请本权限，本权限只用于运营商在双卡情况下，提高认证取号的成功率，若对此权限敏感，也可以不申请此权限，功能一切正常。
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0x11);

        //注意在未登录情况下才去初始化一键登录SDK，避免频繁预取号
        if (!isLogin) {
            YuYanOneKeyLoginSDK.init(this, "申请的应用id", new SDKInitResultCallback() {
                @Override
                public void onSuccess(YuYanOneKeyLogin oneKeyLogin) {
                    //初始化成功后才能去调用一键登录
                    isLoginInitSuccess = true;
                    mOneKeyLogin = oneKeyLogin;
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

    /**
     * 全屏模式授权页配置
     */
    private void initLoginConfig() {
        //移除之前的配置信息
        mOneKeyLogin.removeAuthRegisterViewConfig();
        mOneKeyLogin.removeAuthRegisterXmlConfig();

        mOneKeyLogin.setDebugMode(BuildConfig.DEBUG);
        //宽高为0,则为全屏模式的授权页
        mOneKeyLogin.setAuthUIConfig(new AuthPageUiConfig()
                .setDialogWidth(0)
                .setDialogHeight(0)
        );

//        mOneKeyLogin.setAuthUIConfig(new AuthPageUiConfig()
//                // 设置授权页背景图 drawable资源的目录，不需要加后缀
//                .setPageBackgroundPath("white_bg")
//                // 授权页进场时动画
//                .setAuthPageActIn("in_activity", "out_activity")
//                // 授权页出场动画
//                .setAuthPageActOut("in_activity", "out_activity")
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
//                .setNavReturnImgPath("back_white")
//                // 设置协议页状态栏颜色（系统5.0以上可设置） 不设置则与授权页设置一致
//                .setWebViewStatusBarColor(Color.RED)
//                // 设置协议页顶部导航栏背景色，不设置则与授权页一致
//                .setWebNavColor(Color.GREEN)
//                // 设置协议页顶部导航栏文字大小，不设置则与授权页一致
//                .setWebNavTextSize(18)
//                // 设置标题栏是否隐藏
//                .setNavHidden(false)
//                // 设置返回按钮是否隐藏
//                .setNavReturnHidden(false)
//                //设置状态栏颜色(需Android 5.0以上系统版本)
//                .setStatusBarColor(Color.GREEN)
//                // 设置状态栏文字颜色(系统版本6.0以上可设置黑白色) true为黑色
//                .setLightColor(false)
//                // 设置状态栏是否隐藏
//                .setStatusBarHidden(false)
//                //设置状态栏UI属性,View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN,View.SYSTEM_UI_FLAG_LOW_PROFILE
//                .setStatusBarUIFlag(-1)
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
//                // slogan 隐藏
//                .setSloganHidden(false)
//                /*--------logo-------*/
//                //logo图片(drawable)
//                .setLogoImgPath("logo")
//                //logo图片显隐藏
//                .setLogoHidden(false)
//                // logo 图片宽度(dp)
//                .setLogoWidth(60)
//                // logo图片高度(dp)
//                .setLogoHeight(60)
//                //logo距离顶部导航栏的距离(dp)
//                .setLogoOffsetY(100)
//                //logo距离底部距离(dp)
//                .setLogoOffsetY_B(500)
//
//                /*--------掩码栏-------*/
//                //掩码字体颜色
//                .setNumberColor(Color.BLUE)
//                //掩码字体大小(sp)
//                .setNumberSize(35)
//                //掩码距离顶部导航栏的位移(dp)
//                .setNumFieldOffsetY(220)
//                //掩码距离底部的位移(dp)
//                .setNumFieldOffsetY_B(100)
//                // 设置号码栏相对于默认位置的X轴位置，单位dp
//                .setNumberFieldOffsetX(10)
//                // 设置手机号掩码的布局对齐方式 只支持Gravity.LEFT,Gravity.RIGHT,Gravity.CENTER_HORIZONTAL
//                .setNumberLayoutGravity(Gravity.CENTER_HORIZONTAL)
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
//                // 设置登录按钮宽度，单位dp
//                .setLogBtnWidth(300)
//                // 设置登录按钮高低,单位dp
//                .setLogBtnHeight(28)
//                /*--------切换到其他方式-------*/
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
//                .setAppPrivacyOne("《第一条隐私协议》", "https://www.aliyun.com/ss/?k=%E4%B8%80%E9%94%AE%E7%99%BB%E5%BD%95SDK")
//                //第二条隐私协议
//                .setAppPrivacyTwo("《第二条隐私协议》", "https://github.com/sucese/android-open-framework-analysis")
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
//                .setUncheckedImgPath("ic_launcher")
//                // 设置运营商协议前缀符号，只能设置一个字符，且只能设置<>（）《》 『』 [] ()中的一个
//                .setVendorPrivacyPrefix("《")
//                // 设置运营商协议后缀符号，只能设置一个字符，且只能设置<>（）《》 『』 [] ()中的一个
//                .setVendorPrivacySuffix("》")
//                .setPrivacyBefore("自定义开头")
//                .setPrivacyEnd("自定义结尾")
//        );


        //通过代码的方式添加自定义控件
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
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                oneKeyLogin(false);
                break;
            case R.id.bt_login_dialog:
                oneKeyLogin(true);
                break;
            default:
                break;
        }
    }


    /**
     * 初始化弹框模式的配置
     */
    private void initLoginDialogConfig() {
        //移除之前的配置信息
        mOneKeyLogin.removeAuthRegisterViewConfig();
        mOneKeyLogin.removeAuthRegisterXmlConfig();

        int screenWidth = CommonUtils.getScreenWidthIndp(this);
        int screenHeight = CommonUtils.getScreenHeightIndp(this);

        mOneKeyLogin.setAuthUIConfig(new AuthPageUiConfig()
                // 设置弹框模式授权页宽度，单位为dp,大于0则为弹框模式
                .setDialogWidth((int) (screenWidth * 0.8))
                //  设置弹框模式授权页高度，单位为dp,大于0则为弹框模式
                .setDialogHeight((int) (screenHeight * 0.65))
                // 设置弹框授权页X轴偏移，单位dp
                .setDialogOffsetX(0)
                // 设置弹框授权页Y轴偏移，单位dp
                .setDialogOffsetY(0)
                // 设置弹框授权页底部显示
                .setDialogBottom(false)
                //隐藏导航栏
                .setNavHidden(true)
                .setLogoImgPath("logo")
        );

        //通过xml方式添加自定义布局
        mOneKeyLogin.addAuthRegisterXmlConfig(
                new AuthCustomXmlConfig(R.layout.layout_title, new XmlViewDelegate() {
                    @Override
                    public void onViewCreate(View view) {
                        view.findViewById(R.id.btn_close)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mOneKeyLogin.finishAuthPage();
                                    }
                                });
                    }
                }));

    }

    /**
     * 一键登录
     */
    private void oneKeyLogin(boolean isDialogMode) {
        if (!isLoginInitSuccess) {
            //使用其他登录方式
            return;
        }

        if (isDialogMode) {
            initLoginDialogConfig();
        } else {
            initLoginConfig();
        }

        mOneKeyLogin.getLoginToken(this, 5000, new OnOneKeyLoginCallback() {
            @Override
            public void onShowAuthPageSuccess() {
                LogUtils.d(TAG, "onShowAuthPageSuccess");
            }

            @Override
            public void onTokenSuccess(String token) {
                LogUtils.d(TAG, "onTokenSuccess " + token);
                mTvToken.setText(token);
                show("token获取成功");

                //token获取成功后需要开发者手动调用finishAuthPage方法关闭授权页
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //该方法需要在主线程中调用
                        mOneKeyLogin.finishAuthPage();
                    }
                });
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
        //及时释放资源
        if (mOneKeyLogin != null) {
            mOneKeyLogin.onDestroy();
        }
    }
}
