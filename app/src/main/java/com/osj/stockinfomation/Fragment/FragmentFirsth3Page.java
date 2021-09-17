package com.osj.stockinfomation.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.osj.stockinfomation.Adapter.AdapterCategoryLikeFavContentList;
import com.osj.stockinfomation.Adapter.AdapterGridPage3Category2ContentList;
import com.osj.stockinfomation.Adapter.AdapterGridPage3ContentList;
import com.osj.stockinfomation.Adapter.AdapterMainSpotContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage3Cagegory2ContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage3ContentList;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAO;
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
import com.osj.stockinfomation.databinding.FragmentFirst3PageBinding;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.MessageEvent;
import com.osj.stockinfomation.util.Spacing;
import com.osj.stockinfomation.util.SpacingGrid3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentFirsth3Page extends BaseFragment {

    private CustomerMainPresenter mPresenter;
    private Activity activity;
    SpotUpDAO category1result;

    TextView tv_page3_tab1;
    TextView tv_page3_tab2;

    TextView txt_page3_subtitle1;
    TextView txt_page3_subtitle2;
    TextView txt_page3_middle;
    TextView txt_page3_middle1;

    RecyclerView gv_page3;
    RecyclerView gv_page3_category2;
    RecyclerView gv_page3_category22;
    RecyclerView rc_page3_tab2;

    String ca_id2 = null;
    NestedScrollView nestedScrollView;

    LinearLayout ll_page3_lately;
    RelativeLayout ll_page3_middle;
//    View view_page3_line;
    LinearLayout ll_page3_grid;
    LinearLayout ll_page3_tab1;
    RelativeLayout rl_fragment_first3_no_result;
    RelativeLayout rl_fragment_first3;

    AdapterMainpage3ContentList adapterGridPage3ContentList;
    AdapterMainpage3Cagegory2ContentList adapterMainpage3Cagegory2ContentList;
    AdapterMainpage3Cagegory2ContentList adapterMainpage3Cagegory22ContentList;
    AdapterCategoryLikeFavContentList adapterCategoryLikeFavContentList;

    LinearLayout ll_page3_grid_view_on_off;
    TextView txt_page3_grid_view_on_off;
    ImageView iv_page3_grid_view_on_off;

    AlertDialog alertDialog = null;

    public FragmentFirsth3Page(Activity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_3_page, container, false);
        initView(view);
        setEvent();
        SpotUpDAOList spotUpDAOList = new SpotUpDAOList();
        loadData(1, spotUpDAOList.getCaId(), "", "");
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

            tv_page3_tab1 = (TextView)view.findViewById(R.id.tv_page3_tab1);
            tv_page3_tab2 = (TextView)view.findViewById(R.id.tv_page3_tab2);

            txt_page3_subtitle1 = (TextView)view.findViewById(R.id.txt_page3_subtitle1);
            txt_page3_subtitle2 = (TextView)view.findViewById(R.id.txt_page3_subtitle2);
            txt_page3_middle = (TextView)view.findViewById(R.id.txt_page3_middle);
            txt_page3_middle1 = (TextView)view.findViewById(R.id.txt_page3_middle1);
            nestedScrollView = (NestedScrollView)view.findViewById(R.id.nestedScrollView);

            ll_page3_middle = (RelativeLayout) view.findViewById(R.id.ll_page3_middle);
            ll_page3_lately = (LinearLayout)view.findViewById(R.id.ll_page3_lately);
            ll_page3_grid = (LinearLayout)view.findViewById(R.id.ll_page3_grid);
            ll_page3_tab1 = (LinearLayout)view.findViewById(R.id.ll_page3_tab1);
            rl_fragment_first3_no_result = (RelativeLayout)view.findViewById(R.id.rl_fragment_first3_no_result);
            rl_fragment_first3 = (RelativeLayout)view.findViewById(R.id.rl_fragment_first3);

            //ll_page3_grid_view_on_off = (LinearLayout)view.findViewById(R.id.ll_page3_grid_view_on_off);
           // txt_page3_grid_view_on_off = (TextView)view.findViewById(R.id.txt_page3_grid_view_on_off);
           // iv_page3_grid_view_on_off = (ImageView)view.findViewById(R.id.iv_page3_grid_view_on_off);

            Spacing spaceDecoration = new Spacing(C.recyclerViewItemDepth, C.recyclerViewItemDepth);
            SpacingGrid3 spaceDecoration3 = new SpacingGrid3(C.recyclerViewItemDepth, C.recyclerViewItemDepth);

            gv_page3 = (RecyclerView)view.findViewById(R.id.gv_page3);
            this.gv_page3.setLayoutManager(new GridLayoutManager(getContext(), 3));
            gv_page3.addItemDecoration(spaceDecoration3);

            gv_page3_category2 = (RecyclerView)view.findViewById(R.id.gv_page3_category2);
            this.gv_page3_category2.setLayoutManager(new GridLayoutManager(getContext(), 3));
            gv_page3_category2.addItemDecoration(spaceDecoration3);

            gv_page3_category22 = (RecyclerView)view.findViewById(R.id.gv_page3_category22);
            this.gv_page3_category22.setLayoutManager(new GridLayoutManager(getContext(), 3));
            gv_page3_category22.addItemDecoration(spaceDecoration3);

            rc_page3_tab2 = (RecyclerView)view.findViewById(R.id.rc_page3_tab2);
            rc_page3_tab2.setLayoutManager(new LinearLayoutManager(getContext()));

//            view_page3_line = (View)view.findViewById(R.id.view_page3_line);

            setProgress((ProgressBar)view.findViewById(R.id.progress));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {
        try {
            tv_page3_tab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_page3_tab1.setTextColor(Color.parseColor("#000000"));
                    tv_page3_tab2.setTextColor(Color.parseColor("#999999"));

                    tv_page3_tab1.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_page3_tab2.setBackgroundColor(Color.parseColor("#F3F4F7"));

                    ll_page3_tab1.setVisibility(View.VISIBLE);
                    rl_fragment_first3.setVisibility(View.GONE);
                }
            });

            tv_page3_tab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_page3_tab1.setTextColor(Color.parseColor("#999999"));
                    tv_page3_tab2.setTextColor(Color.parseColor("#000000"));

                    tv_page3_tab1.setBackgroundColor(Color.parseColor("#F3F4F7"));
                    tv_page3_tab2.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    ll_page3_tab1.setVisibility(View.GONE);
                    rl_fragment_first3.setVisibility(View.VISIBLE);

                    showProgress();
                    mPresenter.getCategoryLike(activity, new CommonCallback.SingleObjectCallback<GetCategoryLikeFavDAO>() {
                        @Override
                        public void onSuccess(GetCategoryLikeFavDAO result) {
                            hideProgress();

                            if(result.getList().size() == 0)
                                rl_fragment_first3_no_result.setVisibility(View.VISIBLE);
                            else
                                rl_fragment_first3_no_result.setVisibility(View.GONE);

                            adapterCategoryLikeFavContentList = new AdapterCategoryLikeFavContentList(activity, result.getList(), "Like", new AdapterCategoryLikeFavContentList.SpotCallBack() {
                                @Override
                                public void onMore(int position) {
                                    Intent intent = new Intent(activity, BrowserActivity.class);
                                    intent.putExtra("data", adapterCategoryLikeFavContentList.getData().get(position).getCode());
                                    intent.putExtra("openType", "search");

                                    startActivity(intent);
                                }

                                @Override
                                public void onTitleClick(String title) {
                                    Intent intent = new Intent(activity, BrowserActivity.class);
                                    intent.putExtra("data", title);
                                    intent.putExtra("openType", "data");
                                    startActivity(intent);
                                }

                                @Override
                                public void onDelete(int position) {
                                    showCustomAlert(activity, "즐겨찾기를 해제하시겠습니까?", "즐겨찾기된 컨텐츠가 목록에서 사라집니다.", true, R.drawable.img_alert_error, 2, "", "", null, new BaseActivity.OnClickListener() {
                                        @Override
                                        public void onClick() {
                                            mPresenter.setCategoryLike(activity, adapterCategoryLikeFavContentList.getData().get(position).getCaId2(), adapterCategoryLikeFavContentList.getData().get(position).getCaId3(), new CommonCallback.SingleObjectCallback<SetCategoryLikeDAO>() {
                                                @Override
                                                public void onSuccess(SetCategoryLikeDAO result1) {
                                                    hideProgress();
                                                    adapterCategoryLikeFavContentList.getData().remove(position);
                                                    adapterCategoryLikeFavContentList.notifyDataSetChanged();

                                                    String message = getString(R.string.page1_fav_off);
                                                    showCustomAlert(activity, message, "", true, R.drawable.img_alert_ok, 1, "", "", null, null);

                                                    if(adapterCategoryLikeFavContentList.getData().size() == 0)
                                                        rl_fragment_first3_no_result.setVisibility(View.VISIBLE);
                                                    else
                                                        rl_fragment_first3_no_result.setVisibility(View.GONE);
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
                            rc_page3_tab2.setAdapter(adapterCategoryLikeFavContentList);

                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                        }
                    });
                }
            });

            txt_page3_subtitle1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txt_page3_subtitle1.setTextColor(Color.parseColor("#C50000"));
                    txt_page3_subtitle2.setTextColor(Color.parseColor("#999999"));

//                    ll_page3_middle.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.VISIBLE);
                    ll_page3_lately.setVisibility(View.GONE);
                }
            });

            txt_page3_subtitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txt_page3_subtitle1.setTextColor(Color.parseColor("#999999"));
                    txt_page3_subtitle2.setTextColor(Color.parseColor("#C50000"));

//                    ll_page3_middle.setVisibility(View.GONE);
                    nestedScrollView.setVisibility(View.GONE);
                    ll_page3_lately.setVisibility(View.VISIBLE);

                    showProgress();
                    mPresenter.getLatelyCategory(activity, new CommonCallback.SingleObjectCallback<SpotUpDAOCategory2>() {
                        @Override
                        public void onSuccess(SpotUpDAOCategory2 result) {
                            hideProgress();
                            txt_page3_middle1.setVisibility(View.VISIBLE);

                            if (result != null) {
                                adapterMainpage3Cagegory22ContentList = new AdapterMainpage3Cagegory2ContentList(activity, result.getList(), item -> {
                                    loadData(3, item.getCaId(), item.getCode(), item.getCodeName());
                                    ca_id2 = item.getCaId();
                                });

                                gv_page3_category22.setAdapter(adapterMainpage3Cagegory22ContentList);
                            }
                        }

                        @Override
                        public void onFailed(String fault) {
                            hideProgress();
                            showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                        }
                    });
                }
            });

