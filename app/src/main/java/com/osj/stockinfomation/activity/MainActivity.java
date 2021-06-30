package com.osj.stockinfomation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.osj.stockinfomation.Adapter.AdapterMainpageSearchList;
import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetCheckFreeDAO;
import com.osj.stockinfomation.DAO.GetContentsViewDAO;
import com.osj.stockinfomation.DAO.GetContentsViewType2DAO;
import com.osj.stockinfomation.DAO.GetSearchMainDAO;
import com.osj.stockinfomation.DAO.GetSearchMainDAOList;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.Http.NSCallback;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityMainBinding;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PushPayloadDAO;
import com.osj.stockinfomation.util.RecyclerDecoration;
import com.osj.stockinfomation.util.WebViewSetter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    private VpAdapterMain adapter;
    public PushPayloadDAO payload = null;

    CustomerMainPresenter mPresenter;

    AdapterMainpageSearchList searchAdapter;

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    YouTubePlayerView youtube;
    AlertDialog alertDialog = null;

    boolean piAll = false;
    boolean pi1 = false;
    boolean pi2 = false;
    boolean pi3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPager();
        initClickEvent();
        loadData();
        initFCM();

        Log.d("osj", "android ID :: " + Util_osj.getAndroidId(this));
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter = new CustomerMainPresenter();
        payload = (PushPayloadDAO) getIntent().getSerializableExtra(C.PUSH_PAYLOAD);

        binding.icHeaderBar.rcHeader.setLayoutManager(new LinearLayoutManager(this));
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(C.recyclerViewItemDepth);
        binding.icHeaderBar.rcHeader.addItemDecoration(spaceDecoration);
    }

    private void initViewPager() {

        try {
            adapter = new VpAdapterMain(getSupportFragmentManager(), this);
            binding.vpMain.setAdapter(adapter);
            binding.vpMain.setOffscreenPageLimit(4);
            binding.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position == 0){
                        EventBus.getDefault().post(new MessageEvent(0));
                    } else if(position == 2){
                        EventBus.getDefault().post(new MessageEvent(2));
                    } else if(position == 3){
                        EventBus.getDefault().post(new MessageEvent(3));
                    }
                    bottomTabSelect(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            bottomTabSelect(0);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void initFCM(){
        try {
            if(payload != null) {

                if (payload.getLinkUrl().equals("page1-1")) {
                    EventBus.getDefault().post(new MessageEvent(0));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(0);
                    bottomTabSelect(0);
                } else if (payload.getLinkUrl().equals("page1-2")) {
                    EventBus.getDefault().post(new MessageEvent(1001));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(0);

                    bottomTabSelect(0);
                } else if (payload.getLinkUrl().equals("page1-3")) {
                    EventBus.getDefault().post(new MessageEvent(1002));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(0);
                    bottomTabSelect(0);
                } else if (payload.getLinkUrl().equals("page1-4")) {
                    EventBus.getDefault().post(new MessageEvent(1003));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(0);
                    bottomTabSelect(0);
                }else if (payload.getLinkUrl().equals("page2")) {
                    EventBus.getDefault().post(new MessageEvent(2));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(1);
                    bottomTabSelect(1);
                } else if (payload.getLinkUrl().equals("page3")) {
                    EventBus.getDefault().post(new MessageEvent(3));
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(2);
                    bottomTabSelect(2);
                } else if (payload.getLinkUrl().equals("page4")) {
                    SearchLayoutGone();
                    binding.vpMain.setCurrentItem(3);
                    bottomTabSelect(3);
                }
            }
        } catch (Exception e){
            showCustomAlert(MainActivity.this, "",
                    "푸시 데이터가 정상적이지 않습니다.", true, R.drawable.img_alert_error, 1, "", "", null, null);
        }
    }

    View.OnClickListener listener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(new MessageEvent(0));
            SearchLayoutGone();
            binding.vpMain.setCurrentItem(0);
            bottomTabSelect(0);
        }
    };

    View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(new MessageEvent(2));
            SearchLayoutGone();
            binding.vpMain.setCurrentItem(1);
            bottomTabSelect(1);
        }
    };

    View.OnClickListener listener3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EventBus.getDefault().post(new MessageEvent(3));
            SearchLayoutGone();
            binding.vpMain.setCurrentItem(2);
            bottomTabSelect(2);
        }
    };

    View.OnClickListener listener4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchLayoutGone();
            binding.vpMain.setCurrentItem(3);
            bottomTabSelect(3);
        }
    };

    private void initClickEvent(){
        binding.icBtnBottomTab.llBottomTab1.setOnClickListener(listener1);
        binding.icBtnBottomTab.btnBottomTab1.setOnClickListener(listener1);
        binding.icBtnBottomTab.txtBottomTab1.setOnClickListener(listener1);
        binding.icHeaderBar.ivHeaderLogo.setOnClickListener(listener1);

        binding.icBtnBottomTab.llBottomTab2.setOnClickListener(listener2);
        binding.icBtnBottomTab.btnBottomTab2.setOnClickListener(listener2);
        binding.icBtnBottomTab.txtBottomTab2.setOnClickListener(listener2);

        binding.icBtnBottomTab.llBottomTab3.setOnClickListener(listener3);
        binding.icBtnBottomTab.btnBottomTab3.setOnClickListener(listener3);
        binding.icBtnBottomTab.txtBottomTab3.setOnClickListener(listener3);

        binding.icBtnBottomTab.llBottomTab4.setOnClickListener(listener4);
        binding.icBtnBottomTab.btnBottomTab4.setOnClickListener(listener4);
        binding.icBtnBottomTab.txtBottomTab4.setOnClickListener(listener4);

        //검색 온오프
        binding.icHeaderBar.btnHeaderSearchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.icHeaderBar.llHeaderSearchLayout.getVisibility() == View.VISIBLE)
                    binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.GONE);
                else
                    binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.VISIBLE);
            }
        });

        //설정화면 이동
        binding.icHeaderBar.btnHeaderSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchLayoutGone();
                Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //에디트 텍스트 내용 삭제
        binding.icHeaderBar.btnHeaderSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.icHeaderBar.edtHeaderSearch.setText("");
            }
        });

        List<GetSearchMainDAOList> data = new ArrayList<>();
        binding.icHeaderBar.tvHeaderNoResult.setVisibility(View.VISIBLE);
        searchAdapter = new AdapterMainpageSearchList(this, data, new AdapterMainpageSearchList.onClickCallback() {
            @Override
            public void onClick(GetSearchMainDAOList item) {
                showProgress();

                if(item.getBoTable().equals("contents02")){
                    mPresenter.getContentViewType2(MainActivity.this, item.getBoTable(), item.getWrId(), new CommonCallback.SingleObjectCallback<GetContentsViewType2DAO>() {
                        @Override
                        public void onSuccess(GetContentsViewType2DAO result1) {
                            hideProgress();
                            for(int i = 0; i < searchAdapter.getData().size(); i++){
                                if(searchAdapter.getData().get(i).getWrId().equals(result1.getWrId())){
                                    searchAdapter.getData().get(i).setWrHit(String.valueOf(Integer.parseInt(searchAdapter.getData().get(i).getWrHit()) + 1));
                                    searchAdapter.notifyDataSetChanged();
                                }
                            }

                            showModalViewYoutube(result1);
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(MainActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                        }
                    });
                } else {
                    mPresenter.getContentView(MainActivity.this, item.getBoTable(), item.getWrId(), new CommonCallback.SingleObjectCallback<GetContentsViewDAO>() {
                        @Override
                        public void onSuccess(GetContentsViewDAO result) {
                            hideProgress();

                            for(int i = 0; i < searchAdapter.getData().size(); i++){
                                if(searchAdapter.getData().get(i).getWrId().equals(result.getWrId())){
                                    searchAdapter.getData().get(i).setWrHit(String.valueOf(Integer.parseInt(searchAdapter.getData().get(i).getWrHit()) + 1));
                                    searchAdapter.notifyDataSetChanged();
                                }
                            }

                            showContentView(result);
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(MainActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                        }
                    });
                }
            }
        });

        binding.icHeaderBar.rcHeader.setAdapter(searchAdapter);

        //에디트 텍스트 입력 시 실시간 검색
        binding.icHeaderBar.edtHeaderSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    List<GetSearchMainDAOList> data = new ArrayList<>();
                    binding.icHeaderBar.tvHeaderNoResult.setVisibility(View.VISIBLE);
                    searchAdapter.setData(data);
                    searchAdapter.notifyDataSetChanged();
                } else {
                    showProgress();
                    mPresenter.getSearchMain(MainActivity.this, charSequence.toString(), new CommonCallback.SingleObjectCallback<GetSearchMainDAO>() {
                        @Override
                        public void onSuccess(GetSearchMainDAO result) {
                            hideProgress();
                            if(result.getList().size() != 0)
                                binding.icHeaderBar.tvHeaderNoResult.setVisibility(View.GONE);
                            else
                                binding.icHeaderBar.tvHeaderNoResult.setVisibility(View.VISIBLE);

                            searchAdapter.setData(result.getList());
                            searchAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(MainActivity.this, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView();
            }
        });
    }

    private void loadData(){
//        showProgress();
//        mPresenter.getFreeUpdate(this, new CommonCallback.SingleObjectCallback<BaseDAO>() {
//            @Override
//            public void onSuccess(BaseDAO result) {
//                hideProgress();
//                binding.btnPi.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailed(String fault) {
//                hideProgress();
//            }
//        });

        showProgress();
        mPresenter.getCheckFree(this, new CommonCallback.SingleObjectCallback<GetCheckFreeDAO>() {
            @Override
            public void onSuccess(GetCheckFreeDAO result) {
                hideProgress();
                if(result.getCheckYn().equals("Y") || result.getCheckYn().equals("y"))
                    binding.btnPi.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
            }
        });
    }

    private void showModalView(){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_main_pi, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        EditText edt_dialog_main_name = (EditText)alertDialog.findViewById(R.id.edt_dialog_main_name);
        EditText edt_dialog_main_phone = (EditText)alertDialog.findViewById(R.id.edt_dialog_main_phone);

        RelativeLayout rl_main_pi_all = (RelativeLayout)alertDialog.findViewById(R.id.rl_main_pi_all);
        ImageView iv_dialog_main_all_check = (ImageView)alertDialog.findViewById(R.id.iv_dialog_main_all_check);

        ImageView iv_dialog_main_check1 = (ImageView)alertDialog.findViewById(R.id.iv_dialog_main_check1);
        TextView txt_dialog_main_check1 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_check1);
        TextView txt_dialog_main_1 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_1);

        ImageView iv_dialog_main_check2 = (ImageView)alertDialog.findViewById(R.id.iv_dialog_main_check2);
        TextView txt_dialog_main_check2 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_check2);
        TextView txt_dialog_main_2 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_2);

        ImageView iv_dialog_main_check3 = (ImageView)alertDialog.findViewById(R.id.iv_dialog_main_check3);
        TextView txt_dialog_main_check3 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_check3);
        TextView txt_dialog_main_3 = (TextView)alertDialog.findViewById(R.id.txt_dialog_main_3);

        piAll = true;
        pi1 = true;
        pi2 = true;
        pi3 = true;

        iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_on);
        iv_dialog_main_check1.setBackgroundResource(R.drawable.img_pi_check1_on);
        iv_dialog_main_check2.setBackgroundResource(R.drawable.img_pi_check1_on);
        iv_dialog_main_check3.setBackgroundResource(R.drawable.img_pi_check1_on);

        Button btn_dialog_cancel = (Button)alertDialog.findViewById(R.id.btn_dialog_cancel);
        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        Button btn_dialog_ok = (Button)alertDialog.findViewById(R.id.btn_dialog_ok);
        btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pi1 && pi2 && pi3){
                    boolean chk = Pattern.matches("^[가-힣]{2,4}+[ ]{0,10}$", edt_dialog_main_name.getText().toString());
                    if(!chk || edt_dialog_main_name.getText().toString().isEmpty()){
                        showCustomAlert(MainActivity.this, "", "올바른 성함을 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                        return;
                    }

                    boolean flag = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}+[ ]{0,10}$", edt_dialog_main_phone.getText().toString());
                    if(!flag || edt_dialog_main_phone.getText().toString().isEmpty()) {
                        showCustomAlert(MainActivity.this, "", "올바른 휴대폰번호를 입력해 주세요.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                        return;
                    }

                    showProgress();
                    mPresenter.setFreeUpdate(MainActivity.this, edt_dialog_main_name.getText().toString(), edt_dialog_main_phone.getText().toString(), new CommonCallback.SingleObjectCallback<BaseDAO>() {
                        @Override
                        public void onSuccess(BaseDAO result) {
                            hideProgress();
                            binding.btnPi.setVisibility(View.GONE);
                            EventBus.getDefault().post(new MessageEvent(3));
                            showCustomAlert(MainActivity.this, "", "정상적으로 등록되었습니다.", true, R.drawable.img_alert_ok, 1, "", "", null, new OnClickListener() {
                                @Override
                                public void onClick() {
                                    alertDialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(MainActivity.this, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                        }
                    });

                } else {
                    showCustomAlert(MainActivity.this, "", "필수 약관에 동의 해주셔야 합니다.", true, R.drawable.img_alert_error, 1, "", "", null, null);
                }
            }
        });



        rl_main_pi_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!piAll){
                    piAll = true;
                    pi1 = true;
                    pi2 = true;
                    pi3 = true;

                    iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_on);
                    iv_dialog_main_check1.setBackgroundResource(R.drawable.img_pi_check1_on);
                    iv_dialog_main_check2.setBackgroundResource(R.drawable.img_pi_check1_on);
                    iv_dialog_main_check3.setBackgroundResource(R.drawable.img_pi_check1_on);
                } else {
                    piAll = false;
                    pi1 = false;
                    pi2 = false;
                    pi3 = false;

                    iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_off);
                    iv_dialog_main_check1.setBackgroundResource(R.drawable.img_pi_check1_off);
                    iv_dialog_main_check2.setBackgroundResource(R.drawable.img_pi_check1_off);
                    iv_dialog_main_check3.setBackgroundResource(R.drawable.img_pi_check1_off);
                }
            }
        });

        View.OnClickListener click1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pi1){
                    pi1 = false;
                    iv_dialog_main_check1.setBackgroundResource(R.drawable.img_pi_check1_off);
                    iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_off);
                } else {
                    pi1 = true;
                    iv_dialog_main_check1.setBackgroundResource(R.drawable.img_pi_check1_on);
                    if(pi1 && pi2 && pi3) iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_on);
                }
            }
        };

        View.OnClickListener click2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pi2){
                    pi2 = false;
                    iv_dialog_main_check2.setBackgroundResource(R.drawable.img_pi_check1_off);
                    iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_off);
                } else {
                    pi2 = true;
                    iv_dialog_main_check2.setBackgroundResource(R.drawable.img_pi_check1_on);
                    if(pi1 && pi2 && pi3) iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_on);

                }
            }
        };

        View.OnClickListener click3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pi3){
                    pi3 = false;
                    iv_dialog_main_check3.setBackgroundResource(R.drawable.img_pi_check1_off);
                    iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_off);
                } else {
                    pi3 = true;
                    iv_dialog_main_check3.setBackgroundResource(R.drawable.img_pi_check1_on);
                    if(pi1 && pi2 && pi3) iv_dialog_main_all_check.setBackgroundResource(R.drawable.img_pi_check_on);
                }
            }
        };

        txt_dialog_main_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=privacy");
            }
        });

        txt_dialog_main_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=provision");
            }
        });

        txt_dialog_main_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showModalView("/bbs/content.php?co_id=message");
            }
        });

        txt_dialog_main_check1.setOnClickListener(click1);
        iv_dialog_main_check1.setOnClickListener(click1);

        txt_dialog_main_check2.setOnClickListener(click2);
        iv_dialog_main_check2.setOnClickListener(click2);

        txt_dialog_main_check3.setOnClickListener(click3);
        iv_dialog_main_check3.setOnClickListener(click3);
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

    private void bottomTabSelect(int position){
        binding.icBtnBottomTab.btnBottomTab1.setBackgroundResource(R.drawable.img_bottom1);
        binding.icBtnBottomTab.btnBottomTab2.setBackgroundResource(R.drawable.img_bottom2);
        binding.icBtnBottomTab.btnBottomTab3.setBackgroundResource(R.drawable.img_bottom3);
        binding.icBtnBottomTab.btnBottomTab4.setBackgroundResource(R.drawable.img_bottom4);

        binding.icBtnBottomTab.txtBottomTab1.setTextColor(Color.parseColor("#000000"));
        binding.icBtnBottomTab.txtBottomTab2.setTextColor(Color.parseColor("#000000"));
        binding.icBtnBottomTab.txtBottomTab3.setTextColor(Color.parseColor("#000000"));
        binding.icBtnBottomTab.txtBottomTab4.setTextColor(Color.parseColor("#000000"));

        switch (position){
            case 0:
                binding.icBtnBottomTab.btnBottomTab1.setBackgroundResource(R.drawable.img_bottom1_on);
                binding.icBtnBottomTab.txtBottomTab1.setTextColor(Color.parseColor("#C50000"));
                break;
            case 1:
                binding.icBtnBottomTab.btnBottomTab2.setBackgroundResource(R.drawable.img_bottom2_on);
                binding.icBtnBottomTab.txtBottomTab2.setTextColor(Color.parseColor("#C50000"));
                break;
            case 2:
                binding.icBtnBottomTab.btnBottomTab3.setBackgroundResource(R.drawable.img_bottom3_on);
                binding.icBtnBottomTab.txtBottomTab3.setTextColor(Color.parseColor("#C50000"));
                break;
            case 3:
                binding.icBtnBottomTab.btnBottomTab4.setBackgroundResource(R.drawable.img_bottom4_on);
                binding.icBtnBottomTab.txtBottomTab4.setTextColor(Color.parseColor("#C50000"));
                break;
        }
    }

    public void showContentView(GetContentsViewDAO result){
//        binding.icHeaderBar.ivFirst1Profile.setBackground(new ShapeDrawable(new OvalShape()));
//        binding.icHeaderBar.ivFirst1Profile.setClipToOutline(true);

//        Glide.with(this).load(result.getWrFile()).asBitmap().into(binding.icHeaderBar.ivFirst1Profile);

//        if (result.getLikeCheck().toLowerCase().equals("n"))
//            binding.icHeaderBar.ivFirst1Fav.setBackgroundResource(R.drawable.fav_off);
//        else
//            binding.icHeaderBar.ivFirst1Fav.setBackgroundResource(R.drawable.fav_on);
//
//        binding.icHeaderBar.ivFirst1Fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.setLike(MainActivity.this, result.getWrId(), result.getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
//                    @Override
//                    public void onSuccess(SetLikeDAO result1) {
//                        String message = "";
//                        if (result1.getWrLike().toLowerCase().equals("n")) {
//                            message = getString(R.string.page1_fav_off);
//                            binding.icHeaderBar.ivFirst1Fav.setBackgroundResource(R.drawable.fav_off);
//                        } else {
//                            message = getString(R.string.page1_fav_on);
//                            binding.icHeaderBar.ivFirst1Fav.setBackgroundResource(R.drawable.fav_on);
//                        }
//
////                        mPresenter.adapterMainContentListNotifyDataSetChanged(result.getWrId(), result1.getWrLike());
//                        showCustomAlert(MainActivity.this, message, getString(R.string.page1_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
//                    }
//
//                    @Override
//                    public void onFailed(String fault) {
//                        showCustomAlert(MainActivity.this, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
//                    }
//                });
//            }
//        });

        String[] arrStr = result.getWrDatetime().split(" ");

        if (arrStr.length == 2)
            binding.icHeaderBar.txtFirst1Date.setText(arrStr[0]);
        else
            binding.icHeaderBar.txtFirst1Date.setText(result.getWrDatetime());

        binding.icHeaderBar.txtFirst1Name.setText(result.getWrName());
        binding.icHeaderBar.txtFirst1LikeCount.setText(result.getWrLike());
        binding.icHeaderBar.txtFirst1ViewCount.setText(result.getWrHit());
        binding.icHeaderBar.txtFirst1Title.setText(result.getWrSubject());
        binding.icHeaderBar.txtFirst1Page.setHtmlText(result.getWrContent());

        binding.icHeaderBar.btnFirst1Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.icHeaderBar.rlFirst1Page.setVisibility(View.GONE);
                binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.GONE);
        binding.icHeaderBar.rlFirst1Page.setVisibility(View.VISIBLE);
    }

    private void showModalViewYoutube(GetContentsViewType2DAO result){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_category2, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.show();

        youtube = (YouTubePlayerView)alertDialog.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtube);

        youtube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = result.getWrLink1();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertDialog = null;
                youtube.release();
                C.backIndex = false;
            }
        });

        C.backIndex = true;
    }

    private void SearchLayoutGone(){
        binding.icHeaderBar.rlFirst1Page.setVisibility(View.GONE);
        binding.icHeaderBar.edtHeaderSearch.setText("");
        binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.GONE);
    }

    /**
     * 백버튼 클릭
     */
    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for(Fragment fragment : fragmentList){
                if(fragment instanceof NSCallback.OnBackPressedListener){
                    ((NSCallback.OnBackPressedListener)fragment).onBackPressed();
                }
            }
        }

        if(!C.backIndex){
            if(alertDialog != null){
                alertDialog.dismiss();
                youtube.release();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        C.backIndex = false;
                    }
                }, 100);
            } else if(binding.icHeaderBar.rlFirst1Page.getVisibility() == View.VISIBLE){
                binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.VISIBLE);
                binding.icHeaderBar.rlFirst1Page.setVisibility(View.GONE);
            } else if(binding.icHeaderBar.llHeaderSearchLayout.getVisibility() == View.VISIBLE){
                binding.icHeaderBar.edtHeaderSearch.setText("");
                binding.icHeaderBar.llHeaderSearchLayout.setVisibility(View.GONE);
            } else {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis();
                    toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                    finish();
                    toast.cancel();
                }
            }
        }
    }

    public void setCurrentPage(int page){
        SearchLayoutGone();
        binding.vpMain.setCurrentItem(page);
        bottomTabSelect(page);
    }
}