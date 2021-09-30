package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterMainContentList;
import com.osj.stockinfomation.Adapter.AdapterMainContentListToday;
import com.osj.stockinfomation.Adapter.AdapterMainSpotContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage4ContentList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetContentsViewDAO;
import com.osj.stockinfomation.DAO.GetContentsViewType2DAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.BrowserActivity;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.HTMLTextView;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.RecyclerDecoration;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentFirsth2Page extends BaseFragment {

    AdapterMainContentListToday adapterMainContentList;

    private CustomerMainPresenter mPresenter;
    private Activity activity;
    RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;

    private HTMLTextView txt_first1_page;
    private ScrollView sl_first1_page;
    private Button btn_first1_page;
    private RelativeLayout rl_first1_page;

    private ImageView iv_first1_profile;
    private TextView txt_first1_date;
    private TextView txt_first1_name;
    private ImageView iv_first1_fav;
    private TextView txt_first1_view_count;
    private TextView txt_first1_like_count;
    private TextView txt_first1_title;

    public FragmentFirsth2Page(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_2_page, container, false);
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

            Log.d("osj", "FragmentFirsth1Page onCreateView");
        } catch (Exception e){

        }
        return view;
    }

    protected void initView(View view) {
        try {

            mPresenter = new CustomerMainPresenter();

            this.recyclerView = (RecyclerView) view.findViewById(R.id.rc_maincontent);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerDecoration spaceDecoration = new RecyclerDecoration(C.recyclerViewItemDepth);
            recyclerView.addItemDecoration(spaceDecoration);

            this.swipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            this.nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);

            txt_first1_page = (HTMLTextView)view.findViewById(R.id.txt_first1_page);
            sl_first1_page = (ScrollView)view.findViewById(R.id.sl_first1_page);
            btn_first1_page = (Button)view.findViewById(R.id.btn_first1_page);

            iv_first1_profile = (ImageView)view.findViewById(R.id.iv_first1_profile);
            iv_first1_fav = (ImageView)view.findViewById(R.id.iv_first1_fav);

            txt_first1_date = (TextView)view.findViewById(R.id.txt_first1_date);
            txt_first1_like_count = (TextView)view.findViewById(R.id.txt_first1_like_count);
            txt_first1_name = (TextView)view.findViewById(R.id.txt_first1_name);
            txt_first1_view_count = (TextView)view.findViewById(R.id.txt_first1_view_count);
            txt_first1_title = (TextView)view.findViewById(R.id.txt_first1_title);

            rl_first1_page = (RelativeLayout) view.findViewById(R.id.rl_first1_page);

            setProgress((ProgressBar)view.findViewById(R.id.progress));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {
        try {
            this.swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayoutDirection direction) {
                    if (direction == SwipyRefreshLayoutDirection.TOP) {
                        loadData(true);
                    } else {
                        loadData(false);
                    }
                }
            });

            PagingUtil.onRefreshForNested(nestedScrollView, recyclerView, swipeRefreshLayout, new PagingUtil.onPaging() {
                @Override
                public void onPaging() {
                    loadData(false);
                }
            });

            btn_first1_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    rl_first1_page.setVisibility(View.GONE);
                    C.backIndex = false;
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

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

