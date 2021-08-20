package com.com.myblog.view.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.com.myblog.R;
import com.com.myblog.controller.AuthController;
import com.com.myblog.controller.dto.CMRespDto;
import com.com.myblog.controller.dto.JoinDto;
import com.com.myblog.model.User;
import com.com.myblog.view.InitActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinActivity extends AppCompatActivity implements InitActivity {

    private static final String TAG = "JoinActivity";
    private JoinActivity mContext = JoinActivity.this;

    private TextInputEditText tfUsername, tfPassword, tfEmail;
    private MaterialButton btnJoin;
    private TextView tvLinkLogin;

    private AuthController authController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        initLr();
        initSetting();
    }

    @Override
    public void init() {
        authController = new AuthController();
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        tfEmail = findViewById(R.id.tfEmail);
        btnJoin = findViewById(R.id.btnJoin);
        tvLinkLogin = findViewById(R.id.tvLinkLogin);
    }

    @Override
    public void initLr() {
        btnJoin.setOnClickListener(v -> {
            String username = tfUsername.getText().toString().trim();
            String password = tfPassword.getText().toString().trim();
            String email = tfEmail.getText().toString().trim();
            authController.join(new JoinDto(username, password, email)).enqueue(new Callback<CMRespDto<User>>() {
                @Override
                public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                   CMRespDto<User> cm = response.body();
                    Log.d(TAG, "onResponse: " + cm.getData());
                    Intent intent = new Intent(
                            mContext,
                            LoginActivity.class
                    );
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<CMRespDto<User>> call, Throwable t) {

                }
            });
        });
        tvLinkLogin.setOnClickListener(v->{
            Intent intent = new Intent(
                    mContext,
                    LoginActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    public void initSetting() {

    }
}