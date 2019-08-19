* [1. SDK接入流程](#1-sdk接入流程)
  * [1.1 权限申请](#11-添加SDK至项目)
  * [1.2 权限申请](#12-权限申请)
* [2. SDK使用](#2-sdk使用)
  * [2.1 SDK初始化](#21-sdk初始化)
  * [2.2 一键登录](#22-一键登录)
  * [2.3 自定义授权界面的UI属性](#23-自定义授权界面的ui属性)
  * [2.4 添加自定义登录方式](#24-添加自定义登录方式)
  * [2.5 api](#25-api)
  * [2.6 混淆keep规则](#26-混淆keep规则)
  * [2.7 常见错误码](#27-常见错误码)



体验Demo下载

![一键登录](https://www.pgyer.com/app/qrcode/Edhb)

<font color="#ff0000">注意事项：</font>

- <font color="#ff0000">使用一键登录需要用户打开蜂窝数据网络</font>
- <font color="#ff0000">取号认证过程需要消耗少量用户数据流量</font>



### 1. SDK接入流程

####1.1 添加SDK至项目

接入环境： AndroidStudio

将Demo中libs目录下的两个aar文件拷贝至工程的libs目录下，然后在app的build.gradle进行以下配置

```groovy
android {
    ...
    //加载aar包需要
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
 	....

    // json解析库(必须)
    implementation 'com.alibaba:fastjson:1.2.32'
    // 艾狄墨搏公共组件库(使用艾狄墨搏广告SDK时可不依赖，其余情况必须)
    implementation(name: 'common-release', ext: 'aar')
    // 一键登录SDK
    implementation(name: 'onekeylogin-release', ext: 'aar')
}

```

<font color="#ff0000"> 注意事项： 在同时接入一键登录和[艾狄墨搏广告SDK](http://101.37.118.54/dokuwiki/doku.php?id=admobgensdk)时，若发生依赖冲突，可移除common-release.aar这个库依赖(广告SDK必须升级至最新版)。</font>

#### 1.2 权限申请

若出现权限相关问题，请检查APP的权限是否申请正常。正常引用aar，权限会自动merge。若权限没有merge，需要添加如下权限。

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
```

<font color="#ff0000"> 注：确保以上权限正常申请，否则无法使用一键登录</font>

### 2. SDK使用

#### 2.1 SDK初始化

SDK只需在未登录前初始化，所以调用前需判断用户是否登录，避免频繁预取号

```java
YuYanOneKeyLoginSDK.init(this, "申请的appid", new SDKInitResultCallback() {
    @Override
    public void onSuccess(YuYanOneKeyLogin oneKeyLogin) {
        //初始化成功后才能去调用一键登录
    }

    @Override
    public void onFailed(String error) {
    }
});
```

<font color="#ff0000">注： 申请的appid与包名和应用签名绑定，运行时需用应用签名对应用进行签名，否则无法调起一键登录</font>

#### 2.2 一键登录

在初始化成功后，便可调用一键登录方法。第一个参数为获取凭证的超时时间，单位ms。

```java
mOneKeyLogin.getLoginToken(5000, new OnTokenResultCallback() {
    @Override
    public void onShowAuthPageSuccess() {
        // 显示授权界面成功
    }

    @Override
    public void onTokenSuccess(String token) {
        // 获取token成功，需将token上报至服务端
    }

    @Override
    public void onTokenFailed(String error) {
        // 获取token失败
    }
});
```

#### 2.3 自定义授权界面的UI属性

```java
mOneKeyLogin.setAuthUIConfig(new AuthPageUiConfig()
                             /*--------导航栏-------*/
                             //导航栏主题色
                             .setNavColor(Color.BLUE)
                             //导航栏文字标题
                             .setNavText("导航栏标题")
                             //导航栏文字颜色
                             .setNavTextColor(Color.RED)
                             //导航栏字体大小(sp)
                             .setNavTextSize(20)
                             //导航栏返回键图片(drawable目录下)
                             .setNavReturnImgPath("ic_launcher")
                             /*--------slogan-------*/
                             //slogan文字内容
                             .setSloganText("标语内容")
                             //slogan文字颜色
                             .setSloganTextColor(Color.GREEN)
                             //slogan文字大小(sp)
                             .setSloganTextSize(20)
                             //slogan相对于导航栏的位移(dp)
                             .setSloganOffsetY(200)
                             //slogan相对于底部的位移(dp)
                             .setSloganOffsetY_B(240)
                             /*--------logo-------*/
                             //logo图片(drawable)
                             .setLogoImgPath("ic_launcher")
                             //logo图片显隐藏
                             .setLogoHidden(false)
                             // logo 图片宽度(dp)
                             .setLogoWidth(50)
                             // logo图片高度(dp)
                             .setLogoHeight(50)
                             //logo距离顶部导航栏的距离(dp)
                             .setLogoOffsetY(100)
                             //logo距离底部距离(dp)
                             .setLogoOffsetY_B(500)
                             /*--------掩码栏-------*/
                             //掩码字体颜色
                             .setNumberColor(Color.BLUE)
                             //掩码字体大小(sp)
                             .setNumberSize(35)
                             //掩码距离顶部导航栏的位移(dp)
                             .setNumFieldOffsetY(220)
                             //掩码距离底部的位移(dp)
                             .setNumFieldOffsetY_B(100)
                             /*--------登录按钮-------*/
                             .setLogBtnText("登录按钮文字")
                             //登录按钮字体颜色
                             .setLogBtnTextColor(Color.WHITE)
                             //登录按钮背景图片路径
                             .setLogBtnBackgroundPath("shape_bg")
                             //登录按钮距离顶部位移
                             .setLogBtnOffsetY(300)
                             //登录按钮距离底部位移
                             .setLogBtnOffsetY_B(400)
                             /*--------切换到其他方式-------*/
                             //设置按钮点击事件
                             .setSwitchClicker(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                 }
                             })
                             //设置按钮是否可见
                             .setSwitchAccHidden(false)
                             //设置文字内容
                             .setSwitchAccText("切换到其他登录方式 内容")
                             //设置文字颜色
                             .setSwitchAccTextColor(Color.BLUE)
                             //设置按钮文字大小
                             .setSwitchAccTextSize(22)
                             //设置按钮距离顶部位移
                             .setSwitchOffsetY(400)
                             //设置按钮距离底部位移
                             .setSwitchOffsetY_B(300)
                             /*--------协议栏-------*/
                             //第一条隐私协议
                             .setAppPrivacyOne("第一条隐私协议", "https://www.aliyun.com/ss/?k=%E4%B8%80%E9%94%AE%E7%99%BB%E5%BD%95SDK")
                             //第二条隐私协议
                             .setAppPrivacyTwo("第二条隐私协议", "https://github.com/sucese/android-open-framework-analysis")
                             //基础文字颜色，隐私协议的颜色
                             .setAppPrivacyColor(Color.BLUE, Color.RED)
                             //设置隐私条款相对于顶部的位移
                             .setPrivacyOffsetY(500)
                             //设置隐私条款相对于底部的位移
                             .setPrivacyOffsetY_B(200)
                             //隐私条款勾选状态
                             .setPrivacyState(true)
                             //复选框隐藏状态
                             .setCheckboxHidden(false)
                             //复选框选中状态的图片路径
                .setCheckedImgPath("shape_bg")
                //复选框未选中状态的图片路径
                .setUncheckedImgPath("ic_launcher"));
```

#### 2.4 添加自定义登录方式

每次调用**getLoginToken**请求之前，都需重新设置**AuthCustomViewConfig**，因为在授权页关闭时都会清空注入进去的 **AuthCustomViewConfig** ，具体实现请见 **demo** 工 程 

```java
mOneKeyLogin.addAuthCustomViewConfig("loginTypeView", /* 自定义控件名称*/
                  new AuthCustomViewConfig(addView, /* 开发者传入的自定义控件, SDK目前只支持RelativeLayout,需设置自定义控件的LayoutParams */                                         AuthCustomViewConfig.RootViewId.ROOT_VIEW_ID_BODY)); /* 设置控件位置 */
mOneKeyLogin.getLoginToken(5000, new OnTokenResultCallback() {
    ...   
}
```

目前SDK授权页允许在两个位置插入开发者自定义的控件

```java
public static final int ROOT_VIEW_ID_BODY = 0; // 授权页空白处
public static final int ROOT_VIEW_ID_TITLE_BAR = 1; // 标题栏
```

#### 2.5 api

YuYanOneKeyLogin : SDK初始化

```java
public class YuYanOneKeyLogin {
   
   /**
     * SDK初始化接口，预取号有效期为60 min
     *
     * @param context
     * @param appId    分配的应用id
     * @param callback
     */
    public static void init(final Context context, String appId, SDKInitResultCallback callback);
    
     /**
     * SDK初始化接口
     *
     * @param context
     * @param appId       分配的应用id
     * @param overdueTime 预取号预有效期 单位min
     * @param callback
     */
    public static void init(final Context context, String appId, final int overdueTime, SDKInitResultCallback callback);
    
}
```

YuYanOneKeyLogin 一键登录

```java
public class YuYanOneKeyLogin {
    
    /**
     * 唤起一键登录，用户授权后返回一键登录的token
     *
     * @param timeout  超时时间
     * @param callback
     */
    void getLoginToken(int timeout, OnTokenResultCallback callback);


    /**
     * 设置SDK是否debug模式运行
     *
     * @param isDebug true则输出关键步骤运行日志
     */
    void setDebugMode(boolean isDebug);


    /**
     * 关闭授权页
     */
    void finishAuthPage();
    
     /**
     * 修改授权页面主题，开发者可以通过 此方法修改授权页面主题
     * 需在 getLoginToken接口之前调用
     * 注意：设置图片背景函数只需要填入图片的名称，无需后缀与路径
     * 并且图片需要放置drawable目录下
     *
     * @param config
     */
    void setAuthUIConfig(AuthPageUiConfig config);


    /**
     * 添加自定义控件区
     * 每次调用getLoginToken请求之前，都需重新设置AuthCustomViewConfig
     * @param name   开发者自定义的控件名称
     * @param config 自定义控件配置
     */
    void addAuthCustomViewConfig(String name, AuthCustomViewConfig config);
    
}
```

#### 2.6 混淆keep规则

```java
-keep class cn.com.chinatelecom.gateway.lib.** {*;}
-keep class com.unicom.xiaowo.login.** {*;}
-keep class com.cmic.sso.sdk.** {*;}
-keep class com.mobile.auth.gatewayauth.** {*;}
-keep class android.support.v4.** { *;}
-keep class org.json.**{*;}
-keep class com.alibaba.fastjson.** {*;}
```

#### 2.7 常见错误码

| 错误码 |          描述          |                   建议                   |
| :----: | :--------------------: | :--------------------------------------: |
|  -201  |   蜂窝数据网络不可用   | 提示用户打开数据网络或者切换其他登录方式 |
|  -202  | 用户获取IMSI权限未允许 |   提示用户开启权限或者切换其他登录方式   |
|  -203  |     token获取失败      |             切换其他登录方式             |
|  -204  |    授权界面唤起失败    |          建议切换到其他登录方式          |
|  -205  |    登录按钮文案非法    |  登录按钮文案必须包含”登录”、”注册”字眼  |

