package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import com.bumptech.glide.Glide;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterMyFavList;
import com.osj.stockinfomation.Adapter.AdapterPushList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetLikeDAO;
import com.osj.stockinfomation.DAO.GetLikeDetailDAO;
import com.osj.stockinfomation.DAO.GetPushDetailDAO;
import com.osj.stockinfomation.DAO.GetPushListDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
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

public class FragmentThrid extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;

    RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;

    RelativeLayout rl_fragment_sec_no_result;

    AdapterPushList adapter;
    AlertDialog alertDialog = null;

    public FragmentThrid(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);
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
        Log.d("osj", "eventbus subscribe 3");
        if(event.position == 3){
            Log.e("position 3 : ","");
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

            setProgress((ProgressBar)view.findViewById(R.id.progress));
            rl_fragment_sec_no_result.setVisibility(View.GONE);

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
        mPresenter.getPushList(activity, "free", new CommonCallback.SingleObjectCallback<GetPushListDAO>() {
            @Override
            public void onSuccess(GetPushListDAO result) {
                swipeRefreshLayout.setRefreshing(false);
                hideProgress();

                Log.e("size : ", String.valueOf(result.getList().size()));

                if(result.getList().size() != 0){
                    rl_fragment_sec_no_result.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new AdapterPushList(activity, result.getList(), "free", new AdapterPushList.SpotCallBack() {
                        @Override
                        public void onTitleClick(int position) {
                            showProgress();
                            mPresenter.getPushDetail(adapter.getData().get(position).getPuId(), "free", new CommonCallback.SingleObjectCallback<GetPushDetailDAO>() {
                                @Override
                                public void onSuccess(GetPushDetailDAO result) {
                                    hideProgress();
                                    showModalView(result);
                                }

                                @Override
                                public void onFailed(String fault) {
                                    hideProgress();
                                    showCustomAlert(activity, "", fault, true, R.drawable.img_alert_error, 1, "", "", null, null);
                                }
                            });
                        }

                        @Override
                        public void onDelete(int position) {
                            showCustomAlert(activity, "해당 메세지를 삭제하시겠습니까?", "해당 메시지가 목록에서 사라집니다.", true, R.drawable.img_alert_error, 2, "", "", null, new BaseActivity.OnClickListener() {
                                @Override
                                public void onClick() {
                                    showProgress();
                                    mPresenter.getPushDelete(activity, adapter.getData().get(position).getPuId(), "free", new CommonCallback.SingleObjectCallback<BaseDAO>() {
                                        @Override
                                        public void onSuccess(BaseDAO result) {
                                            hideProgress();

                                            adapter.getData().remove(position);
                                            adapter.notifyDataSetChanged();

                                            showCustomAlert(activity, "삭제되었습니다.", "", true, R.drawable.img_alert_ok, 1, "", "", null, null);

                                            if(adapter.getData().size() == 0)
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
                    });

                    recyclerView.setAdapter(adapter);
                } else {
                    rl_fragment_sec_no_result.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
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

    private void showModalView(GetPushDetailDAO result){
        C.backIndex = true;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_push_notice, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView txt_push_title = (TextView)alertDialog.findViewById(R.id.txt_push_title);
        TextView txt_push_date = (TextView)alertDialog.findViewById(R.id.txt_push_date);
        TextView txt_push_name = (TextView)alertDialog.findViewById(R.id.txt_push_name);
        HTMLTextView txt_push_content = (HTMLTextView)alertDialog.findViewById(R.id.txt_push_content);
        Button btn_push = (Button)alertDialog.findViewById(R.id.btn_push);

        txt_push_title.setText(result.getPuSubject());
        String[] str = result.getSendDatetime().split(" ");
        if(str.length == 2)
            txt_push_date.setText(str[0]);
        else
            txt_push_date.setText(result.getSendDatetime());

        txt_push_name.setText(result.getWrName());
        txt_push_content.setHtmlText(result.getPuContent());

        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                alertDialog = null;
                C.backIndex = false;

//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        C.backIndex = false;
//                    }
//                }, 100);
            }
        });

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
}
