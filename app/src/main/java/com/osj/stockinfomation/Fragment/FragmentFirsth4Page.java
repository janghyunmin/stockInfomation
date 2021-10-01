package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterCategoryLikeFavContentList;
import com.osj.stockinfomation.Adapter.AdapterMainSpotContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage3Cagegory2ContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage3ContentList;
import com.osj.stockinfomation.Adapter.ParentRecyclerViewAdapter;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAO;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.DAO.SpotUpDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory2;
import com.osj.stockinfomation.DataModel.NewCategory01Model;
import com.osj.stockinfomation.DataModel.NewCategory02Model;
import com.osj.stockinfomation.DataModel.NewCategory03Model;
import com.osj.stockinfomation.Dialog.Category03Dialog;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.BrowserActivity;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.Spacing;
import com.osj.stockinfomation.util.SpacingGrid3;
import com.osj.stockinfomation.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FragmentFirsth4Page extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;
    SpotUpDAO category1result;


    AlertDialog alertDialog = null;
    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ArrayList<NewCategory01Model> parentModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager parentLayoutManager;
    private SwipyRefreshLayout swipeRefreshLayout;

    SearchView search_layout;


    public FragmentFirsth4Page(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_3page, container, false);
        initView(view);
        setEvent();


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
            this.swipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            this.search_layout = (SearchView) view.findViewById(R.id.search_layout);


            mPresenter.getCategory01(Util_osj.getAndroidId(activity), new CommonCallback.SingleObjectCallback<SpotUpDAO>() {
                @Override
                public void onSuccess(SpotUpDAO result) {
                    hideProgress();
                    category1result = result;


                    parentModelArrayList.clear();
                    for(int index = 0; index < result.getList().size(); index++){
                        parentModelArrayList.add(new NewCategory01Model(
                                result.getList().get(index).getCaId(),
                                result.getList().get(index).getCode(),
                                result.getList().get(index).getCodeName()));
                    }
                    parentRecyclerView = view.findViewById(R.id.rc_maincontent);
                    parentRecyclerView.setHasFixedSize(true);
                    parentLayoutManager = new LinearLayoutManager(getContext());
                    ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, getContext() , activity);
                    parentRecyclerView.setLayoutManager(parentLayoutManager);
                    parentRecyclerView.setAdapter(ParentAdapter);
                    ParentAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailed(String fault) {
                    hideProgress();
                    showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                }
            });


//            view_page3_line = (View)view.findViewById(R.id.view_page3_line);

            setProgress((ProgressBar)view.findViewById(R.id.progress));

            swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayoutDirection direction) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });


            // below line is to call set on query text listener method.
            search_layout.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // inside on query text change method we are
                    // calling a method to filter our recycler view.
                    filter(newText);
                    return false;
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<NewCategory01Model> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (NewCategory01Model item : parentModelArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCodeName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            LogUtil.logE("검색결과 없음.");
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            ParentAdapter = new ParentRecyclerViewAdapter(filteredlist, getContext() , activity);
            parentRecyclerView.setLayoutManager(parentLayoutManager);
            parentRecyclerView.setAdapter(ParentAdapter);
            ParentAdapter.notifyDataSetChanged();
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
        if(event.position == 13){
            SpotUpDAOList spotUpDAOList = new SpotUpDAOList();

        }
    }

    @Override
    public void onBackPressed() {

    }
}
