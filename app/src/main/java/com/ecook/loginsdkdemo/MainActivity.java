package com.ecook.loginsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 注意 若要运行Demo,需要填入申请的appid,需要将build.gradle中的
 * applicationId换成申请的包名，以及使用申请应用的签名运行
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_onekey_login:
                startActivity(new Intent(this, OneKeyLoginActivity.class));
                break;
            case R.id.bt_mobile_auth:
                startActivity(new Intent(this, MobileAuthActivity.class));
                break;
            default:
        }
    }
    
}
