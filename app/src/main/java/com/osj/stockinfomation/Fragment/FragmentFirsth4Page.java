package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterMainpage4ContentList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOList;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.Spacing;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentFirsth4Page extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;
    //GridView gridView;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;
    AlertDialog alertDialog = null;

    public FragmentFirsth4Page(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_4_page, container, false);
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

            //this.gridView = (GridView) view.findViewById(R.id.gv_maincontent);
            this.recyclerView = (RecyclerView)view.findViewById(R.id.rc_maincontent);
            this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            Spacing spaceDecoration = new Spacing(C.recyclerViewItemDepth, C.recyclerViewItemDepth);
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

    @Override
    public void onBackPressed() {
        if(alertDialog != null){
            alertDialog.dismiss();
            alertDialog = null;
            C.backIndex = false;

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    C.backIndex = false;
//                }
//            }, 100);
        }
    }

    public void loadData(boolean isFirst) {
        showProgress();
        mPresenter.loadList1(isFirst, getActivity(), "contents03", new CommonCallback.SingleObjectCallback<AdapterMainpage4ContentList>() {
            @Override
            public void onSuccess(AdapterMainpage4ContentList result) {
                hideProgress();
                if (result != null) {
                    recyclerView.setAdapter(result);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailed(String fault) {
                hideProgress();
                showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new CustomerMainPresenter.RowClick() {
            @Override
            public void rowClick(ResultMarketConditionsDAOList item, String contentType) {

            }

            @Override
            public void rowCLick(ResultMarketConditionsDAOList item) {
                showModalView(item);
            }
        });
    }

    private void showModalView(ResultMarketConditionsDAOList result){
        C.backIndex = true;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_category4, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        ImageView iv = (ImageView)alertDialog.findViewById(R.id.iv_category4);
        Glide.with(activity).load(result.getWrFile()).asBitmap().into(iv);
    }
    //showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);

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
        if(event.position == 14){
            Log.d("osj", "first1page 14 load date");
            SpotUpDAOList spotUpDAOList = new SpotUpDAOList();
            loadData(true);
        }
    }

}
