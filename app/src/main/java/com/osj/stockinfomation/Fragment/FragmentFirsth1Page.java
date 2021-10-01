package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.osj.stockinfomation.Adapter.AdapterHotMain;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DataModel.CloseIndexList;
import com.osj.stockinfomation.DataModel.DateList;
import com.osj.stockinfomation.DataModel.HotList;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.databinding.FragmentFirst1PageBinding;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentFirsth1Page extends BaseFragment {
    private CustomerMainPresenter mPresenter;

    private Activity activity;

    Handler handler = new Handler();
    public FragmentFirst1PageBinding binding;

    private ConstraintLayout parent;


    /** jhm 2021-09-28 오후 1:15
     * 코스피 , 코스닥 종가 변수
     ***/
    String get_kospi = "";
    String get_kosdaq = "";


    /** jhm 2021-09-23 오후 4:13
     * 하단 상위 종목 5개
     ***/
    ArrayList<HotList> hotList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterHotMain adapterHotMain;


    ArrayList<Entry> kospi_list = new ArrayList<>();
    ArrayList<Entry> kosdaq_list = new ArrayList<>();

    // marker ui
    MyMarkerView myMarkerView;


    // 코스피 / 코스닥 날짜 동일데이터
    ArrayList<DateList> dateList = new ArrayList<>();
    int[] chart_colorArray = new int[] {Color.parseColor("#FF2727"),Color.parseColor("#CEA1B4DC")};
    String[] legendName = {"코스피 지수","코스닥 지수"};

    public FragmentFirsth1Page(Activity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_first_1_page, container, false);
        View view = binding.getRoot();
        initView(view);
//        setEvent();
        // loadData(true);


        try {
            if(EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
                EventBus.getDefault().register(this);
            } else {
                EventBus.getDefault().register(this);
            }

            Log.d("osj", "FragmentFirsth1Page onCreateView");
        } catch (Exception e){

        }
        return view;
    }

    protected void initView(View view) {
        try {
            mPresenter = new CustomerMainPresenter();


            /** jhm 2021-09-28 오전 11:26
             * 코스피 / 코스닥 종가 정보
             ***/
            getCloseIndex();
            getChartData(); // 차트 데이터 10일치

            /** jhm 2021-09-23 오후 4:40
             * 코스피 지수
             ***/
            KospiThread kospi_req = new KospiThread();
            kospi_req.start();

            /** jhm 2021-09-23 오후 4:40
             * 코스닥 지수
             ***/
            KosDaqThread kosdaq_req = new KosDaqThread();
            kosdaq_req.start();



            /** jhm 2021-09-23 오후 4:40
             * 상위 5개 종목
             ***/


            TopRankThread top_rank_req = new TopRankThread();
            top_rank_req.start();




            binding.hotRv.setHasFixedSize(true);
            binding.hotRv.setItemAnimator(new DefaultItemAnimator());
            binding.hotRv.setNestedScrollingEnabled(true);
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.hotRv.setLayoutManager(linearLayoutManager);
            binding.hotRv.setAdapter(adapterHotMain);
            //adapterHotMain.notifyDataSetChanged();


            myMarkerView = new MyMarkerView(getContext(),R.layout.markerviewtext);


            setProgress((ProgressBar)view.findViewById(R.id.progress));




        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    @Override
    protected void setEvent() {

    }


//    protected void setEvent() {
//        try {
//            btn_first1_page.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //swipeRefreshLayout.setVisibility(View.VISIBLE);
//                    //rl_first1_page.setVisibility(View.GONE);
//                    binding.parent.setVisibility(View.GONE);
//                    C.backIndex = false;
//                }
//            });
//        } catch (Exception e) {
//            ErrorController.showError(e);
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e){

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event.position == 11){
            Log.d("osj", "first1page load date");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
//            loadData(true);

        }
    }


    @Override
    public void onBackPressed() {
        if(binding.parent.getVisibility() == View.VISIBLE){
            //swipeRefreshLayout.setVisibility(View.VISIBLE);
            binding.parent.setVisibility(View.GONE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    C.backIndex = false;
                }
            }, 100);
        }
    }


    public void getCloseIndex(){
        mPresenter.getCloseIndex(getActivity(), new CommonCallback.SingleObjectCallback<CloseIndexList>() {
            @Override
            public void onSuccess(CloseIndexList result) {
                LogUtil.logE("result : " + result.getCloseList().size());
                if(result.getCloseList()!=null){


                    for(int index = 0; index < result.getCloseList().size(); index++){
                        switch (result.getCloseList().get(index).getIssuecode()){
                            case "K1":
                                get_kospi = result.getCloseList().get(index).getTrdPrc();
                                break;
                            case "Q1":
                                get_kosdaq = result.getCloseList().get(index).getTrdPrc();
                                break;
                        }




                    }

                }else{
                    LogUtil.logE("실패..");
                }
            }

            @Override
            public void onFailed(String fault) {
                LogUtil.logE("fail " + fault);
            }
        });
    }


    public void getChartData(){
        mPresenter.getChartData(getActivity(), new CommonCallback.SingleObjectCallback<CloseIndexList>() {
            @Override
            public void onSuccess(CloseIndexList result) {
                LogUtil.logE("getChart result : " + result.getCloseList().size());
                if(result.getCloseList()!=null){

                    kospi_list.clear();
                    kosdaq_list.clear();
                    dateList.clear();


                    for(int index = 0; index < result.getCloseList().size(); index++){
                        switch (result.getCloseList().get(index).getIssuecode()){
                            case "K1":
                                dateList.add(new DateList(result.getCloseList().get(index).getDate()));
                                kospi_list.add(new Entry(index,Float.parseFloat(result.getCloseList().get(index).getTrdPrc())));
                                break;
                            case "Q1":
                                dateList.add(new DateList(result.getCloseList().get(index).getDate()));
                                kosdaq_list.add(new BarEntry(index,Float.parseFloat(result.getCloseList().get(index).getTrdPrc())));

                                break;
                        }
                    }

                    gridKospi();
                    gridKosdaq();

                }else{
                    LogUtil.logE("실패..");
                }
            }

            @Override
            public void onFailed(String fault) {
                LogUtil.logE("fail " + fault);
            }
        });
    }

    class KospiThread extends Thread {
        @Override
        public void run() {
            try {
                StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/index".replace("{marketcode}", URLEncoder.encode("kospi", "UTF-8")));
                urlBuilder.append("?");
                urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xxb7342660d3bb4a3289781ab97a31f4d2", "UTF-8"));
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null)
                                break;
                            kospiData(line);
                        }
                        reader.close();
                    }
                    conn.disconnect();

                }



            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class KosDaqThread extends Thread {
        @Override
        public void run() {
            try {
                StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/stocks/{marketcode}/index".replace("{marketcode}", URLEncoder.encode("kosdaq", "UTF-8")));
                urlBuilder.append("?");
                urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xxb7342660d3bb4a3289781ab97a31f4d2", "UTF-8"));
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null)
                                break;
                            kosdaqData(line);
                        }
                        reader.close();
                    }
                    conn.disconnect();

                }



            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class TopRankThread extends Thread {
        @Override
        public void run() {
            try {
                StringBuilder urlBuilder = new StringBuilder("https://sandbox-apigw.koscom.co.kr/v2/market/multiquote/stocks/{marketcode}/price".replace("{marketcode}", URLEncoder.encode("kospi", "UTF-8")));
                urlBuilder.append("?");
                urlBuilder.append(URLEncoder.encode("isuCd","UTF-8") + "=" + URLEncoder.encode("005930,035720,035420,066570,036570", "UTF-8") + "&");
                urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode("l7xxb7342660d3bb4a3289781ab97a31f4d2", "UTF-8"));
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null)
                                break;
                            topRankData(line);
                        }
                        reader.close();
                    }
                    conn.disconnect();

                }



            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }











    //showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
    public void kospiData(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject resultObj = jsonObject.getJSONObject("result");

                    String trdPrc = resultObj.getString("trdPrc"); // 코스피 현재 지수
                    String cmpprevddTpCd = resultObj.getString("cmpprevddTpCd"); // 전일대비 구분코드
                    // 1:상한 , 2:상승 , 3:보합 , 4.하한 , 5.하락 , 6.기세상한 , 7.기세상승 , 8.기세하한 , 9.기세하락
                    Double cmpprevddPrc = resultObj.getDouble("cmpprevddPrc"); // 전일대비가격 하락 포인트

                    Double trdDouble = Double.parseDouble(trdPrc); // 현재 지수에서
                    Double trdClose = Double.parseDouble(get_kospi); // 코스피 지수 종가
                    Double per = (trdDouble-trdClose) / trdClose * 100; // (현재지수 - 종가) / 종가 * 100

                    //. 종가 구해와야함
                    switch (cmpprevddTpCd){
                        // 상승 case
                        case "1" :
                        case "2" :
                        case "6" :
                        case "7" :
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKospiImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.img_up_arrow));
                            } else {
                                binding.mKospiImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.img_up_arrow));
                            }
                            //Glide.with(getContext()).load(R.drawable.img_up_arrow).into(binding.mKospiImg);
                            binding.mKospiNum.setTextColor(getContext().getColor(R.color.c_fe0000));
                            binding.mKospiNum2.setTextColor(getContext().getColor(R.color.c_fe0000));
                            binding.mKospiNumPer.setTextColor(getContext().getColor(R.color.c_fe0000));
                            break;

                        // 보합 case
                        case "3":
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKospiImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.minus));
                            } else {
                                binding.mKospiImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.minus));
                            }
                            //Glide.with(getContext()).load(R.drawable.minus).into(binding.mKospiImg);
                            binding.mKospiNum.setTextColor(getContext().getColor(R.color.black));
                            binding.mKospiNum2.setTextColor(getContext().getColor(R.color.black));
                            binding.mKospiNumPer.setTextColor(getContext().getColor(R.color.black));
                            break;

                        case "4" :
                        case "5" :
                        case "8" :
                        case "9" :
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKospiImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.img_down_arrow));
                            } else {
                                binding.mKospiImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.img_down_arrow));
                            }

                            //Glide.with(getContext()).load(R.drawable.img_down_arrow).into(binding.mKospiImg);
                            binding.mKospiNum.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            binding.mKospiNum2.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            binding.mKospiNumPer.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            break;

                    }

                    binding.mKospiNum.setText(trdPrc);
                    binding.mKospiNum2.setText(cmpprevddPrc.toString());
                    binding.mKospiNumPer.setText(Double.toString((double)Math.round(per*100)/100) + "%");



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }




    public void kosdaqData(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.logE("data : " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject resultObj = jsonObject.getJSONObject("result");

                    String trdPrc = resultObj.getString("trdPrc"); // 코스피 현재 지수
                    String cmpprevddTpCd = resultObj.getString("cmpprevddTpCd"); // 전일대비 구분코드
                    // 1:상한 , 2:상승 , 3:보합 , 4.하한 , 5.하락 , 6.기세상한 , 7.기세상승 , 8.기세하한 , 9.기세하락
                    Double cmpprevddPrc = resultObj.getDouble("cmpprevddPrc"); // 전일대비가격 하락 포인트

                    Double trdDouble = Double.parseDouble(trdPrc); // 현재 지수에서
                    Double trdClose = Double.parseDouble(get_kosdaq); // 코스닥 지수 종가
                    Double per = (trdDouble-trdClose) / trdClose * 100; // (현재지수 - 종가) / 종가 * 100

                    //. 종가 구해와야함


                    switch (cmpprevddTpCd){
                        // 상승 case
                        case "1" :
                        case "2" :
                        case "6" :
                        case "7" :
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKosdaqImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.img_up_arrow));
                            } else {
                                binding.mKosdaqImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.img_up_arrow));
                            }

                            //Glide.with(getContext()).load(R.drawable.img_up_arrow).into(binding.mKosdaqImg);
                            binding.mKosdaqNum.setTextColor(getContext().getColor(R.color.c_fe0000));
                            binding.mKosdaqNum2.setTextColor(getContext().getColor(R.color.c_fe0000));
                            binding.mKosdaqNumPer.setTextColor(getContext().getColor(R.color.c_fe0000));
                            break;

                        // 보합 case
                        case "3":
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKosdaqImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.minus));
                            } else {
                                binding.mKosdaqImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.minus));
                            }

                            //Glide.with(getContext()).load(R.drawable.minus).into(binding.mKosdaqImg);
                            binding.mKosdaqNum.setTextColor(getContext().getColor(R.color.black));
                            binding.mKosdaqNum2.setTextColor(getContext().getColor(R.color.black));
                            binding.mKosdaqNumPer.setTextColor(getContext().getColor(R.color.black));
                            break;

                        case "4" :
                        case "5" :
                        case "8" :
                        case "9" :
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                binding.mKosdaqImg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.img_down_arrow));
                            } else {
                                binding.mKosdaqImg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.img_down_arrow));
                            }

                            // Glide.with(getContext()).load(R.drawable.img_down_arrow).into(binding.mKosdaqImg);
                            binding.mKosdaqNum.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            binding.mKosdaqNum2.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            binding.mKosdaqNumPer.setTextColor(getContext().getColor(R.color.c_4e7dff));
                            break;

                    }
                    binding.mKosdaqNum.setText(trdPrc);
                    binding.mKosdaqNum2.setText(cmpprevddPrc.toString());
                    binding.mKosdaqNumPer.setText(Double.toString((double)Math.round(per*100)/100) + "%");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void topRankData(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.logE("data : " + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject resultObj = jsonObject.getJSONObject("result");
                    JSONArray resultArr = resultObj.getJSONArray("isulist");

                    hotList.clear();
                    for(int index = 0; index < resultArr.length(); index++){
                        JSONObject rankObj = resultArr.getJSONObject(index);

                        Integer trdPrc = rankObj.getInt("trdPrc"); // 현재가
                        String code = rankObj.getString("isuSrtCd"); // 종목코드
                        String cmpprevddTpCd = rankObj.getString("cmpprevddTpCd"); // 전일대비 구분코드
                        Integer cmpprevddPrc = rankObj.getInt("cmpprevddPrc"); // 전일대비 가격 - 금액

                        hotList.add(new HotList(index+1,code,trdPrc,cmpprevddTpCd,cmpprevddPrc));
                    }


                    adapterHotMain = new AdapterHotMain(hotList,getContext());
                    binding.hotRv.setAdapter(adapterHotMain);
                    adapterHotMain.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }



    /** jhm 2021-09-29 오전 10:49 
     * 차트 그리기
     ***/
    public void gridKospi(){

        XAxis xAxis = binding.lineChartKospi.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);


//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 레이블 아래로
//        xAxis.setLabelCount(5);

        YAxis yAxis = binding.lineChartKospi.getAxisRight(); // y축 오른쪽
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);


        Legend legend = binding.lineChartKospi.getLegend(); //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//하단 왼쪽에 설정
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.c_red)); // 레전드 컬러 설정

        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        entry.formColor = chart_colorArray[0];
        entry.label = String.valueOf(legendName[0]);
        legendEntries[0] = entry;
        legend.setCustom(legendEntries);



        //binding.lineChart.setData(data);

        LineDataSet lineDataSet = new LineDataSet(kospi_list, "코스피 지수");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setFillColor(Color.parseColor("#FF2727")); // 차트 안에 라인배경 색상
        //lineDataSet.setValueTextColor(Color.WHITE);
        //lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#CEFF66A4")); // 부모 동그라미 색상
        lineDataSet.setCircleHoleColor(Color.parseColor("#CEFF2450")); // 자식 동그라미 색상
        //lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawFilled(true);

        LineData chartData = new LineData(lineDataSet);

        myMarkerView.setChartView(binding.lineChartKospi);
        binding.lineChartKospi.setScaleEnabled(false);
        binding.lineChartKospi.getAxisLeft().setDrawGridLines(false);
        binding.lineChartKospi.getXAxis().setDrawGridLines(false);
        binding.lineChartKospi.setPinchZoom(false); // pinCh 두손가락으로 줌 설정
        binding.lineChartKospi.animateXY(0, 1500); // 차트 그려질때 애니메이션
        binding.lineChartKospi.setDescription(null); // 차트 밑에 description 표시 유무
        binding.lineChartKospi.setHighlightPerTapEnabled(true); // 막대 눌렀을때
        binding.lineChartKospi.setHighlightPerDragEnabled(true); // 드래그하면서 옆으로 이동할때
        binding.lineChartKospi.setTouchEnabled(true); // 터치
        binding.lineChartKospi.setDoubleTapToZoomEnabled(false);
        binding.lineChartKospi.setMarker(myMarkerView);
        binding.lineChartKospi.setDrawMarkers(true);
        binding.lineChartKospi.setData(chartData);
        binding.lineChartKospi.invalidate();



    }

    public void gridKosdaq(){


        XAxis xAxis1 = binding.lineChartKosdaq.getXAxis();
        xAxis1.setDrawLabels(false);
        xAxis1.setDrawAxisLine(false);
        xAxis1.setDrawGridLines(false);

//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 레이블 아래로
//        xAxis.setLabelCount(5);

        YAxis yAxis1 = binding.lineChartKosdaq.getAxisRight(); // y축 오른쪽
        yAxis1.setDrawLabels(false);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setDrawGridLines(false);

        Legend legend = binding.lineChartKosdaq.getLegend(); //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//하단 왼쪽에 설정
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.c_CEA1B4DC)); // 레전드 컬러 설정
        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        entry.formColor = chart_colorArray[1];
        entry.label = String.valueOf(legendName[1]);
        legendEntries[0] = entry;
        legend.setCustom(legendEntries);

        //binding.lineChart.setData(data);

        LineDataSet lineDataSet1 = new LineDataSet(kosdaq_list, "코스닥 지수");
        //lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setCubicIntensity(0.2f);
        lineDataSet1.setFillColor(Color.parseColor("#CEA1B4DC")); // 차트 안에 라인배경 색상
        //lineDataSet1.setValueTextColor(Color.WHITE);
        //lineDataSet1.setValueTextSize(10);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(4);
        lineDataSet1.setCircleColor(Color.parseColor("#CEA1B4DC")); // 부모 동그라미 색상
        lineDataSet1.setCircleHoleColor(Color.BLUE); // 자식 동그라미 색상

        //lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawHorizontalHighlightIndicator(false);
        lineDataSet1.setDrawHighlightIndicators(false);
        lineDataSet1.setDrawFilled(true);

        LineData chartData1 = new LineData(lineDataSet1);

        myMarkerView.setChartView(binding.lineChartKosdaq);
        binding.lineChartKosdaq.setScaleEnabled(false);
        binding.lineChartKosdaq.getAxisLeft().setDrawGridLines(false);
        binding.lineChartKosdaq.getXAxis().setDrawGridLines(false);
        binding.lineChartKosdaq.setPinchZoom(false); // pinCh 두손가락으로 줌 설정
        binding.lineChartKosdaq.animateXY(0, 1500); // 차트 그려질때 애니메이션
        binding.lineChartKosdaq.setDescription(null); // 차트 밑에 description 표시 유무
        binding.lineChartKosdaq.setHighlightPerTapEnabled(true); // 막대 눌렀을때
        binding.lineChartKosdaq.setHighlightPerDragEnabled(true); // 드래그하면서 옆으로 이동할때
        binding.lineChartKosdaq.setTouchEnabled(true); // 터치
        binding.lineChartKosdaq.setDoubleTapToZoomEnabled(false);
        binding.lineChartKosdaq.setMarker(myMarkerView);
        binding.lineChartKosdaq.setDrawMarkers(true);
        binding.lineChartKosdaq.setData(chartData1);
        binding.lineChartKosdaq.invalidate();
    }
    public class MyMarkerView extends MarkerView {

        TextView date;
        ImageView dot_img;
        TextView point;


        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            date = (TextView) findViewById(R.id.date);
            dot_img = (ImageView) findViewById(R.id.dot_img);
            point = (TextView) findViewById(R.id.point);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            if (e instanceof Entry) { //선택한 점의 엔트리 값이 받아와짐 (x축, y축)

                date.setText(""+dateList.get((int) highlight.getX()).getDate());
                Entry ce = (Entry) e;

                point.setText("" + ce.getY());
            } else {
                date.setText(""+dateList.get((int) highlight.getX()).getDate());
                point.setText("" + e.getY());
            }

            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }


    }


}


