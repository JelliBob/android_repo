package com.com.myblog.view.user;

import android.os.Bundle;

import com.com.myblog.R;
import com.com.myblog.view.CustomAppBarActivity;
import com.com.myblog.view.InitActivity;

public class UserInfoActivity extends CustomAppBarActivity implements InitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        init();
        initLr();
        initSetting();
    }

    @Override
    public void init() {

    }

    @Override
    public void initLr() {

    }

    @Override
    public void initSetting() {
        onAppBarSettings(true, "UserInfo");
    }
}