package com.osj.stockinfomation.Adapter;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.osj.stockinfomation.Fragment.FragmentFirst;
import com.osj.stockinfomation.Fragment.FragmentFour;
import com.osj.stockinfomation.Fragment.FragmentSec;
import com.osj.stockinfomation.Fragment.FragmentThrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class VpAdapterMain extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();


    public VpAdapterMain(FragmentManager fm, Activity activity) {
        super(fm);
        mFragmentList.add(new FragmentFirst(activity));
        mFragmentList.add(new FragmentSec(activity));
        mFragmentList.add(new FragmentThrid(activity));
        mFragmentList.add(new FragmentFour(activity));
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
