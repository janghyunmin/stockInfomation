package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.content.pm.PackageItemInfo;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterMyFavList;
import com.osj.stockinfomation.Adapter.VpAdapterFirst;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetContentsViewDAO;
import com.osj.stockinfomation.DAO.GetLikeDAO;
import com.osj.stockinfomation.DAO.GetLikeDetailDAO;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.Http.NSCallback;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.HTMLTextView;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.RecyclerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentSec extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;

    RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;

    RelativeLayout rl_fragment_sec_no_result;

    AdapterMyFavList adapter;

    private HTMLTextView txt_first1_page;
    private ScrollView sl_first1_page;
    private Button btn_first1_page;
    private RelativeLayout rl_first1_page;

    private TextView txt_first1_date;
    private TextView txt_first1_name;
    private TextView txt_first1_view_count;
    private TextView txt_first1_like_count;
    private TextView txt_first1_title;

    public FragmentSec(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sec, container, false);
        initView(view);
        setEvent();
        loadData(true);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e){

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
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e){

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event.position == 2){
            loadData(true);
        }
    }

    protected void initView(View view) {
        try {
            mPresenter = new CustomerMainPresenter();

            this.recyclerView = (RecyclerView) view.findViewById(R.id.rc_maincontent);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            rl_fragment_sec_no_result = (RelativeLayout)view.findViewById(R.id.rl_fragment_sec_no_result);
            RecyclerDecoration spaceDecoration = new RecyclerDecoration(C.recyclerViewItemDepth);
            recyclerView.addItemDecoration(spaceDecoration);

            this.swipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            this.nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);

            txt_first1_page = (HTMLTextView)view.findViewById(R.id.txt_first1_page);
            sl_first1_page = (ScrollView)view.findViewById(R.id.sl_first1_page);
            btn_first1_page = (Button)view.findViewById(R.id.btn_first1_page);

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
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public void loadData(boolean isFirst) {

        showProgress();
        mPresenter.getLike(activity, new CommonCallback.SingleObjectCallback<GetLikeDAO>() {
            @Override
            public void onSuccess(GetLikeDAO result) {
                swipeRefreshLayout.setRefreshing(false);
                hideProgress();
                if(result.getList().size() != 0){
                    rl_fragment_sec_no_result.setVisibility(View.GONE);
                    adapter = new AdapterMyFavList(activity, result.getList(), "like", new AdapterMyFavList.SpotCallBack() {
                        @Override
                        public void onTitleClick(int position) {
                            showProgress();
                            mPresenter.getLikeDetail(activity, adapter.getData().get(position).getWrId(), adapter.getData().get(position).getBoTable(), new CommonCallback.SingleObjectCallback<GetLikeDetailDAO>() {
                                @Override
                                public void onSuccess(GetLikeDetailDAO result) {
                                    hideProgress();
                                    showContentView(result);

                                }

                                @Override
                                public void onFailed(String fault) {
                                    hideProgress();
                                    showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                                }
                            });
                        }

                        @Override
                        public void onFav(int position) {
                            setLike(result, position);
//                            showProgress();
//                            mPresenter.setLike(activity, result.getList().get(position).getWrId(), result.getList().get(position).getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
//                                @Override
//                                public void onSuccess(SetLikeDAO result1) {
//                                    hideProgress();
//
//                                    result.getList().get(position).setLikeCheck(result1.getWrLike());
//                                    adapter.notifyDataSetChanged();
//
//                                    String message;
//                                    if(result1.getWrLike().toLowerCase().equals("n"))
//                                        message = getString(R.string.page3_fav_off_title);
//                                    else
//                                        message = getString(R.string.page3_fav_on_title);
//
//                                    showCustomAlert(activity, message, getString(R.string.page3_fav_subtitle), true, R.drawable.img_alert_ok, 1, "", "", null, null);
//                                }
//
//                                @Override
//                                public void onFailed(String fault) {
//                                    hideProgress();
//                                    showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
//                                }
//                            });
                        }

                        @Override
                        public void onDelete(int position) {
                            setLike(result, position);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                } else {
                    rl_fragment_sec_no_result.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(String fault) {
                swipeRefreshLayout.setRefreshing(false);
                hideProgress();
                showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
            }
        });
    }

    public void setLike(GetLikeDAO result, int position){
        showCustomAlert(activity, "", "즐겨찾기를 삭제하시겠습니까?", true, R.drawable.img_alert_error, 2, "", "", null, new BaseActivity.OnClickListener() {
            @Override
            public void onClick() {
                showProgress();
                mPresenter.setLike(activity, result.getList().get(position).getWrId(), result.getList().get(position).getBoTable(), new CommonCallback.SingleObjectCallback<SetLikeDAO>() {
                    @Override
                    public void onSuccess(SetLikeDAO result1) {
                        hideProgress();

                        result.getList().remove(position);
                        adapter.notifyDataSetChanged();

                        String message;
                        if (result1.getWrLike().toLowerCase().equals("n"))
                            message = getString(R.string.page3_fav_off_title);
                        else
                            message = getString(R.string.page3_fav_on_title);

                        showCustomAlert(activity, message, getString(R.string.page3_fav_subtitle), true, R.drawable.img_alert_ok, 1, "", "", null, null);

                        if (adapter.getData().size() == 0)
                            rl_fragment_sec_no_result.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailed(String fault) {
                        hideProgress();
                        showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                    }
                });
            }
        });
    }

    public void showContentView(GetLikeDetailDAO result){
        C.backIndex = true;
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

        btn_first1_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                rl_first1_page.setVisibility(View.GONE);
            }
        });

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
}
