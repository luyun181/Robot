package com.robot.robotcontrol.ui.activity.video;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ezvizuikit.open.EZUIError;
import com.ezvizuikit.open.EZUIKit;
import com.ezvizuikit.open.EZUIPlayer;
import com.gyf.barlibrary.ImmersionBar;
import com.robot.robotcontrol.R;
import com.robot.robotcontrol.bean.CommonBean;
import com.robot.robotcontrol.bean.TokenBean;
import com.robot.robotcontrol.bean.UrlBean;
import com.robot.robotcontrol.event.NetSuccessEvent;
import com.robot.robotcontrol.interf.ApiService;
import com.robot.robotcontrol.utils.RetrofitUtils;
import com.robot.robotcontrol.widget.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_second_title)
    TextView tvSecondTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    EZUIPlayer webView;
    @BindView(R.id.btn_stop)
    ImageView btnStop;
    @BindView(R.id.btn_top)
    ImageView btnTop;
    @BindView(R.id.btn_right)
    ImageView btnRight;
    @BindView(R.id.btn_bottom)
    ImageView btnBottom;
    @BindView(R.id.btn_left)
    ImageView btnLeft;
    @BindView(R.id.tv_slow)
    TextView tvSlow;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_fast)
    TextView tvFast;
    @BindView(R.id.tv_zoom_in)
    ImageView tvZoomIn;
    @BindView(R.id.tv_zoom_out)
    ImageView tvZoomOut;
    private int type = 0;
    private int speed = 0;
    private String token;
    private String video_id;
    private ApiService apiService;
    private String ezopen;
    private String ezopeNhd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        toolbarTitle.setText("视频");
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .navigationBarWithKitkatEnable(false)
                .statusBarDarkFont(false, 0.2f)
                .init();
        apiService = RetrofitUtils.getInstance(this).create(ApiService.class);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            token = extras.getString("token");
            video_id = extras.getString("video_id");
            ezopen = extras.getString("ezopen");
            ezopeNhd = extras.getString("ezopeNhd");
        }


        EZUIKit.initWithAppKey(getApplication(), "089c632132ea44b4b4bf82db781b9f8d");
        EZUIKit.setAccessToken(token);
        webView.setLoadingView(initProgressBar());
        //设置播放回调callback
        webView.setCallBack(new EZUIPlayer.EZUIPlayerCallBack() {
            @Override
            public void onPlaySuccess() {

            }

            @Override
            public void onPlayFail(EZUIError ezuiError) {

            }

            @Override
            public void onVideoSizeChange(int i, int i1) {

            }

            @Override
            public void onPrepared() {
//                开始播放
//                webView.startPlay();
            }

            @Override
            public void onPlayTime(Calendar calendar) {

            }

            @Override
            public void onPlayFinish() {

            }
        });
        //设置播放参数
        webView.setUrl(ezopen);
        webView.startPlay();
    }




    @Override
    protected void onStop() {
        super.onStop();
        webView.stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        webView.releasePlayer();
    }

    private View initProgressBar() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(lp);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);//addRule参数对应RelativeLayout XML布局的属性
        ProgressBar mProgressBar = new ProgressBar(this);
        mProgressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
        relativeLayout.addView(mProgressBar, rlp);
        return relativeLayout;
    }



    @OnClick({R.id.btn_stop, R.id.btn_top, R.id.btn_right, R.id.btn_bottom,
            R.id.btn_left, R.id.tv_slow, R.id.tv_normal, R.id.tv_fast, R.id.tv_zoom_in, R.id.tv_zoom_out, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_stop:
                stop(video_id, type);
                break;
            case R.id.btn_top:
                type = 0;
                control(video_id, type, speed);
                break;
            case R.id.btn_right:
                type = 3;
                control(video_id, type, speed);
                break;
            case R.id.btn_bottom:
                type = 1;
                control(video_id, type, speed);
                break;
            case R.id.btn_left:
                type = 2;
                control(video_id, type, speed);
                break;
            case R.id.tv_slow:
                speed = 0;
                ToastUtils.showLong("慢速");
                break;
            case R.id.tv_normal:
                speed = 1;
                ToastUtils.showLong("正常");
                break;
            case R.id.tv_fast:
                speed = 2;
                ToastUtils.showLong("快速");
                break;
            case R.id.tv_zoom_in:
                type = 8;
                control(video_id, type, speed);
                break;
            case R.id.tv_zoom_out:
                type = 9;
                control(video_id, type, speed);
                break;
            default:
                break;
        }
    }

    private void control(String deviceSerial, int direction, int speed) {
        final Dialog dialog = CustomProgressDialog.createLoadingDialog(this, "");
        ApiService apiService = RetrofitUtils.getInstance(this).create(ApiService.class);
        Observable<CommonBean> videoControl = apiService.getVideoControl(deviceSerial, direction, speed);
        videoControl.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dialog.show();
                    }

                    @Override
                    public void onNext(CommonBean commonBean) {
                        int code = commonBean.getCode();
                        if (code == 200) {
                            ToastUtils.showLong("操作成功");
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        ToastUtils.showLong(R.string.net_error);
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });


    }

    private void stop(String deviceSerial, int direction) {
        final Dialog dialog = CustomProgressDialog.createLoadingDialog(this, "");
        ApiService apiService = RetrofitUtils.getInstance(this).create(ApiService.class);
        Observable<CommonBean> videoControl = apiService.getStopControl(deviceSerial, direction);
        videoControl.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dialog.show();
                    }

                    @Override
                    public void onNext(CommonBean commonBean) {
                        int code = commonBean.getCode();
                        if (code == 200) {
                            ToastUtils.showLong("操作成功");
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        ToastUtils.showLong(R.string.net_error);
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });


    }

}
