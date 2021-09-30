package com.osj.stockinfomation.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.osj.stockinfomation.Fragment.LiveFragment;
import com.osj.stockinfomation.Fragment.OneMonthFragment;
import com.osj.stockinfomation.Fragment.SixMonthFragment;
import com.osj.stockinfomation.Fragment.ThreeMonthFragment;
import com.osj.stockinfomation.Fragment.TwelveMonthFragment;

import java.util.ArrayList;
import java.util.List;

public class MonthAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments=new ArrayList<>();

    public MonthAdapter(FragmentManager fm , int flag) {
        super(fm);
        switch (flag){
            case 0 :
                fragments.add(new OneMonthFragment());
                fragments.add(new ThreeMonthFragment());
                fragments.add(new SixMonthFragment());
                fragments.add(new TwelveMonthFragment());
                break;
            case 1:
                fragments.add(new LiveFragment());
                break;
        }

    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

