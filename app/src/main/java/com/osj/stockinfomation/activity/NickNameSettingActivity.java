package com.osj.stockinfomation.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetNickNameDAO;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityNicknameSettingBinding;

import java.util.regex.Pattern;

public class NickNameSettingActivity extends BaseActivity {
    ActivityNicknameSettingBinding binding;
    CustomerMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_setting);

        initView();
        initClickEvent();
        loadData();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nickname_setting);

        setProgress(binding.progress);
        mPresenter = new CustomerMainPresenter();
    }
    private void initClickEvent(){
        binding.ivNicknameSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.llNicknameSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnNicknameSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean chk = Pattern.matches("^[0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]{1,8}+[ ]{0,10}$", binding.edtNicknameSetting.getText().toString());
                if(!chk){
                    showProgress();
                    mPresenter.setProfile(NickNameSettingActivity.this, binding.edtNicknameSetting.getText().toString(), new CommonCallback.SingleObjectCallback<BaseDAO>() {
                        @Override
                        public void onSuccess(BaseDAO result) {
                            hideProgress();
                            showCustomAlert(NickNameSettingActivity.this, "", "닉네임 변경이 완료되었습니다.", true, R.drawable.img_alert_ok, 1, "", "", null, new OnClickListener() {
                                @Override
                                public void onClick() {
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(NickNameSettingActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                        }
                    });
                } else {
                    showCustomAlert(NickNameSettingActivity.this, "", "닉네임은 한/영 포함\n8자 이내로만 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                }
            }
        });

        binding.edtNicknameSetting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if(count == 0){
                    binding.btnNicknameSetting.setBackgroundColor(Color.parseColor("#D6D5DE"));
                    binding.btnNicknameSetting.setTextColor(Color.parseColor("#999999"));
                    binding.btnNicknameSetting.setClickable(false);
                } else {
                    binding.btnNicknameSetting.setBackgroundColor(Color.parseColor("#C50000"));
                    binding.btnNicknameSetting.setTextColor(Color.parseColor("#ffffff"));
                    binding.btnNicknameSetting.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void loadData(){
        showProgress();
        mPresenter.getProfile(this, new CommonCallback.SingleObjectCallback<GetNickNameDAO>() {
            @Override
            public void onSuccess(GetNickNameDAO result) {
                hideProgress();
                binding.edtNicknameSetting.setText(result.getMbNick());
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                showCustomAlert(NickNameSettingActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
            }
        });
    }
}
