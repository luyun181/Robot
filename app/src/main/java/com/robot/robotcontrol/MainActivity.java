package com.robot.robotcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.robot.robotcontrol.base.BaseMainFragment;
import com.robot.robotcontrol.event.TabSelectedEvent;
import com.robot.robotcontrol.ui.activity.login.LoginActivity;
import com.robot.robotcontrol.ui.fragmennt.home.HomeFragment;
import com.robot.robotcontrol.ui.fragmennt.home.child.HomeListFragment;
import com.robot.robotcontrol.ui.fragmennt.me.MeFragment;
import com.robot.robotcontrol.ui.fragmennt.me.child.MeHomeFragment;
import com.robot.robotcontrol.ui.fragmennt.video.VideoFragment;
import com.robot.robotcontrol.ui.fragmennt.video.child.VideoHomeFragment;
import com.robot.robotcontrol.widget.BottomBar;
import com.robot.robotcontrol.widget.BottomBarTab;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener{
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private int REQUEST_CODE = 0x00;
    private int RESULT_CODE = 0x01;
    private Context mContext;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private BottomBar mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = VideoFragment.newInstance();
            mFragments[THIRD] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(VideoHomeFragment.class);
            mFragments[THIRD] = findFragment(MeHomeFragment.class);
        }
        initView();

    }
    private void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.icons8_car, getString(R.string.home)))
                .addItem(new BottomBarTab(this, R.mipmap.icons8_video, getString(R.string.video)))
                .addItem(new BottomBarTab(this, R.mipmap.icons8_account, getString(R.string.me)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                boolean is_login = SPUtils.getInstance().getBoolean("is_login");
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1)

                {
                    if (currentFragment instanceof HomeFragment) {
                        currentFragment.popToChild(HomeListFragment.class, false);
                    } else if (currentFragment instanceof VideoHomeFragment) {
                        currentFragment.popToChild(VideoHomeFragment.class, false);
                    } else if (currentFragment instanceof MeFragment) {
                        currentFragment.popToChild(MeHomeFragment.class, false);
                    }
                    return;
                }


                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1)

                {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
    }
    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
