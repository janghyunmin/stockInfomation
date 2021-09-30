package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterMyFavList;
import com.osj.stockinfomation.Adapter.MonthAdapter;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetLikeDAO;
import com.osj.stockinfomation.DAO.GetLikeDetailDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.databinding.FragmentSecBinding;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.HTMLTextView;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.RecyclerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/** jhm 2021-09-23 오후 5:58
 * 실시간 수익현황 TAB
 ***/
public class FragmentSec extends BaseFragment {

    private Activity activity;


    /** jhm 2021-09-24 오전 11:00
     * Common
     ***/
    Context mContext;
    FragmentSecBinding binding;
    ViewFlipper v_fllipper; // 배너 이미지 슬라이드


    public static final int SUM = 0;
    public static final int LIVE = 1;



    public static int[] shuffle(int[] arr){
        for(int x=0;x<arr.length;x++){
            int i = (int)(Math.random()*arr.length);
            int j = (int)(Math.random()*arr.length);

            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        return arr;
    }


    public FragmentSec(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sec, container, false);
        View view = binding.getRoot();
        mContext = getContext();

        showSlide();
        onResume();


        initView(view);
        setEvent();
        loadData(true);





        try {
            if(EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
                EventBus.getDefault().register(this);
            } else {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e){

        }
        return view;
    }

    public void showSlide(){
        int images[] = {R.drawable.banner1,R.drawable.banner2};

        // shuffle 함수로 랜덤으로 출력함
        int[] arr = shuffle(images);
        v_fllipper = binding.banner;
        for(int image : arr) {
            fllipperImages( image );
        }
    }
    // 이미지 슬라이더 구현 메서드
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        Glide.with(getContext()).load(image).into(imageView);
        //imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);

       /** jhm 2021-09-23 오후 6:36
        * onclick 속성 추가하기 , 클릭후 신청 화면 나오는 로직.
        ***/
    }

    @Override
    protected void initView(View view) {
        /** jhm 2021-09-27 오후 1:05
         * text spannable
         ***/
        String count = binding.accumulate.getText().toString();
        SpannableString acc = new SpannableString(count);
        acc.setSpan(new ForegroundColorSpan(Color.RED),9,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        acc.setSpan(new RelativeSizeSpan(1.9f),9,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.accumulate.setText(acc);

        String per = binding.pick.getText().toString();
        SpannableString pick = new SpannableString(per);
        pick.setSpan(new ForegroundColorSpan(Color.RED),19,23,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pick.setSpan(new RelativeSizeSpan(1.9f),19,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.pick.setText(pick);





        /** jhm 2021-09-27 오후 1:05
         * 하단 누적/실시간 tab
         ***/
        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tabLayout.setVisibility(View.VISIBLE);
                binding.btnLeft.setTextColor(Color.parseColor("#ffffff"));
                binding.btnLeft.setBackgroundColor(Color.parseColor("#d2191f"));

                binding.btnRight.setTextColor(Color.parseColor("#FF000000"));
                binding.btnRight.setBackgroundColor(Color.parseColor("#f4f4f4"));

                binding.viewPager.setAdapter(new MonthAdapter(getChildFragmentManager(),SUM));
            }
        });

        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tabLayout.setVisibility(View.GONE);

                binding.btnRight.setTextColor(Color.parseColor("#ffffff"));
                binding.btnRight.setBackgroundColor(Color.parseColor("#d2191f"));

                binding.btnLeft.setTextColor(Color.parseColor("#FF000000"));
                binding.btnLeft.setBackgroundColor(Color.parseColor("#f4f4f4"));

                binding.viewPager.setAdapter(new MonthAdapter(getChildFragmentManager(),LIVE));

            }
        });
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("1개월"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("3개월"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("6개월"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("12개월"));
        binding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00abd4"));
        binding.tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        binding.tabLayout.setTabTextColors(Color.parseColor("#d7d7d7"), Color.parseColor("#222222"));


        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void setEvent() {
        try {
            binding.swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayoutDirection direction) {
                    if (direction == SwipyRefreshLayoutDirection.TOP) {
                        loadData(true);
                    } else {
                        loadData(false);
                    }
                }
            });

//            PagingUtil.onRefreshForNested(binding.nestedScrollView, binding.recyclerView, binding.swipeRefreshLayout, new PagingUtil.onPaging() {
//                @Override
//                public void onPaging() {
//                    loadData(false);
//                }
//            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void loadData(boolean isFirst) {

    }

    @Override
    public void onResume() {
        super.onResume();
        binding.viewPager.setAdapter(new MonthAdapter(getChildFragmentManager(),SUM));
    }
}
