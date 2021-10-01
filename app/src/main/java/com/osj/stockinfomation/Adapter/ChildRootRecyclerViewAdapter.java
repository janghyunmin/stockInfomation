package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DataModel.NewCategory01Model;
import com.osj.stockinfomation.DataModel.NewCategory02Model;
import com.osj.stockinfomation.DataModel.NewCategory03Model;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.BrowserActivity;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChildRootRecyclerViewAdapter extends RecyclerView.Adapter<ChildRootRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NewCategory03Model> newCategory03Model;
    public Context cxt;
    public Activity activity;

    public ChildRootRecyclerViewAdapter(ArrayList<NewCategory03Model> arrayList, Context mContext ,Activity activity) {
        this.cxt = mContext;
        this.newCategory03Model = arrayList;
        this.activity = activity;

    }


    @NonNull
    @Override
    public ChildRootRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_main_spot_content_row, parent, false);

        return new ChildRootRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildRootRecyclerViewAdapter.ViewHolder holder, int position) {
        try{

            holder.txt_spot_content_more.setTag(position);
            holder.txt_spot_content_title.setTag(position);
            DecimalFormat df2 = new DecimalFormat("#,###,##0");
            DecimalFormat df3 = new DecimalFormat("0.00");

            if(newCategory03Model.size()!=0){
                holder.txt_spot_content_title.setText(newCategory03Model.get(position).getCodeName());

                if(newCategory03Model.get(position).getChage()!= null){
                    if( newCategory03Model.get(position).getChage().toString().toLowerCase().equals("rise")){
                        holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(newCategory03Model.get(position).getDisplayedPrice())));
                        holder.txt_spot_content_per.setText(df3.format(newCategory03Model.get(position).getPer()) + "%");
                        holder.txt_spot_content_price.setTextColor(Color.parseColor("#C50000"));
                        holder.txt_spot_content_per.setTextColor(Color.parseColor("#C50000"));
                        holder.img_spot_content.setBackgroundResource(R.drawable.img_up_arrow);
                    } else if(newCategory03Model.get(position).getChage().toString().toLowerCase().equals("even")) {
                        holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(newCategory03Model.get(position).getDisplayedPrice())));
                        holder.txt_spot_content_per.setText(df3.format(newCategory03Model.get(position).getPer()) + "%");
                        holder.txt_spot_content_price.setTextColor(Color.parseColor("#000000"));
                        holder.txt_spot_content_per.setTextColor(Color.parseColor("#000000"));
                        holder.img_spot_content.setBackgroundResource(0);
                    } else {
                        holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(newCategory03Model.get(position).getDisplayedPrice())));
                        holder.txt_spot_content_per.setText(df3.format(newCategory03Model.get(position).getPer()) + "%");
                        holder.txt_spot_content_price.setTextColor(Color.parseColor("#0034AE"));
                        holder.txt_spot_content_per.setTextColor(Color.parseColor("#0034AE"));
                        holder.img_spot_content.setBackgroundResource(R.drawable.img_down_arrow);
                    }
                } else {
                    holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(newCategory03Model.get(position).getDisplayedPrice())));
                    holder.txt_spot_content_per.setText(df3.format(newCategory03Model.get(position).getPer()) + "%");
                    holder.txt_spot_content_price.setTextColor(Color.parseColor("#C50000"));
                    holder.txt_spot_content_per.setTextColor(Color.parseColor("#C50000"));
                    holder.img_spot_content.setBackgroundResource(R.drawable.img_up_arrow);
                }
            }else{
                holder.txt_spot_content_title.setText("데이터가 없습니다.");
            }


        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_spot_content_title;
        private TextView txt_spot_content_price;
        private TextView txt_spot_content_per;
        private TextView txt_spot_content_more;
        private ImageView img_spot_content;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_spot_content_title = (TextView)itemView.findViewById(R.id.txt_spot_content_title);
            txt_spot_content_price = (TextView)itemView.findViewById(R.id.txt_spot_content_price);
            txt_spot_content_per = (TextView)itemView.findViewById(R.id.txt_spot_content_per);
            txt_spot_content_more = (TextView)itemView.findViewById(R.id.txt_spot_content_more);
            img_spot_content = (ImageView)itemView.findViewById(R.id.img_spot_content);

            try {
//                iv_spot_content_star.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        spotCallBack.onFav((int)v.getTag());
//                    }
//                });



                txt_spot_content_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BrowserActivity.class);
                        intent.putExtra("data", newCategory03Model.get(getAdapterPosition()).getCode());
                        activity.startActivity(intent);
                        LogUtil.logE("소분류 아이템 더보기 click..");
                    }
                });

//                iv_spot_content_delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        spotCallBack.onDelete((int) view.getTag());
//                    }
//                });


            } catch (Exception e) {
                ErrorController.showError(e);
            }

        }
    }

    @Override
    public int getItemCount() {
        return newCategory03Model.size();
    }
}
