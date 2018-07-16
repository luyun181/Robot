package com.robot.robotcontrol.ui.fragmennt.home.child;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.robot.robotcontrol.R;
import com.robot.robotcontrol.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 */
public class HomeListFragment extends BaseFragment {



    public static HomeListFragment newInstance() {
        Bundle args = new Bundle();
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        return view;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_list;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