//            ll_page3_grid_view_on_off.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(txt_page3_grid_view_on_off.getText().equals("펼쳐보기")){
//                        adapterGridPage3ContentList.setData(category1result.getList());
//                        adapterGridPage3ContentList.notifyDataSetChanged();
//
//                        txt_page3_grid_view_on_off.setText("접기");
//                        iv_page3_grid_view_on_off.setBackgroundResource(R.drawable.img_grid_full_down);
//                    } else {
//                        List<SpotUpDAOList> tempList = new ArrayList<>();
//
//                        for(int i = 0; i < category1result.getList().size(); i++){
//                            if(i < 6)
//                                tempList.add(category1result.getList().get(i));
//                            else break;
//                        }
//
//                        adapterGridPage3ContentList.setData(tempList);
//                        adapterGridPage3ContentList.notifyDataSetChanged();
//
//                        txt_page3_grid_view_on_off.setText("펼쳐보기");
//                        iv_page3_grid_view_on_off.setBackgroundResource(R.drawable.img_grid_full);
//                    }
//                }
//            });

//            txt_page3_grid_view_on_off.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(txt_page3_grid_view_on_off.getText().equals("펼쳐보기")){
//                        adapterGridPage3ContentList.setData(category1result.getList());
//                        adapterGridPage3ContentList.notifyDataSetChanged();
//
//                        txt_page3_grid_view_on_off.setText("접기");
//                        iv_page3_grid_view_on_off.setBackgroundResource(R.drawable.img_grid_full_down);
//                    } else {
//                        List<SpotUpDAOList> tempList = new ArrayList<>();
//
//                        for(int i = 0; i < category1result.getList().size(); i++){
//                            if(i < 6)
//                                tempList.add(category1result.getList().get(i));
//                            else break;
//                        }
//
//                        adapterGridPage3ContentList.setData(tempList);
//                        adapterGridPage3ContentList.notifyDataSetChanged();
//
//                        txt_page3_grid_view_on_off.setText("펼쳐보기");
//                        iv_page3_grid_view_on_off.setBackgroundResource(R.drawable.img_grid_full);
//                    }
//                }
//            });

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

    public void loadData(int isFirst, String ca_id, String code, String code_Name) {
        switch (isFirst){
            case 1:
                showProgress();
                txt_page3_subtitle1.setTextColor(Color.parseColor("#C50000"));
                txt_page3_subtitle2.setTextColor(Color.parseColor("#999999"));
                ll_page3_middle.setVisibility(View.VISIBLE);
                ll_page3_lately.setVisibility(View.GONE);
                ll_page3_grid.setVisibility(View.GONE);
                tv_page3_tab1.setTextColor(Color.parseColor("#000000"));
                tv_page3_tab2.setTextColor(Color.parseColor("#999999"));
                tv_page3_tab1.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_page3_tab2.setBackgroundColor(Color.parseColor("#F3F4F7"));
                ll_page3_tab1.setVisibility(View.VISIBLE);
                rl_fragment_first3.setVisibility(View.GONE);
                mPresenter.getCategory01(Util_osj.getAndroidId(activity), new CommonCallback.SingleObjectCallback<SpotUpDAO>() {
                    @Override
                    public void onSuccess(SpotUpDAO result) {
                        hideProgress();
                        category1result = result;

                        LogUtil.logE("result " + result.getList().size());
                        List<SpotUpDAOList> tempList = new ArrayList<>();
                        for(int i = 0; i < result.getList().size(); i++){
                              tempList.add(result.getList().get(i));
                            }
                        adapterGridPage3ContentList = new AdapterMainpage3ContentList(activity, tempList, new AdapterMainpage3ContentList.onClickCallback() {
                            @Override
                            public void onClick(SpotUpDAOList item) {
                                loadData(2, item.getCaId(), item.getCode(),"");
                                ll_page3_grid.setVisibility(View.VISIBLE);
                                txt_page3_middle.setText(item.getCodeName());
                            }
                        });
                        gv_page3.setAdapter(adapterGridPage3ContentList);
//                        if(result.getList().size() > 6){
//                            ll_page3_grid_view_on_off.setVisibility(View.VISIBLE);
//                            txt_page3_grid_view_on_off.setText("펼쳐보기");
//                            iv_page3_grid_view_on_off.setBackgroundResource(R.drawable.img_grid_full);
//
//                            List<SpotUpDAOList> tempList = new ArrayList<>();
//
//                            for(int i = 0; i < result.getList().size(); i++){
//                                if(i < 6)
//                                    tempList.add(result.getList().get(i));
//                                else break;
//                            }
//
//                            adapterGridPage3ContentList = new AdapterMainpage3ContentList(activity, tempList, new AdapterMainpage3ContentList.onClickCallback() {
//                                @Override
//                                public void onClick(SpotUpDAOList item) {
//
//                                    loadData(2, item.getCaId(), item.getCode(),"");
////                                    txt_page3_middle.setVisibility(View.VISIBLE);
//                                    ll_page3_grid.setVisibility(View.VISIBLE);
//                                    txt_page3_middle.setText(item.getCodeName());
//                                }
//                            });
//                        } else {
//                            ll_page3_grid_view_on_off.setVisibility(View.GONE);
//
//                            adapterGridPage3ContentList = new AdapterMainpage3ContentList(activity, result.getList(), new AdapterMainpage3ContentList.onClickCallback() {
//                                @Override
//                                public void onClick(SpotUpDAOList item) {
//
//                                    loadData(2, item.getCaId(), item.getCode(),"");
////                                    txt_page3_middle.setVisibility(View.VISIBLE);
//                                    ll_page3_grid.setVisibility(View.VISIBLE);
//                                    txt_page3_middle.setText(item.getCodeName());
//                                }
//                            });
//                        }
//                        gv_page3.setAdapter(adapterGridPage3ContentList);
                    }

                    @Override
                    public void onFailed(String fault) {
                        hideProgress();
                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                    }
                });
                break;
            case 2:
                showProgress();
                mPresenter.getCategory02(ca_id, code, new CommonCallback.SingleObjectCallback<SpotUpDAOCategory2>() {
                    @Override
                    public void onSuccess(SpotUpDAOCategory2 result) {
                        hideProgress();
                        if(result.getList().size() == 0){
//                            txt_page3_middle.setVisibility(View.GONE);
//                            view_page3_line.setVisibility(View.GONE);
                            ll_page3_grid.setVisibility(View.GONE);
                        }
                        if (result != null) {
                            adapterMainpage3Cagegory2ContentList = new AdapterMainpage3Cagegory2ContentList(activity, result.getList(), new AdapterMainpage3Cagegory2ContentList.onClickCallback() {
                                @Override
                                public void onClick(SpotUpDAOListCategory2 item) {
                                    loadData(3, item.getCaId(), item.getCode(), item.getCodeName());
                                    ca_id2 = item.getCaId();
                                }
                            });

                            gv_page3_category2.setAdapter(adapterMainpage3Cagegory2ContentList);

                            nestedScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    nestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(String fault) {
                        hideProgress();
                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                    }
                });
                break;
            case 3:
                showProgress();
                mPresenter.getCategory03(activity, ca_id, code, new CommonCallback.SingleObjectCallback<SpotUpDAOCategory3>() {
                    @Override
                    public void onSuccess(SpotUpDAOCategory3 result) {
                        hideProgress();
                        showModalView(result, code_Name);
                    }

                    @Override
                    public void onFailed(String fault) {
                        hideProgress();
                        showCustomAlert(activity, "", fault, true , R.drawable.img_alert_error, 1, "", "" , null, null);
                    }
                });
                break;
        }
    }

    AdapterMainSpotContentList adapterMainSpotContentList;
    RecyclerView recyclerView;

    private void showModalView(SpotUpDAOCategory3 result, String code_Name){
        C.backIndex = true;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_category3, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        ((TextView)alertDialog.findViewById(R.id.txt_category3)).setText(code_Name);

        ((ImageView)alertDialog.findViewById(R.id.iv_category3_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                C.backIndex = false;
            }
        });

        recyclerView = (RecyclerView)alertDialog.findViewById(R.id.rc_category3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMainSpotContentList = new AdapterMainSpotContentList(activity, result.getList(), "Modal", new AdapterMainSpotContentList.SpotCallBack() {
            @Override
            public void onMore(int position) {
                Intent intent = new Intent(activity, BrowserActivity.class);
                intent.putExtra("data", adapterMainSpotContentList.getData().get(position).getCode());
                intent.putExtra("openType", "search");

                startActivity(intent);
            }

            @Override
            public void onTitleClick(String title) {
                Intent intent = new Intent(activity, BrowserActivity.class);
                intent.putExtra("data", title);
                intent.putExtra("openType", "data");
                startActivity(intent);
            }

            @Override
            public void onFav(int position) {
                showProgress();
                mPresenter.setCategoryLike(activity, ca_id2, result.getList().get(position).getCaId3(), new CommonCallback.SingleObjectCallback<SetCategoryLikeDAO>() {
                    @Override
                    public void onSuccess(SetCategoryLikeDAO result1) {
                        hideProgress();
                        adapterMainSpotContentList.getData().get(position).setLikeYn(result1.getWrLike());
                        adapterMainSpotContentList.notifyDataSetChanged();

                        String message;
                        if(result1.getWrLike().toLowerCase().equals("n"))
                            message = getString(R.string.page3_fav_off_title);
                        else
                            message = getString(R.string.page3_fav_on_title);

                        showCustomAlert(activity, message, getString(R.string.page3_fav_subtitle), false, R.drawable.img_alert_error, 1, "", "", null, null);
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

            }
        });
        recyclerView.setAdapter(adapterMainSpotContentList);
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
            loadData(1, spotUpDAOList.getCaId(), "", "");
        }
    }
}
