package com.robot.robotcontrol.ui.fragmennt.me.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.robot.robotcontrol.R;
import com.robot.robotcontrol.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by YoKeyword on 16/6/5.
 */
public class MeHomeFragment extends BaseFragment {



    public static MeHomeFragment newInstance() {

        Bundle args = new Bundle();

        MeHomeFragment fragment = new MeHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment_home, container, false);

        return view;
    }

    @Override
    protected void initView() {


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
    }


}
