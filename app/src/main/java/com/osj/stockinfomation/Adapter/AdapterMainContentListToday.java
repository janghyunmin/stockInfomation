package com.osj.stockinfomation.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class AdapterMainContentListToday extends NsBaseRecyclerViewAdapter<AdapterMainContentListToday.ItemViewHolder, ResultMarketConditionsDAOList> {

    private Activity activity;
    onClickCallback onClick;
    String contentType;
    CustomerMainPresenter.FavClick fav;

    public interface onClickCallback {
        void onClick(ResultMarketConditionsDAOList item, String contentType);
    }

    public AdapterMainContentListToday(Activity mContext, List<ResultMarketConditionsDAOList> data, String contentType, onClickCallback onClick, CustomerMainPresenter.FavClick fav) {
        super(mContext, data);
        this.activity = mContext;
        this.contentType = contentType;
        this.onClick = onClick;
        this.fav = fav;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_maincontent_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            ResultMarketConditionsDAOList item = data.get(position);

            if(item.getWrSubject().contains("??????")){
                Glide.with(activity).load(R.drawable.open).into(holder.icon);
            }else if(item.getWrSubject().contains("?????????")){
                Glide.with(activity).load(R.drawable.close).into(holder.icon);
            }
            holder.txt_maincontent_title.setText(item.getWrSubject()); // ????????? ??????

            String db_date = item.getWrDatetime(); // db??? ?????????????????? ??????

            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ????????????


            LogUtil.logE("db_today : " + db_date);
            LogUtil.logE("sdf.format(mDate) : " + sdf.format(mDate));

            if(db_date.substring(0,10).equals(sdf.format(mDate))){
                LogUtil.logE("??????????????? ?????? return new");
                holder.new_icon.setVisibility(View.VISIBLE);
            }else{
                LogUtil.logE("??????????????? ??????????????? ?????? return old");
                holder.new_icon.setVisibility(View.GONE);
            }

            Glide.with(activity).load(R.drawable.research).into(holder.view_icon); // ??? ??? ?????? icon
            if(!item.getWrHit().isEmpty())
                holder.txt_maincontent_view_count.setText(item.getWrHit());
            else
                holder.txt_maincontent_view_count.setText("0");


            holder.itemView.setTag(position);
            holder.ll__maincontent.setTag(position);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon , new_icon , view_icon ;
        private TextView txt_maincontent_title , txt_maincontent_view_count;
        private ConstraintLayout ll__maincontent;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            icon = (ImageView)view.findViewById(R.id.icon);
            new_icon = (ImageView)view.findViewById(R.id.new_icon);
            view_icon = (ImageView)view.findViewById(R.id.view_icon);
            txt_maincontent_title = (TextView)view.findViewById(R.id.txt_maincontent_title);
            txt_maincontent_view_count = (TextView)view.findViewById(R.id.txt_maincontent_view_count);
            ll__maincontent = (ConstraintLayout)view.findViewById(R.id.ll__maincontent);

                ll__maincontent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onClick(getData().get((int)view.getTag()), contentType);
                    }
                });

        }
    }

}

