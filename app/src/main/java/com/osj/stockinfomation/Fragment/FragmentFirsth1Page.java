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


    /** jhm 2021-09-28 ?????? 1:15
     * ????????? , ????????? ?????? ??????
     ***/
    String get_kospi = "";
    String get_kosdaq = "";


    /** jhm 2021-09-23 ?????? 4:13
     * ?????? ?????? ?????? 5???
     ***/
    ArrayList<HotList> hotList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AdapterHotMain adapterHotMain;


    ArrayList<Entry> kospi_list = new ArrayList<>();
    ArrayList<Entry> kosdaq_list = new ArrayList<>();

    // marker ui
    MyMarkerView myMarkerView;


    // ????????? / ????????? ?????? ???????????????
    ArrayList<DateList> dateList = new ArrayList<>();
    int[] chart_colorArray = new int[] {Color.parseColor("#FF2727"),Color.parseColor("#CEA1B4DC")};
    String[] legendName = {"????????? ??????","????????? ??????"};

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


            /** jhm 2021-09-28 ?????? 11:26
             * ????????? / ????????? ?????? ??????
             ***/
            getCloseIndex();
            getChartData(); // ?????? ????????? 10??????

            /** jhm 2021-09-23 ?????? 4:40
             * ????????? ??????
             ***/
            KospiThread kospi_req = new KospiThread();
            kospi_req.start();

            /** jhm 2021-09-23 ?????? 4:40
             * ????????? ??????
             ***/
            KosDaqThread kosdaq_req = new KosDaqThread();
            kosdaq_req.start();



            /** jhm 2021-09-23 ?????? 4:40
             * ?????? 5??? ??????
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
                    LogUtil.logE("??????..");
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
                    LogUtil.logE("??????..");
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

                    String trdPrc = resultObj.getString("trdPrc"); // ????????? ?????? ??????
                    String cmpprevddTpCd = resultObj.getString("cmpprevddTpCd"); // ???????????? ????????????
                    // 1:?????? , 2:?????? , 3:?????? , 4.?????? , 5.?????? , 6.???????????? , 7.???????????? , 8.???????????? , 9.????????????
                    Double cmpprevddPrc = resultObj.getDouble("cmpprevddPrc"); // ?????????????????? ?????? ?????????

                    Double trdDouble = Double.parseDouble(trdPrc); // ?????? ????????????
                    Double trdClose = Double.parseDouble(get_kospi); // ????????? ?????? ??????
                    Double per = (trdDouble-trdClose) / trdClose * 100; // (???????????? - ??????) / ?????? * 100

                    //. ?????? ???????????????
                    switch (cmpprevddTpCd){
                        // ?????? case
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

                        // ?????? case
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

                    String trdPrc = resultObj.getString("trdPrc"); // ????????? ?????? ??????
                    String cmpprevddTpCd = resultObj.getString("cmpprevddTpCd"); // ???????????? ????????????
                    // 1:?????? , 2:?????? , 3:?????? , 4.?????? , 5.?????? , 6.???????????? , 7.???????????? , 8.???????????? , 9.????????????
                    Double cmpprevddPrc = resultObj.getDouble("cmpprevddPrc"); // ?????????????????? ?????? ?????????

                    Double trdDouble = Double.parseDouble(trdPrc); // ?????? ????????????
                    Double trdClose = Double.parseDouble(get_kosdaq); // ????????? ?????? ??????
                    Double per = (trdDouble-trdClose) / trdClose * 100; // (???????????? - ??????) / ?????? * 100

                    //. ?????? ???????????????


                    switch (cmpprevddTpCd){
                        // ?????? case
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

                        // ?????? case
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

                        Integer trdPrc = rankObj.getInt("trdPrc"); // ?????????
                        String code = rankObj.getString("isuSrtCd"); // ????????????
                        String cmpprevddTpCd = rankObj.getString("cmpprevddTpCd"); // ???????????? ????????????
                        Integer cmpprevddPrc = rankObj.getInt("cmpprevddPrc"); // ???????????? ?????? - ??????

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



    /** jhm 2021-09-29 ?????? 10:49 
     * ?????? ?????????
     ***/
    public void gridKospi(){

        XAxis xAxis = binding.lineChartKospi.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);


