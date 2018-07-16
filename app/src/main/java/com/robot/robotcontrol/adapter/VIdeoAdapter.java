package com.robot.robotcontrol.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.robot.robotcontrol.R;
import com.robot.robotcontrol.bean.VideoListBean;

import java.util.List;

public class VIdeoAdapter extends BaseQuickAdapter<VideoListBean.DataBean, BaseViewHolder> {
    public VIdeoAdapter(int layoutResId, @Nullable List<VideoListBean.DataBean> data) {
        super(layoutResId, data);
    }

    public VIdeoAdapter(@Nullable List<VideoListBean.DataBean> data) {
        super(data);
    }

    public VIdeoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoListBean.DataBean item) {
        String deviceSerial = item.getDeviceSerial();
        if (deviceSerial != null) {
            helper.setText(R.id.tv_video_code, deviceSerial);
        }
        int status = item.getStatus();
        //在线状态：0-不在线，1-在线
        switch (status) {
            case 0:
                helper.setText(R.id.tv_status, "不在线");
                break;
            case 1:
                helper.setText(R.id.tv_status, "在线");
                break;
        }
    }
}
