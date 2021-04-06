package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

public class FragmentThrid extends Fragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;

    public FragmentThrid(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);
        initView(view);
        setEvent();
        loadData(true);
        return view;
    }

    protected void initView(View view) {
        try {
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {
        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void loadData(boolean isFirst) {

    }

}
