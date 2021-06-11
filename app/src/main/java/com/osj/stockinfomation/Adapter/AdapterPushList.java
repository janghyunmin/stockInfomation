package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DAO.GetLikeDAOList;
import com.osj.stockinfomation.DAO.GetPushListDAOList;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class AdapterPushList extends NsBaseRecyclerViewAdapter<AdapterPushList.ItemViewHolder, GetPushListDAOList> {

    private Activity activity;
    private String type;

    public interface SpotCallBack{
        void onTitleClick(int position);
        void onDelete(int position);
    }

    SpotCallBack spotCallBack;

    public AdapterPushList(Activity mContext, List<GetPushListDAOList> data, String type, SpotCallBack spotCallBack) {
        super(mContext, data);
        this.activity = mContext;
        this.spotCallBack = spotCallBack;
        this.type = type;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_fav_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            GetPushListDAOList item = data.get(position);

            holder.iv_my_fav.setVisibility(View.GONE);

            holder.iv_my_fav.setTag(position);
            holder.itemView.setTag(position);
            holder.iv_my_fav_close.setTag(position);
            holder.ll_my_fav_close.setTag(position);

            if(type.equals("notice"))
                holder.ll_my_fav_close.setVisibility(View.GONE);

            holder.txt_my_fav_title.setText(item.getPuSubject());

            String[] str = item.getSendDatetime().split(" ");
            if(str.length == 2)
                holder.txt_my_fav_date.setText(str[0]);
            else
                holder.txt_my_fav_date.setText(item.getSendDatetime());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_my_fav;
        private TextView txt_my_fav_date;
        private ImageView iv_my_fav_close;
        private LinearLayout ll_my_fav_close;
        private TextView txt_my_fav_title;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            iv_my_fav = (ImageView)view.findViewById(R.id.iv_my_fav);
            txt_my_fav_date = (TextView)view.findViewById(R.id.txt_my_fav_date);
            iv_my_fav_close = (ImageView)view.findViewById(R.id.iv_my_fav_close);
            ll_my_fav_close = (LinearLayout) view.findViewById(R.id.ll_my_fav_close);
            txt_my_fav_title = (TextView)view.findViewById(R.id.txt_my_fav_title);

            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotCallBack.onTitleClick((int)view.getTag());
                    }
                });

                ll_my_fav_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotCallBack.onDelete((int) view.getTag());
                    }
                });

                iv_my_fav_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spotCallBack.onDelete((int) view.getTag());
                    }
                });


            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }

}
