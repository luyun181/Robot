package com.robot.robotcontrol.ui.fragmennt.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.robotcontrol.R;
import com.robot.robotcontrol.base.BaseMainFragment;
import com.robot.robotcontrol.ui.fragmennt.home.child.HomeListFragment;


public class HomeFragment extends BaseMainFragment {
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.home_container, HomeListFragment.newInstance());
        }
    }
}
