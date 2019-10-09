package com.ecook.loginsdkdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.admobile.onekeylogin.core.YuYanMobileAuth;
import com.admobile.onekeylogin.support.YuYanOneKeyLoginSDK;
import com.admobile.onekeylogin.support.callback.MobileAuthSDKInitResultCallback;
import com.admobile.onekeylogin.support.callback.OnTokenResultCallback;

/**
 * 手机号验证
 *
 * @author parting_soul
 * @date 2019-09-04
 */
public class MobileAuthActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mBtCheck;
    private YuYanMobileAuth mMobileAuthLogin;
    private TextView mTvMsg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mobile_auth);
        mEditText = findViewById(R.id.mEtInput);
        mBtCheck = findViewById(R.id.bt_check);
        mTvMsg = findViewById(R.id.tvMsg);


        initMobileAuthSDK();
        mBtCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileAuth();
            }
        });
    }

    /**
     * 初始化手机号校验SDK
     */
    private void initMobileAuthSDK() {
        YuYanOneKeyLoginSDK.initMobileAuth(this, "申请的appid", new MobileAuthSDKInitResultCallback() {
            @Override
            public void onSuccess(YuYanMobileAuth mobileAuth) {
                mMobileAuthLogin = mobileAuth;
            }

            @Override
            public void onFailed(String error) {
                mTvMsg.setText(error);
            }
        });
    }

    private void mobileAuth() {
        if (mMobileAuthLogin == null) {
            //初始化异常或者未成功，可以选择其他登录方式
            return;
        }

        String phone = mEditText.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            mTvMsg.setText("手机号码格式异常");
            return;
        }

        mMobileAuthLogin.getAuthToken(phone, 5000, new OnTokenResultCallback() {

            @Override
            public void onTokenSuccess(String token) {
                //上报token至开发者服务器
                mTvMsg.setText(token);
            }

            @Override
            public void onTokenFailed(String error) {
                Log.e("tag", "onTokenFailed: " + error);
                mTvMsg.setText(error);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMobileAuthLogin != null) {
            mMobileAuthLogin.onDestroy();
        }
    }


}
