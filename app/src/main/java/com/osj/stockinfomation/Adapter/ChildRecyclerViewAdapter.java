package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.C.C;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.DataModel.NewCategory02Model;
import com.osj.stockinfomation.DataModel.NewCategory03Model;
import com.osj.stockinfomation.Dialog.Category03Dialog;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.BrowserActivity;
import com.osj.stockinfomation.util.LogUtil;

import java.util.ArrayList;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder> {
    public ArrayList<NewCategory02Model> childModelArrayList;
    public ArrayList<NewCategory03Model> childRootModelArrayList = new ArrayList<>();
    Context cxt;
    private Activity activity;
    public CustomerMainPresenter mPresenter;
    Category03Dialog category03Dialog;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView category2_name;
        public ConstraintLayout child_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            category2_name = itemView.findViewById(R.id.category2_name);
            child_layout = itemView.findViewById(R.id.child_layout);



        }
    }

    public ChildRecyclerViewAdapter(ArrayList<NewCategory02Model> arrayList, Context mContext,Activity activity) {
        this.cxt = mContext;
        this.childModelArrayList = arrayList;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_page3_row_child, parent, false);
        mPresenter = new CustomerMainPresenter();

        return new MyViewHolder(view);
    }
    private View.OnClickListener OkBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            category03Dialog.dismiss();
        }
    };

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewCategory02Model getItem = childModelArrayList.get(position);
        Glide.with(cxt).load(getItem.getIcon()).into(holder.icon);
        holder.category2_name.setText(getItem.getCategory02_name());

        //childRootModelArrayList.clear(); // 소분류 arraylist
        holder.child_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category03Dialog = new Category03Dialog(cxt,OkBtn,childRootModelArrayList,activity);
                mPresenter.getCategory03(activity, getItem.getCa_id2(), getItem.getCode(), new CommonCallback.SingleObjectCallback<SpotUpDAOCategory3>() {
                    @Override
                    public void onSuccess(SpotUpDAOCategory3 result) {
                        childRootModelArrayList.clear();
                        for(int index = 0; index < result.getList().size(); index++){
                            childRootModelArrayList.add(new NewCategory03Model(
                                    result.getList().get(index).getCaId3(),
                                    result.getList().get(index).getCa3Code(),
                                    result.getList().get(index).getCode(),
                                    result.getList().get(index).getCodeName(),
                                    result.getList().get(index).getAccTadeVolume(),
                                    result.getList().get(index).getChage(),
                                    result.getList().get(index).getChangePrice(),
                                    result.getList().get(index).getChangePriceRate(),
                                    result.getList().get(index).getDisplayedPrice(),
                                    result.getList().get(index).getLikeYn(),
                                    result.getList().get(index).getPer()
                            ));
                        }
                        category03Dialog.show();
                    }

                    @Override
                    public void onFailed(String fault) {
                        LogUtil.logE("fail.." + fault);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }


}
