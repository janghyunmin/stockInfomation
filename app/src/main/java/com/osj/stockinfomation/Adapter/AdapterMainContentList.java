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
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class AdapterMainContentList extends NsBaseRecyclerViewAdapter<AdapterMainContentList.ItemViewHolder, ResultMarketConditionsDAOList> {

    private Activity activity;
    onClickCallback onClick;
    String contentType;
    CustomerMainPresenter.FavClick fav;
    int type; // 1tab / 2tab 비교 변수

    public interface onClickCallback {
        void onClick(ResultMarketConditionsDAOList item, String contentType);
    }

    public AdapterMainContentList(int type,Activity mContext, List<ResultMarketConditionsDAOList> data, String contentType, onClickCallback onClick, CustomerMainPresenter.FavClick fav) {
        super(mContext, data);
        this.activity = mContext;
        this.contentType = contentType;
        this.onClick = onClick;
        this.fav = fav;
        this.type = type;
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
            /** jhm 2021-08-31 오후 6:31 
             * 이미지 삭제
             ***/
            if(type == 1 ){
                holder.iv_maincontent_row.setVisibility(View.GONE);
                /** jhm 2021-08-31 오후 6:46
                 * 게시일 날짜 추가 split
                 ***/
                String date = item.getWrDatetime();
                String result = date.substring(0,10);
                holder.txt_maincontent_view_date.setText(result);
            }else {
                holder.iv_maincontent_row.setVisibility(View.VISIBLE);
                Glide.with(activity).load(item.getWrFile()).asBitmap().into(holder.iv_maincontent_row);
                holder.txt_maincontent_view_date.setVisibility(View.GONE);
            }

            holder.txt_maincontent_title.setText(item.getLabel() + " " + item.getWrSubject());

            String content = holder.txt_maincontent_title.getText().toString();
            SpannableString spannableString = new SpannableString(content);
            String word = item.getLabel();
            int start = content.indexOf(word);
            int end = start + word.length();
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.txt_maincontent_title.setText(spannableString);

            if(!item.getWrLike().isEmpty())
                holder.txt_maincontent_fav_count.setText(item.getWrLike());
            else
                holder.txt_maincontent_fav_count.setText("0");

            if(!item.getWrHit().isEmpty())
                holder.txt_maincontent_view_count.setText(item.getWrHit());
            else
                holder.txt_maincontent_view_count.setText("0");

            if(item.getLikeCheck().toLowerCase().equals("n"))
                holder.iv_maincontent_fav.setBackgroundResource(R.drawable.fav_off);
            else
                holder.iv_maincontent_fav.setBackgroundResource(R.drawable.fav_on);

            holder.iv_maincontent_fav.setTag(position);
            holder.itemView.setTag(position);




//            holder.iv_maincontent_row.setTag(R.string.common_google_play_services_enable_button, position);
//            holder.ll__maincontent.setTag(position);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_maincontent_row;
        private TextView txt_maincontent_title;
        private ImageView iv_maincontent_fav;
        private TextView txt_maincontent_fav_count;
        private TextView txt_maincontent_view_count;
        private LinearLayout ll__maincontent;
        private TextView txt_maincontent_view_date;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            iv_maincontent_row = (ImageView)view.findViewById(R.id.iv_maincontent_row);
            txt_maincontent_title = (TextView)view.findViewById(R.id.txt_maincontent_title);
            iv_maincontent_fav = (ImageView)view.findViewById(R.id.iv_maincontent_fav);
            txt_maincontent_fav_count = (TextView)view.findViewById(R.id.txt_maincontent_fav_count);
            txt_maincontent_view_count = (TextView)view.findViewById(R.id.txt_maincontent_view_count);
            ll__maincontent = (LinearLayout)view.findViewById(R.id.ll__maincontent);
            txt_maincontent_view_date = (TextView) view.findViewById(R.id.txt_maincontent_view_date);

            try {

                iv_maincontent_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fav.FavClick(getData().get((int)v.getTag()));
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onClick(getData().get((int)view.getTag()), contentType);
                    }
                });

//                iv_maincontent_row.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onClick.onClick(getData().get((int)view.getTag(R.string.common_google_play_services_enable_button)), contentType);
//                    }
//                });
//
//                ll__maincontent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onClick.onClick(getData().get((int)view.getTag()), contentType);
//                    }
//                });

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }

}