//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X??? ????????? ?????????
//        xAxis.setLabelCount(5);

        YAxis yAxis = binding.lineChartKospi.getAxisRight(); // y??? ?????????
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);


        Legend legend = binding.lineChartKospi.getLegend(); //????????? ?????? (?????? ?????? ?????? ????????? ???????????? ??????)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//?????? ????????? ??????
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.c_red)); // ????????? ?????? ??????

        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        entry.formColor = chart_colorArray[0];
        entry.label = String.valueOf(legendName[0]);
        legendEntries[0] = entry;
        legend.setCustom(legendEntries);



        //binding.lineChart.setData(data);

        LineDataSet lineDataSet = new LineDataSet(kospi_list, "????????? ??????");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setFillColor(Color.parseColor("#FF2727")); // ?????? ?????? ???????????? ??????
        //lineDataSet.setValueTextColor(Color.WHITE);
        //lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.setCircleColor(Color.parseColor("#CEFF66A4")); // ?????? ???????????? ??????
        lineDataSet.setCircleHoleColor(Color.parseColor("#CEFF2450")); // ?????? ???????????? ??????
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
        binding.lineChartKospi.setPinchZoom(false); // pinCh ?????????????????? ??? ??????
        binding.lineChartKospi.animateXY(0, 1500); // ?????? ???????????? ???????????????
        binding.lineChartKospi.setDescription(null); // ?????? ?????? description ?????? ??????
        binding.lineChartKospi.setHighlightPerTapEnabled(true); // ?????? ????????????
        binding.lineChartKospi.setHighlightPerDragEnabled(true); // ?????????????????? ????????? ????????????
        binding.lineChartKospi.setTouchEnabled(true); // ??????
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

//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X??? ????????? ?????????
//        xAxis.setLabelCount(5);

        YAxis yAxis1 = binding.lineChartKosdaq.getAxisRight(); // y??? ?????????
        yAxis1.setDrawLabels(false);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setDrawGridLines(false);

        Legend legend = binding.lineChartKosdaq.getLegend(); //????????? ?????? (?????? ?????? ?????? ????????? ???????????? ??????)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//?????? ????????? ??????
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.c_CEA1B4DC)); // ????????? ?????? ??????
        LegendEntry[] legendEntries = new LegendEntry[1];
        LegendEntry entry = new LegendEntry();
        entry.formColor = chart_colorArray[1];
        entry.label = String.valueOf(legendName[1]);
        legendEntries[0] = entry;
        legend.setCustom(legendEntries);

        //binding.lineChart.setData(data);

        LineDataSet lineDataSet1 = new LineDataSet(kosdaq_list, "????????? ??????");
        //lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setCubicIntensity(0.2f);
        lineDataSet1.setFillColor(Color.parseColor("#CEA1B4DC")); // ?????? ?????? ???????????? ??????
        //lineDataSet1.setValueTextColor(Color.WHITE);
        //lineDataSet1.setValueTextSize(10);
        lineDataSet1.setLineWidth(2);
        lineDataSet1.setCircleRadius(4);
        lineDataSet1.setCircleColor(Color.parseColor("#CEA1B4DC")); // ?????? ???????????? ??????
        lineDataSet1.setCircleHoleColor(Color.BLUE); // ?????? ???????????? ??????

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
        binding.lineChartKosdaq.setPinchZoom(false); // pinCh ?????????????????? ??? ??????
        binding.lineChartKosdaq.animateXY(0, 1500); // ?????? ???????????? ???????????????
        binding.lineChartKosdaq.setDescription(null); // ?????? ?????? description ?????? ??????
        binding.lineChartKosdaq.setHighlightPerTapEnabled(true); // ?????? ????????????
        binding.lineChartKosdaq.setHighlightPerDragEnabled(true); // ?????????????????? ????????? ????????????
        binding.lineChartKosdaq.setTouchEnabled(true); // ??????
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
            if (e instanceof Entry) { //????????? ?????? ????????? ?????? ???????????? (x???, y???)

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


