package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterCategoryLikeFavContentList;
import com.osj.stockinfomation.Adapter.AdapterGridPage3Category2ContentList;
import com.osj.stockinfomation.Adapter.AdapterGridPage3ContentList;
import com.osj.stockinfomation.Adapter.AdapterMainContentList;
import com.osj.stockinfomation.Adapter.AdapterMainSpotContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage3ContentList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAO;
import com.osj.stockinfomation.DAO.GetContentsViewType2DAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.DAO.SpotUpDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory3;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.BrowserActivity;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.RecyclerDecoration;
import com.osj.stockinfomation.util.Spacing;
import com.osj.stockinfomation.util.SpacingGrid3;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentFirsth3Page extends BaseFragment {


    AdapterMainContentList adapterMainContentList;

    private CustomerMainPresenter mPresenter;
    private Activity activity;
    RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;
    YouTubePlayerView youtube;
    AlertDialog alertDialog = null;

    public FragmentFirsth3Page(Activity activity){
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
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            PagingUtil.onRefreshForNested(nestedScrollView, recyclerView, swipeRefreshLayout, new PagingUtil.onPaging() {
                @Override
                public void onPaging() {
                    loadData(false);
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBackPressed() {
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
        }

    }

    public void loadData(boolean isFirst) {
        showProgress();
        mPresenter.loadList(isFirst, getActivity(), "contents02", new CommonCallback.SingleObjectCallback<ResultMarketConditionsDAO>() {
            @Override
            public void onSuccess(ResultMarketConditionsDAO result) {
                hideProgress();

                if (result.getList() != null && result.getList().size() > 0) {
                    if (isFirst) {
                        adapterMainContentList = new AdapterMainContentList(activity, result.getList(), "contents02", new AdapterMainContentList.onClickCallback() {
                            @Override
                            public void onClick(ResultMarketConditionsDAOList item, String contentType) {
                                showProgress();
                                mPresenter.getContentViewType2(activity, contentType, item.getWrId(), new CommonCallback.SingleObjectCallback<GetContentsViewType2DAO>() {
                                    @Override
                                    public void onSuccess(GetContentsViewType2DAO result1) {
                                        hideProgress();
                                        for(int i = 0; i < adapterMainContentList.getData().size(); i++){
                                            LogUtil.logE("adapterMainContentList.getData().get(i).getWrHit() : " + adapterMainContentList.getData().get(i).getWrHit());
                                            if(adapterMainContentList.getData().get(i).getWrId().equals(result1.getWrId())){
                                                adapterMainContentList.getData().get(i).setWrHit(String.valueOf(Integer.parseInt(adapterMainContentList.getData().get(i).getWrHit()) + 1));
                                                adapterMainContentList.notifyDataSetChanged();
                                            }
                                        }

                                        showModalView(result1);
                                    }

                                    @Override
                                    public void onFailed(String fault) {
                                        hideProgress();
                                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
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
                                        showCustomAlert(activity, message , getString(R.string.page1_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
                                    }

                                    @Override
                                    public void onFailed(String fault) {
                                        hideProgress();
                                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(adapterMainContentList);
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



//        mPresenter.loadList(isFirst, getActivity(), "contents02", new CommonCallback.SingleObjectCallback<AdapterMainContentList>() {
//            @Override
//            public void onSuccess(AdapterMainContentList result) {
//                hideProgress();
//                if (result != null) {
//                    recyclerView.setAdapter(result);
//                }
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailed(String fault) {
//                hideProgress();
//                showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, new CustomerMainPresenter.RowClick() {
//            @Override
//            public void rowClick(ResultMarketConditionsDAOList item, String contentType) {
//                showProgress();
//                mPresenter.getContentViewType2(activity, contentType, item.getWrId(), new CommonCallback.SingleObjectCallback<GetContentsViewType2DAO>() {
//                    @Override
//                    public void onSuccess(GetContentsViewType2DAO result) {
//                        hideProgress();
//                        showModalView(result);
//                    }
//
//                    @Override
//                    public void onFailed(String fault) {
//                        hideProgress();
//                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
//                    }
//                });
//            }
//
//            @Override
//            public void rowCLick(ResultMarketConditionsDAOList item) {
//
//            }
//        }, new CustomerMainPresenter.FavClick() {
//            @Override
//            public void FavClick(ResultMarketConditionsDAOList item) {
//                showProgress();
//                mPresenter.setLike(activity, item.getWrId(), item.getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
//                    @Override
//                    public void onSuccess(SetLikeDAO result1) {
//                        hideProgress();
//                        String message = "";
//                        if (result1.getWrLike().toLowerCase().equals("n")) {
//                            message = getString(R.string.page1_fav_off);
//                        } else {
//                            message = getString(R.string.page1_fav_on);
//                        }
//
//                        mPresenter.adapterMainContentListNotifyDataSetChanged(item.getWrId(), result1.getWrLike());
//                        showCustomAlert(activity, message , getString(R.string.page1_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
//                    }
//
//                    @Override
//                    public void onFailed(String fault) {
//                        hideProgress();
//                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
//                    }
//                });
//            }
//        });
    }

    private void showModalView(GetContentsViewType2DAO result){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_category2, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        if(event.position == 12){
            loadData(true);
        }
    }

    //showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);

}
