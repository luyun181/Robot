package com.robot.robotcontrol.ui.fragmennt.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robot.robotcontrol.R;
import com.robot.robotcontrol.base.BaseMainFragment;
import com.robot.robotcontrol.ui.fragmennt.video.child.VideoHomeFragment;


public class VideoFragment extends BaseMainFragment {


    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);

        return view;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.video_fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.me_container, VideoHomeFragment.newInstance());
        }
    }


}
