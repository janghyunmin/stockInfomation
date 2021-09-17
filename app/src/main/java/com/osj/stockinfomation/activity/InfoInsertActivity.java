package com.osj.stockinfomation.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.InfomationEditLayoutBinding;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PreferencesUtil;
import com.osj.stockinfomation.util.PushPayloadDAO;
import com.osj.stockinfomation.util.StatusBarCustom;
import com.osj.stockinfomation.util.WebViewSetter;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Pattern;

public class InfoInsertActivity extends BaseActivity implements View.OnClickListener{


    InfomationEditLayoutBinding binding;
    PushPayloadDAO payload = null;
    CustomerMainPresenter customerMainPresenter;
    boolean piAll = false;
    boolean pi1 = false;
    boolean pi2 = false;
    boolean pi3 = false;
    Context mContext;

    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        binding = DataBindingUtil.setContentView(this,R.layout.infomation_edit_layout);

        payload = (PushPayloadDAO) getIntent().getSerializableExtra(C.PUSH_PAYLOAD);

        customerMainPresenter = new CustomerMainPresenter();



        if(PreferencesUtil.getString(mContext, PreferencesUtil.PreferenceKey.PREF_PERMISSION_INFO, "N").equals("N")){
            showPermisstionCustomAlert(mContext, new OnCancelListener() {
                @Override
                public void onClick() {
                    finish();
                }
            }, new OnClickListener() {
                @Override
                public void onClick() {
                    PreferencesUtil.putString(mContext, PreferencesUtil.PreferenceKey.PREF_PERMISSION_INFO, "Y");
                    getToken();  // 사용자 푸시 토근 발행
                }
            });
        } else {
            getToken();  // 사용자 푸시 토근 발행
        }

        // 각각의 체크박스 onclick
        binding.ivDialogMainAllCheck.setOnClickListener(this);
        binding.ivDialogMainCheck1.setOnClickListener(this);
        binding.ivDialogMainCheck2.setOnClickListener(this);
        binding.ivDialogMainCheck3.setOnClickListener(this);


        // 보기 text onclick
        binding.txtDialogMain1.setOnClickListener(this);
        binding.txtDialogMain2.setOnClickListener(this);
        binding.txtDialogMain3.setOnClickListener(this);

