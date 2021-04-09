package com.osj.stockinfomation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.osj.stockinfomation.Adapter.VpAdapterMain;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.databinding.ActivityMainBinding;
import com.osj.stockinfomation.util.ErrorController;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    private VpAdapterMain adapter;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPager();
        initClickEvent();
    }

    private void initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    private void initViewPager() {

        try {
            adapter = new VpAdapterMain(getSupportFragmentManager(), this);
            binding.vpMain.setAdapter(adapter);

            binding.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
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

    View.OnClickListener listener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.vpMain.setCurrentItem(0);
            bottomTabSelect(0);
        }
    };

    View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.vpMain.setCurrentItem(1);
            bottomTabSelect(1);
        }
    };

    View.OnClickListener listener3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.vpMain.setCurrentItem(2);
            bottomTabSelect(2);
        }
    };

    View.OnClickListener listener4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
                Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //에디트 텍스트 내용 삭제
        binding.icHeaderBar.btnHeaderSearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.icHeaderBar.edtHeaderSearch.setText("");
                //TODO search api
            }
        });

        //에디트 텍스트 입력 시 실시간 검색
        binding.icHeaderBar.edtHeaderSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void bottomTabSelect(int position){
        binding.icBtnBottomTab.btnBottomTab1.setBackgroundResource(R.drawable.img_bottom1);
        binding.icBtnBottomTab.btnBottomTab2.setBackgroundResource(R.drawable.img_bottom2);
        binding.icBtnBottomTab.btnBottomTab3.setBackgroundResource(R.drawable.img_bottom3);
        binding.icBtnBottomTab.btnBottomTab4.setBackgroundResource(R.drawable.img_bottom3);

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

    /**
     * 백버튼 클릭
     */
    @Override
    public void onBackPressed() {
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