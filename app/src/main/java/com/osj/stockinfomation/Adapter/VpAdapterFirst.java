package com.osj.stockinfomation.Adapter;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.osj.stockinfomation.Fragment.FragmentFirsth1Page;
import com.osj.stockinfomation.Fragment.FragmentFirsth2Page;
import com.osj.stockinfomation.Fragment.FragmentFirsth3Page;
import com.osj.stockinfomation.Fragment.FragmentFirsth4Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class VpAdapterFirst extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();


    public VpAdapterFirst(FragmentManager fm, Activity activity) {
        super(fm);
        mFragmentList.add(new FragmentFirsth1Page(activity));//home ( 주요지수로 변경 )
        mFragmentList.add(new FragmentFirsth2Page(activity));//favo
        mFragmentList.add(new FragmentFirsth3Page(activity));//급등관련주
        mFragmentList.add(new FragmentFirsth4Page(activity));//mypage
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
