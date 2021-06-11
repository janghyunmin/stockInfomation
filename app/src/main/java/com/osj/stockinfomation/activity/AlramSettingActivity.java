package com.osj.stockinfomation.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetAlramDAO;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityAlramSettingBinding;
import com.osj.stockinfomation.databinding.ActivityNicknameSettingBinding;

import java.util.regex.Pattern;

public class AlramSettingActivity extends BaseActivity {
    ActivityAlramSettingBinding binding;
    CustomerMainPresenter mPresenter;

    boolean sw1;
    boolean sw2;
    boolean sw3;
    boolean swAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alram_setting);

        initView();
        loadData();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alram_setting);

        setProgress(binding.progress);
        mPresenter = new CustomerMainPresenter();
    }
    private void initClickEvent() {
        binding.ivAlramSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.llAlramSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.swAlramSetting1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sw1 = b;
                setParam();
            }
        });

        binding.swAlramSetting2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sw2 = b;
                setParam();
            }
        });

        binding.swAlramSetting3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sw3 = b;
                setParam();
            }
        });

        binding.swAlramSettingAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swAll = b;
                if(b){
                    setAlram("y", "y", "y", "y");
                    swAll = true;
                    sw1 = true;
                    sw2 = true;
                    sw3 = true;
                } else {
                    setAlram("n", "n", "n", "n");
                    swAll = false;
                    sw1 = false;
                    sw2 = false;
                    sw3 = false;
                }

                binding.swAlramSetting1.setChecked(sw1);
                binding.swAlramSetting2.setChecked(sw2);
                binding.swAlramSetting3.setChecked(sw3);
                binding.swAlramSettingAll.setChecked(swAll);
            }
        });
    }

    private void loadData(){
        showProgress();
        mPresenter.getAlram(this, new CommonCallback.SingleObjectCallback<GetAlramDAO>() {
            @Override
            public void onSuccess(GetAlramDAO result) {
                hideProgress();
                if(!result.getMb1().isEmpty() && result.getMb1().toLowerCase().equals("n")) sw1 = false;
                else sw1 = true;

                if(!result.getMb2().isEmpty() && result.getMb2().toLowerCase().equals("n")) sw2 = false;
                else sw2 = true;

                if(!result.getMb3().isEmpty() && result.getMb3().toLowerCase().equals("n")) sw3 = false;
                else sw3 = true;

                if(!result.getAlAll().isEmpty() && result.getAlAll().toLowerCase().equals("n")) swAll = false;
                else{
                    sw1 = true;
                    sw3 = true;
                    sw2 = true;
                    swAll = true;
                }
                binding.swAlramSetting1.setChecked(sw1);
                binding.swAlramSetting2.setChecked(sw2);
                binding.swAlramSetting3.setChecked(sw3);
                binding.swAlramSettingAll.setChecked(swAll);
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                showCustomAlert(AlramSettingActivity.this, "", "", true, R.drawable.img_alert_error, 1, "", "", null, null);
            }
        });
    }

    private String setBoolean(boolean result){
        if(!result) return "n";
        else return "y";
    }

    private void setParam(){
        if(sw1 && sw2 && sw3){
            swAll = true;
            binding.swAlramSettingAll.setChecked(swAll);
        }
        setAlram(setBoolean(swAll), setBoolean(sw1), setBoolean(sw2), setBoolean(sw3));
    }

    private void setAlram(String al_all, String mb_1, String mb_2, String mb_3){
        showProgress();
        mPresenter.setAlram(this, al_all, mb_1, mb_2, mb_3, new CommonCallback.SingleObjectCallback<BaseDAO>() {
            @Override
            public void onSuccess(BaseDAO result) {
                hideProgress();
                //binding.swAlramSettingAll.setChecked(swAll);
                //binding.swAlramSetting1.setChecked(sw1);
                //binding.swAlramSetting2.setChecked(sw2);
                //binding.swAlramSetting3.setChecked(sw3);
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                showCustomAlert(AlramSettingActivity.this, "", "", true, R.drawable.img_alert_error, 1, "", "", null, null);
            }
        });
    }
}