//    @Override
//    public boolean onBackPressed() {
//        if(rl_first1_page.getVisibility() == View.VISIBLE){
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
//            rl_first1_page.setVisibility(View.GONE);
//            return true;
//        }
//
//        return false;
//    }

    public void loadData(boolean isFirst) {
        showProgress();
        mPresenter.loadList(isFirst, getActivity(), "contents01", new CommonCallback.SingleObjectCallback<ResultMarketConditionsDAO>() {
            @Override
            public void onSuccess(ResultMarketConditionsDAO result) {
                hideProgress();

                if (result.getList() != null && result.getList().size() > 0) {
                    if (isFirst) {
                        adapterMainContentList = new AdapterMainContentListToday(activity, result.getList(), "contents01", new AdapterMainContentListToday.onClickCallback() {
                            @Override
                            public void onClick(ResultMarketConditionsDAOList item, String contentType) {
                                showProgress();
                                mPresenter.getContentView(activity, contentType, item.getWrId(), new CommonCallback.SingleObjectCallback<GetContentsViewDAO>() {
                                    @Override
                                    public void onSuccess(GetContentsViewDAO result1) {
                                        hideProgress();
                                        for(int i = 0; i < adapterMainContentList.getData().size(); i++){
                                            if(adapterMainContentList.getData().get(i).getWrId().equals(result1.getWrId())){
                                                adapterMainContentList.getData().get(i).setWrHit(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrHit()) + 1));
                                                adapterMainContentList.notifyDataSetChanged();
                                            }
                                        }
                                        showContentView(result1);
                                    }

                                    @Override
                                    public void onFailed(String fault) {
                                        hideProgress();
                                        showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                                    }
                                });
                            }
                        }, new CustomerMainPresenter.FavClick() {
                            @Override
                            public void FavClick(ResultMarketConditionsDAOList item) {
                                showProgress();
                                mPresenter.setLike(activity, item.getWrId(), item.getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
                                    @Override
                                    public void onSuccess(SetLikeDAO result1) {
                                        hideProgress();
                                        String message = "";
                                        if (result1.getWrLike().toLowerCase().equals("n")) {
                                            message = getString(R.string.page1_fav_off);
                                        } else {
                                            message = getString(R.string.page1_fav_on);
                                        }

                                        for(int i = 0; i < adapterMainContentList.getData().size(); i++){
                                            if(adapterMainContentList.getData().get(i).getWrId().equals(item.getWrId())){
                                                adapterMainContentList.getData().get(i).setLikeCheck(result1.getWrLike());
                                                if (result1.getWrLike().toLowerCase().equals("n"))
                                                    adapterMainContentList.getData().get(i).setWrLike(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrLike()) - 1));
                                                else
                                                    adapterMainContentList.getData().get(i).setWrLike(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrLike()) + 1));
                                            }
                                        }
                                        adapterMainContentList.notifyDataSetChanged();
                                        showCustomAlert(activity, message, getString(R.string.page1_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
                                    }

                                    @Override
                                    public void onFailed(String fault) {
                                        hideProgress();
                                        showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(adapterMainContentList);

                        recyclerView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        adapterMainContentList.addAll(result.getList());
                        adapterMainContentList.notifyDataSetChanged();
                    }
                } else {
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void showContentView(GetContentsViewDAO result){
//        iv_first1_profile.setBackground(new ShapeDrawable(new OvalShape()));
//        iv_first1_profile.setClipToOutline(true);

//        Glide.with(activity).load(result.getWrFile()).asBitmap().into(iv_first1_profile);

        if (result.getLikeCheck().toLowerCase().equals("n"))
            iv_first1_fav.setBackgroundResource(R.drawable.fav_off);
        else
            iv_first1_fav.setBackgroundResource(R.drawable.fav_on);

        iv_first1_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setLike(activity, result.getWrId(), result.getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
                    @Override
                    public void onSuccess(SetLikeDAO result1) {
                        String message = "";
                        if (result1.getWrLike().toLowerCase().equals("n")) {
                            message = getString(R.string.page1_fav_off);
                            iv_first1_fav.setBackgroundResource(R.drawable.fav_off);
                        } else {
                            message = getString(R.string.page1_fav_on);
                            iv_first1_fav.setBackgroundResource(R.drawable.fav_on);
                        }

                        for(int i = 0; i < adapterMainContentList.getData().size(); i++){
                            if(adapterMainContentList.getData().get(i).getWrId().equals(result.getWrId())){
                                adapterMainContentList.getData().get(i).setLikeCheck(result1.getWrLike());
                                if (result1.getWrLike().toLowerCase().equals("n"))
                                    adapterMainContentList.getData().get(i).setWrLike(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrLike()) - 1));
                                else
                                    adapterMainContentList.getData().get(i).setWrLike(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrLike()) + 1));
                            }
                        }
                        adapterMainContentList.notifyDataSetChanged();
                        showCustomAlert(activity, message, getString(R.string.page1_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
                    }

                    @Override
                    public void onFailed(String fault) {
                        showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                    }
                });
            }
        });

        String[] arrStr = result.getWrDatetime().split(" ");

        if (arrStr.length == 2)
            txt_first1_date.setText(arrStr[0]);
        else
            txt_first1_date.setText(result.getWrDatetime());

        txt_first1_name.setText(result.getWrName());
        txt_first1_like_count.setText(result.getWrLike());
        txt_first1_view_count.setText(result.getWrHit());
        txt_first1_title.setText(result.getWrSubject());
        txt_first1_page.setHtmlText(result.getWrContent());

        swipeRefreshLayout.setVisibility(View.GONE);
        rl_first1_page.setVisibility(View.VISIBLE);

        C.backIndex = true;
    }

    @Override
    public void onBackPressed() {
        if(rl_first1_page.getVisibility() == View.VISIBLE){
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            rl_first1_page.setVisibility(View.GONE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    C.backIndex = false;
                }
            }, 100);
        }
    }

    //showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);

}

