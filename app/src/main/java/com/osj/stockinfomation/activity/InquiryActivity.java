package com.osj.stockinfomation.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityInquiryBinding;
import com.osj.stockinfomation.databinding.ActivityNicknameSettingBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InquiryActivity extends BaseActivity {
    ActivityInquiryBinding binding;
    CustomerMainPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        initView();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquiry);

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

        binding.btnInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean chk = Pattern.matches("^[가-힣]{2,4}+[ ]{0,10}$", binding.edtInquiryName.getText().toString());
                if(!chk || binding.edtInquiryName.getText().toString().isEmpty()) {
                    showCustomAlert(InquiryActivity.this, "", "올바른 성함을 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                    return;
                }

                chk = isValidEmail(binding.edtInquiryEmail.getText().toString());
                if(!chk || binding.edtInquiryEmail.getText().toString().isEmpty()) {
                    showCustomAlert(InquiryActivity.this, "", "올바른 이메일을 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                    return;
                }

                if(binding.edtInquiryContent.getText().toString().isEmpty()){
                    showCustomAlert(InquiryActivity.this, "", "문의내용을 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                    return;
                }

                showProgress();
                mPresenter.setInquiry(InquiryActivity.this, binding.edtInquiryName.getText().toString(), binding.edtInquiryEmail.getText().toString(), binding.edtInquiryContent.getText().toString(), new CommonCallback.SingleObjectCallback<BaseDAO>() {
                    @Override
                    public void onSuccess(BaseDAO result) {
                        hideProgress();
                        showCustomAlert(InquiryActivity.this, "문의사항이 발송되었습니다.", "빠른시일내로 답변드리겠습니다.", true, R.drawable.img_alert_ok, 1, "", "", null, new OnClickListener() {
                            @Override
                            public void onClick() {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String fault) {
                        hideProgress();
                        showCustomAlert(InquiryActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                    }
                });
            }
        });

        binding.edtInquiryContent.addTextChangedListener(watcher);
        binding.edtInquiryName.addTextChangedListener(watcher);
        binding.edtInquiryEmail.addTextChangedListener(watcher);
    }

    public static boolean isValidEmail(String email) { boolean err = false; String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w++[ ]{0,10}$"; Pattern p = Pattern.compile(regex); Matcher m = p.matcher(email); if(m.matches()) { err = true; } return err; }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btnSetting();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void btnSetting(){
        if(binding.edtInquiryContent.getText().toString().isEmpty() ||
            binding.edtInquiryEmail.getText().toString().isEmpty() ||
            binding.edtInquiryName.getText().toString().isEmpty()){
            binding.btnInquiry.setBackgroundColor(Color.parseColor("#D6D5DE"));
            binding.btnInquiry.setTextColor(Color.parseColor("#999999"));
            binding.btnInquiry.setClickable(false);
        } else {
            binding.btnInquiry.setBackgroundColor(Color.parseColor("#C50000"));
            binding.btnInquiry.setTextColor(Color.parseColor("#ffffff"));
            binding.btnInquiry.setClickable(true);
        }
    }
}
