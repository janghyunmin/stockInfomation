package com.osj.stockinfomation.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.osj.stockinfomation.Adapter.VpAdapterFirst;
import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/** jhm 2021-09-23 오후 5:58 
 * 홈탭 & 주요지수
 ***/
public class FragmentFirst extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private VpAdapterFirst adapter;
    private Activity activity;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public FragmentFirst(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        setEvent();
        loadData(true);
        try {
            if(((MainActivity) getActivity()).payload != null) {
                if (((MainActivity) getActivity()).payload.getLinkUrl().equals("page1-1")) {
                    viewPager.setCurrentItem(0);
                } else if (((MainActivity) getActivity()).payload.getLinkUrl().equals("page1-2")) {
                    viewPager.setCurrentItem(1);
                } else if (((MainActivity) getActivity()).payload.getLinkUrl().equals("page1-3")) {
                    viewPager.setCurrentItem(2);
                } else if (((MainActivity) getActivity()).payload.getLinkUrl().equals("page1-4")) {
                    viewPager.setCurrentItem(3);
                }
            }
        } catch (Exception e){
            showCustomAlert(activity, "",
                    "푸시 데이터가 정상적이지 않습니다.", true, R.drawable.img_alert_error, 1, "", "", null, null);
        }

        return view;
    }

    protected void initView(View view) {
        try {
            viewPager = (ViewPager)view.findViewById(R.id.vp_first);
            tabLayout = (TabLayout)view.findViewById(R.id.tl_first);
            adapter = new VpAdapterFirst(getActivity().getSupportFragmentManager(), activity);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            mPresenter = new CustomerMainPresenter();

            /** jhm 2021-09-17 오후 2:32
             * 0917 tab name 변경
             ***/
//        tabLayout.addTab(tabLayout.newTab().setText("오늘시황"));
//        tabLayout.addTab(tabLayout.newTab().setText("급등뉴스"));
//        tabLayout.addTab(tabLayout.newTab().setText("급등관련주"));
//        tabLayout.addTab(tabLayout.newTab().setText("수익인증"));

            tabLayout.addTab(tabLayout.newTab().setText("주요지수"));
            tabLayout.addTab(tabLayout.newTab().setText("오늘시황"));
            tabLayout.addTab(tabLayout.newTab().setText("긴급속보"));
            tabLayout.addTab(tabLayout.newTab().setText("급등관련주"));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e){

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e){

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e){

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event.position == 0){
            EventBus.getDefault().post(new MessageEvent(11));
////            new Handler().postDelayed(new Runnable() {
////                @Override
////                public void run() {
//                    viewPager.setCurrentItem(event.position + 2);
////                }
////            }, 1000);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            viewPager.setCurrentItem(event.position);
//                }
//            }, 100);

        }
    }

    protected void setEvent() {
        try {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    message(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    message(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void message(int position){
        EventBus.getDefault().post(new MessageEvent(position + 11));
    }

    public void loadData(boolean isFirst) {

    }

    @Override
    public void onBackPressed() {

    }

}
