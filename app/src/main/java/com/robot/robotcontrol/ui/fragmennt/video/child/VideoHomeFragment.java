package com.robot.robotcontrol.ui.fragmennt.video.child;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.robot.robotcontrol.R;
import com.robot.robotcontrol.adapter.VIdeoAdapter;
import com.robot.robotcontrol.base.BaseFragment;
import com.robot.robotcontrol.bean.VideoListBean;
import com.robot.robotcontrol.interf.ApiService;
import com.robot.robotcontrol.ui.activity.login.LoginActivity;
import com.robot.robotcontrol.ui.activity.video.VideoDetailActivity;
import com.robot.robotcontrol.utils.RetrofitUtils;
import com.robot.robotcontrol.widget.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class VideoHomeFragment extends BaseFragment {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_second_title)
    TextView tvSecondTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_video)
    RecyclerView recyclerVideo;
    @BindView(R.id.ticket)
    LinearLayout ticket;
    Unbinder unbinder;

    public static VideoHomeFragment newInstance() {

        Bundle args = new Bundle();

        VideoHomeFragment fragment = new VideoHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbarTitle.setText("设备");
        recyclerVideo.setLayoutManager(new LinearLayoutManager(_mActivity));

        getVideoList();
        return view;
    }

    private void getVideoList() {
        final Dialog dialog = CustomProgressDialog.createLoadingDialog(_mActivity,"");
        ApiService apiService = RetrofitUtils.getInstance(_mActivity).create(ApiService.class);
        Observable<VideoListBean> videoList = apiService.getVideoList();
        videoList.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dialog.show();
                    }

                    @Override
                    public void onNext(VideoListBean videoListBean) {
                        String code = videoListBean.getCode();
                        String msg = videoListBean.getMsg();
                        if (code.equals("200")) {
                            final List<VideoListBean.DataBean> data = videoListBean.getData();
                            VIdeoAdapter vIdeoAdapter = new VIdeoAdapter(R.layout.item_video_list, data);
                            recyclerVideo.setAdapter(vIdeoAdapter);
                            vIdeoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    VideoListBean.DataBean dataBean = data.get(position);
                                    String deviceSerial = dataBean.getDeviceSerial();
                                    int status = dataBean.getStatus();
                                    if (status==1){
                                        Intent intent = new Intent(_mActivity, VideoDetailActivity.class);
                                        intent.putExtra("video_id",deviceSerial);
                                        startActivity(intent);
                                    }else {
                                        ToastUtils.showLong("设备不在线");
                                    }
                                }
                            });

                        } else {
                            if (msg != null) {
                                ToastUtils.showShort(msg);
                            }
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

    @Override
    protected void initView() {
    }


    @Override
    protected void initData() {

    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销eventbus
        EventBus.getDefault().unregister(this);
    }
}