        goInfo();
    }




    /**
     * PUSH 토큰 획득
     */
    private void getToken(){
        Log.e("getToken!","");
        showProgress();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    hideProgress();

                } else {
                    hideProgress();
                    token = task.getResult().getToken();
                    PreferencesUtil.putString(mContext, PreferencesUtil.PreferenceKey.PREF_TOKEN, token);
                }
            }
        });
    }

    public void goInfo(){
        /** jhm 2021-09-16 오후 2:40
         * edittext 에서 입력받은 값을 검증 후 화면처리
         ***/
        piAll = true;
        pi1 = true;
        pi2 = true;
        pi3 = true;

        Glide.with(mContext).load(R.drawable.img_pi_check_on).into(binding.ivDialogMainAllCheck);
        Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck1);
        Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck2);
        Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck3);



        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pi1 && pi2 && pi3){
                    boolean chk = Pattern.matches("^[가-힣]{2,4}+[ ]{0,10}$", binding.edtDialogMainName.getText().toString());
                    if(!chk || binding.edtDialogMainName.getText().toString().isEmpty()){
                        showCustomAlert(InfoInsertActivity.this, "", "올바른 성함을 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                        return;
                    }

                    boolean flag = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}+[ ]{0,10}$", binding.edtDialogMainPhone.getText().toString());
                    if(!flag || binding.edtDialogMainPhone.getText().toString().isEmpty()) {
                        showCustomAlert(InfoInsertActivity.this, "", "올바른 휴대폰번호를 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                        return;
                    }

                    /** jhm 2021-09-16 오후 3:27
                     * 사용자가 모두 작성 완료 후 입력받은 값으로 insert_user.php API call
                     ***/
                    LogUtil.logE("NAME : " + binding.edtDialogMainName.getText().toString());
                    LogUtil.logE("ph : " + binding.edtDialogMainPhone.getText().toString());
                    showProgress();
//                    customerMainPresenter.setUser(InfoInsertActivity.this, binding.edtDialogMainName.getText().toString(), binding.edtDialogMainPhone.getText().toString(), new CommonCallback.SingleObjectCallback<BaseDAO>() {
//                        @Override
//                        public void onSuccess(BaseDAO result) {
//                            hideProgress();
//                                if(result.getResultCode() == "00"){
//                                    LogUtil.logE("set_user.php Call , g5_member Insert S");
//                                }
//                        }
//
//                        @Override
//                        public void onFailed(String fault) {
//                            hideProgress();
//                        }
//                    });



                    customerMainPresenter.setInsertUser(InfoInsertActivity.this, binding.edtDialogMainName.getText().toString(), binding.edtDialogMainPhone.getText().toString(),token, new CommonCallback.SingleObjectCallback<BaseDAO>() {
                        @Override
                        public void onSuccess(BaseDAO result) {
                            hideProgress();
                            LogUtil.logE("성공");
                            EventBus.getDefault().post(new MessageEvent(3));
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            if(payload != null)
                                intent.putExtra(C.PUSH_PAYLOAD, payload);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                        }
                    });



                } else{
                    showCustomAlert(InfoInsertActivity.this, "", "필수 약관에 동의 해주셔야 합니다.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                }






            }
        });
    }

    private void showModalView(String url){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_setting_webview, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        WebView webview = (WebView)alertDialog.findViewById(R.id.dialog_setting_webview);
        WebViewSetter.optimize(webview, this);
        webview.loadUrl(C.BASE_URL + url);

        ((ImageView)alertDialog.findViewById(R.id.iv_setting_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 전체 동의하기 이미지 클릭시
            case R.id.iv_dialog_main_all_check:
                if(!piAll){
                    piAll = true;
                    pi1 = true;
                    pi2 = true;
                    pi3 = true;

                    Glide.with(mContext).load(R.drawable.img_pi_check_on).into(binding.ivDialogMainAllCheck);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck1);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck2);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck3);
                } else {
                    piAll = false;
                    pi1 = false;
                    pi2 = false;
                    pi3 = false;
                    Glide.with(mContext).load(R.drawable.img_pi_check_off).into(binding.ivDialogMainAllCheck);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck1);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck2);
                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck3);
                }
                break;

            // 개인정보 보호처리방침 이미지
            case R.id.iv_dialog_main_check1:
                if(pi1){
                    pi1 = false;

                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck1);
                    Glide.with(mContext).load(R.drawable.img_pi_check_off).into(binding.ivDialogMainAllCheck);

                } else {
                    pi1 = true;

                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck1);
                    if(pi1 && pi2 && pi3) Glide.with(mContext).load(R.drawable.img_pi_check_on).into(binding.ivDialogMainAllCheck);
                }
                break;


            case R.id.iv_dialog_main_check2:
                if(pi2){
                    pi2 = false;
                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck2);
                    Glide.with(mContext).load(R.drawable.img_pi_check_off).into(binding.ivDialogMainAllCheck);
                } else {
                    pi2 = true;
                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck2);
                    if(pi1 && pi2 && pi3)  Glide.with(mContext).load(R.drawable.img_pi_check_on).into(binding.ivDialogMainAllCheck);

                }
                break;
            case R.id.iv_dialog_main_check3:
                if(pi3){
                    pi3 = false;
                    Glide.with(mContext).load(R.drawable.img_pi_check1_off).into(binding.ivDialogMainCheck3);
                    Glide.with(mContext).load(R.drawable.img_pi_check_off).into(binding.ivDialogMainAllCheck);
                } else {
                    pi3 = true;
                    Glide.with(mContext).load(R.drawable.img_pi_check1_on).into(binding.ivDialogMainCheck3);
                    if(pi1 && pi2 && pi3) Glide.with(mContext).load(R.drawable.img_pi_check_on).into(binding.ivDialogMainAllCheck);
                }
                break;


            case R.id.txt_dialog_main_1:
                showModalView("/bbs/content.php?co_id=privacy");
                break;
            case R.id.txt_dialog_main_2:
                showModalView("/bbs/content.php?co_id=provision");
                break;
            case R.id.txt_dialog_main_3:
                showModalView("/bbs/content.php?co_id=message");
                break;

        }
    }
}
