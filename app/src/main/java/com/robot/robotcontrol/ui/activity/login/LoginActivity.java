package com.robot.robotcontrol.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.robot.robotcontrol.MainActivity;
import com.robot.robotcontrol.R;
import com.robot.robotcontrol.bean.CommonBean;
import com.robot.robotcontrol.interf.ApiService;
import com.robot.robotcontrol.ui.activity.video.VideoDetailActivity;
import com.robot.robotcontrol.utils.RetrofitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.iv_app)
    ImageView ivApp;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;
    @BindView(R.id.et_pwd)
    TextInputEditText etPwd;
    @BindView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
//        startActivity(new Intent(LoginActivity.this, VideoDetailActivity.class));
        String name = etUsername.getText().toString();
        String pwd = etPwd.getText().toString();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入用户名");
        } else if (StringUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
        } else {
            login(name, pwd);
        }

    }

    private void login(String name, String pwd) {
        ApiService apiService = RetrofitUtils.getInstance(this).create(ApiService.class);
        Observable<CommonBean> longin = apiService.getLongin(name, pwd);
        longin.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonBean commonBean) {
                        int code = commonBean.getCode();
                        String meg = commonBean.getMeg();
                        if (code == 200) {
                            SPUtils.getInstance().put("is_login", true);
                            if (meg != null) {
                                ToastUtils.showShort(meg);
                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        } else {
                            if (meg != null) {
                                ToastUtils.showShort(meg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(R.string.net_error);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
