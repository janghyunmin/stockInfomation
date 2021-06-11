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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.osj.stockinfomation.Adapter.AdapterPushList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetPushDetailDAO;
import com.osj.stockinfomation.DAO.GetPushListDAO;
import com.osj.stockinfomation.Http.NSCallback;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.InquiryActivity;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.base.BaseActivity;
import com.osj.stockinfomation.base.BaseFragment;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.HTMLTextView;
import com.osj.stockinfomation.util.PagingUtil;
import com.osj.stockinfomation.util.RecyclerDecoration;

public class FragmentFour extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;

    RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;
    private SwipyRefreshLayout swipeRefreshLayout;

    RelativeLayout rl_fragment_sec_no_result;

    public FragmentFour(Activity activity){
        this.activity = activity;
    }

    RelativeLayout rl1;
    RelativeLayout rl2;

    RelativeLayout rl_fragment4;
    RelativeLayout rl_fragment4_list;

    AdapterPushList adapter;
    Button btn_fragment4_back;
    AlertDialog alertDialog = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four, container, false);
        initView(view);
        setEvent();
        return view;
    }

    protected void initView(View view) {
        try {
            rl1 = (RelativeLayout)view.findViewById(R.id.rl1);
            rl2 = (RelativeLayout)view.findViewById(R.id.rl2);

            rl_fragment4 = (RelativeLayout)view.findViewById(R.id.rl_fragment4);
            rl_fragment4_list = (RelativeLayout)view.findViewById(R.id.rl_fragment4_list);

            mPresenter = new CustomerMainPresenter();

            this.recyclerView = (RecyclerView) view.findViewById(R.id.rc_maincontent);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            RecyclerDecoration spaceDecoration = new RecyclerDecoration(C.recyclerViewItemDepth);
            recyclerView.addItemDecoration(spaceDecoration);

            rl_fragment_sec_no_result = (RelativeLayout)view.findViewById(R.id.rl_fragment_sec_no_result);
            this.swipeRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            this.nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);

            btn_fragment4_back = (Button)view.findViewById(R.id.btn_fragment4_back);

            setProgress((ProgressBar)view.findViewById(R.id.progress));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {
        try {
            rl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rl_fragment4.setVisibility(View.GONE);
                    rl_fragment4_list.setVisibility(View.VISIBLE);

                    C.backIndex = true;

                    loadData(true);
                }
            });

            rl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), InquiryActivity.class);
                    startActivity(intent);
                }
            });

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

            btn_fragment4_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rl_fragment4.setVisibility(View.VISIBLE);
                    rl_fragment4_list.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void loadData(boolean isFirst) {
        showProgress();
        mPresenter.getPushList(activity, "notice", new CommonCallback.SingleObjectCallback<GetPushListDAO>() {
            @Override
            public void onSuccess(GetPushListDAO result) {
                swipeRefreshLayout.setRefreshing(false);
                hideProgress();

                if(result.getList().size() != 0){
                    rl_fragment_sec_no_result.setVisibility(View.GONE);
                    adapter = new AdapterPushList(activity, result.getList(), "notice", new AdapterPushList.SpotCallBack() {
                        @Override
                        public void onTitleClick(int position) {
                            showProgress();
                            mPresenter.getPushDetail(adapter.getData().get(position).getPuId(), "notice", new CommonCallback.SingleObjectCallback<GetPushDetailDAO>() {
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
                                    mPresenter.getPushDelete(activity, adapter.getData().get(position).getPuId(), "notice", new CommonCallback.SingleObjectCallback<BaseDAO>() {
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
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alertDialog = null;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(alertDialog != null){
            alertDialog.dismiss();
            alertDialog = null;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    C.backIndex = false;
                }
            }, 100);
        } else if(rl_fragment4_list.getVisibility() == View.VISIBLE){
            rl_fragment4.setVisibility(View.VISIBLE);
            rl_fragment4_list.setVisibility(View.GONE);

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
