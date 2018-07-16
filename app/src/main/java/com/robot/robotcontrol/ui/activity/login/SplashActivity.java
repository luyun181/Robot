package com.robot.robotcontrol.ui.activity.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.SPUtils;
import com.robot.robotcontrol.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private boolean is_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        is_login = SPUtils.getInstance().getBoolean("is_login");
        new CountDownTimer(
                3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (is_login) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            }
        }.start();
    }
}
