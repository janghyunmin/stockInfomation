package com.osj.stockinfomation.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DAO.ProfitDAO;
import com.osj.stockinfomation.DataModel.ProfitList;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.LogUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonthItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ArrayList<ProfitList> profitList;

    public MonthItemAdapter(ArrayList<ProfitList> profitList, Context context) {
        this.profitList = profitList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_month_item,parent,false);
        return new MonthItemAdapter.MonthItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MonthItemViewHolder){
            if(profitList.get(position).getM_status().equals("매수")){
                ((MonthItemViewHolder) holder).m_status.setText("매수");
                ((MonthItemViewHolder) holder).m_status.setBackgroundColor(Color.parseColor("#c50000"));
                ((MonthItemViewHolder) holder).m_status.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                ((MonthItemViewHolder) holder).m_status.setText("매도");
                ((MonthItemViewHolder) holder).m_status.setBackgroundColor(Color.parseColor("#6e6eff"));
                ((MonthItemViewHolder) holder).m_status.setTextColor(Color.parseColor("#FFFFFF"));
            }

            String price = String.valueOf(Integer.parseInt(profitList.get(position).getBuy_price()));

            //종목명
            ((MonthItemViewHolder) holder).stock_name.setText(profitList.get(position).getStock_name());
            //매수가
            ((MonthItemViewHolder) holder).buy_price.setText(price.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
            //퍼센테이지
            ((MonthItemViewHolder) holder).per.setText(profitList.get(position).getPer() + "%");
            //날짜
            ((MonthItemViewHolder) holder).date.setText(profitList.get(position).getDate());



        }
    }

    @Override
    public int getItemCount() {
        return profitList.size();
    }

    public class MonthItemViewHolder extends RecyclerView.ViewHolder{

        TextView stock_name , m_status , buy_price , per , date;


        public MonthItemViewHolder(@NonNull View itemView){
            super(itemView);
            stock_name = (TextView) itemView.findViewById(R.id.stock_name);
            m_status = (TextView) itemView.findViewById(R.id.m_status);
            buy_price = (TextView) itemView.findViewById(R.id.buy_price);
            per = (TextView) itemView.findViewById(R.id.per);
            date = (TextView) itemView.findViewById(R.id.date);

        }
    }
}
