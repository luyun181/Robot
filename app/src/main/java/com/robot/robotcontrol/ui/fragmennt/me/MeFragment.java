package com.robot.robotcontrol.ui.fragmennt.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.robotcontrol.R;
import com.robot.robotcontrol.base.BaseMainFragment;
import com.robot.robotcontrol.ui.fragmennt.me.child.MeHomeFragment;


public class MeFragment extends BaseMainFragment {


    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);

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
            loadRootFragment(R.id.me_container, MeHomeFragment.newInstance());
        }
    }



}
