package com.osj.stockinfomation.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.DataModel.HotList;
import com.osj.stockinfomation.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterHotMain extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<HotList> hotList = new ArrayList<>();

    public AdapterHotMain(ArrayList<HotList> hotList , Context context){
        this.hotList = hotList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_rv_item,parent,false);
        return new AdapterHotMain.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){

            ((ViewHolder) holder).num.setTextColor(context.getColor(R.color.white));
            ((ViewHolder) holder).name.setTextColor(context.getColor(R.color.white));

            switch (hotList.get(position).getCmpprevddTpCd()){
                // 상승 case
                case "1" :
                case "2" :
                case "6" :
                case "7" :
                    Glide.with(context).load(R.drawable.arrow_custom_1).into(((ViewHolder) holder).tpCd);
                    ((ViewHolder) holder).cmpprevddPrc.setTextColor(context.getColor(R.color.c_red));
                    break;

                    // 보합 case
                case "3":
                    Glide.with(context).load(R.drawable.minus_white).into(((ViewHolder) holder).tpCd);
                    ((ViewHolder) holder).cmpprevddPrc.setTextColor(context.getColor(R.color.white));
                    break;

                case "4" :
                case "5" :
                case "8" :
                case "9" :
                    Glide.with(context).load(R.drawable.arrow_custom_2).into(((ViewHolder) holder).tpCd);
                    ((ViewHolder) holder).cmpprevddPrc.setTextColor(context.getColor(R.color.c_blue));
                    break;

            }


            ((ViewHolder) holder).num.setText(String.valueOf(hotList.get(position).getIdex()));
            switch (hotList.get(position).getCode()){
                case "005930": // 삼성전자
                    ((ViewHolder) holder).name.setText("삼성전자");
                    break;
                case "035720" : // 카카오
                    ((ViewHolder) holder).name.setText("카카오");
                    break;
                case "035420": // 네이버
                    ((ViewHolder) holder).name.setText("네이버");
                    break;
                case "066570": // LG전자
                    ((ViewHolder) holder).name.setText("LG전자");
                    break;
                case "036570": // 엔씨소프트
                    ((ViewHolder) holder).name.setText("NC소프트");
                    break;
                    
            }

            DecimalFormat df2 = new DecimalFormat("#,###,##0");

            ((ViewHolder) holder).price.setTextColor(context.getColor(R.color.white));
            ((ViewHolder) holder).price.setText(df2.format(Integer.parseInt(String.valueOf(hotList.get(position).getTrdPrc()))));
            ((ViewHolder) holder).cmpprevddPrc.setText(df2.format(Integer.parseInt(String.valueOf(hotList.get(position).getCmpprevddPrc()))));


        }
    }

    @Override
    public int getItemCount() {
        return hotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView num , name , price , cmpprevddPrc;
        ImageView tpCd;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num = (TextView) itemView.findViewById(R.id.num);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            tpCd = (ImageView) itemView.findViewById(R.id.tpCd);
            cmpprevddPrc = (TextView) itemView.findViewById(R.id.cmpprevddPrc);


        }
    }
}
