package com.osj.stockinfomation.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.osj.stockinfomation.Adapter.VpAdapterFirst;
import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

public class FragmentFirst extends Fragment {

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

            tabLayout.addTab(tabLayout.newTab().setText("오늘시황"));
            tabLayout.addTab(tabLayout.newTab().setText("급등뉴스"));
            tabLayout.addTab(tabLayout.newTab().setText("급등관련주"));
            tabLayout.addTab(tabLayout.newTab().setText("수익인증"));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {
        try {

//            this.swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh(SwipyRefreshLayoutDirection direction) {
//                    if (direction == SwipyRefreshLayoutDirection.TOP) {
//                        loadData(true);
//                    } else {
//                        loadData(false);
//                    }
//                }
//            });
//
//            PagingUtil.onRefreshForNested(nestedScrollView, recyclerView, swipeRefreshLayout, new PagingUtil.onPaging() {
//                @Override
//                public void onPaging() {
//                    loadData(false);
//                }
//            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void loadData(boolean isFirst) {

    }

}
