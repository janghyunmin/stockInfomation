package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAO;
import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory3;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by yjk, NomadSoft.Inc on 2019-04-05.
 */
public class AdapterCategoryLikeFavContentList extends NsBaseRecyclerViewAdapter<AdapterCategoryLikeFavContentList.ItemViewHolder, GetCategoryLikeFavDAOList> {

    private Activity activity;
    private String viewType = "";

    public interface SpotCallBack{
        void onMore(int position);
        void onTitleClick(String title);
        void onDelete(int position);
    }

    SpotCallBack spotCallBack;

    public AdapterCategoryLikeFavContentList(Activity mContext, List<GetCategoryLikeFavDAOList> data, String viewType, SpotCallBack spotCallBack) {
        super(mContext, data);
        this.activity = mContext;
        this.viewType = viewType;
        this.spotCallBack = spotCallBack;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_main_spot_content_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            GetCategoryLikeFavDAOList item = data.get(position);

            holder.iv_spot_content_star.setTag(position);
            holder.txt_spot_content_more.setTag(position);
            holder.txt_spot_content_title.setTag(position);
            holder.iv_spot_content_delete.setTag(position);

            if(!viewType.equals("Modal")){
                holder.iv_spot_content_star.setVisibility(View.GONE);
            } else {
                holder.iv_spot_content_delete.setVisibility(View.GONE);
            }

            holder.txt_spot_content_title.setText(item.getCodeName());

            DecimalFormat df2 = new DecimalFormat("#,###,##0");
            DecimalFormat df3 = new DecimalFormat("0.00");

            if(item.getChange() != null){
                if( item.getChange().toString().toLowerCase().equals("rise")){
                    holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(item.getDisplayedPrice())));
                    holder.txt_spot_content_per.setText(df3.format(item.getPer()) + "%");
                    holder.txt_spot_content_price.setTextColor(Color.parseColor("#C50000"));
                    holder.txt_spot_content_per.setTextColor(Color.parseColor("#C50000"));
                    holder.img_spot_content.setBackgroundResource(R.drawable.img_up_arrow);
                } else if(item.getChange().toString().toLowerCase().equals("even")) {
                    holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(item.getDisplayedPrice())));
                    holder.txt_spot_content_per.setText(df3.format(item.getPer()) + "%");
                    holder.txt_spot_content_price.setTextColor(Color.parseColor("#000000"));
                    holder.txt_spot_content_per.setTextColor(Color.parseColor("#000000"));
                    holder.img_spot_content.setBackgroundResource(0);
                } else {
                    holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(item.getDisplayedPrice())));
                    holder.txt_spot_content_per.setText(df3.format(item.getPer()) + "%");
                    holder.txt_spot_content_price.setTextColor(Color.parseColor("#0034AE"));
                    holder.txt_spot_content_per.setTextColor(Color.parseColor("#0034AE"));
                    holder.img_spot_content.setBackgroundResource(R.drawable.img_down_arrow);
                }
            } else {
                holder.txt_spot_content_price.setText(df2.format(Integer.parseInt(item.getDisplayedPrice())));
                holder.txt_spot_content_per.setText(df3.format(item.getPer()) + "%");
                holder.txt_spot_content_price.setTextColor(Color.parseColor("#C50000"));
                holder.txt_spot_content_per.setTextColor(Color.parseColor("#C50000"));
                holder.img_spot_content.setBackgroundResource(R.drawable.img_up_arrow);
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_spot_content_star;
        private TextView txt_spot_content_title;
        private ImageView iv_spot_content_delete;
        private TextView txt_spot_content_price;
        private TextView txt_spot_content_per;
        private TextView txt_spot_content_more;
        private ImageView img_spot_content;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            iv_spot_content_star = (ImageView)view.findViewById(R.id.iv_spot_content_star);
            txt_spot_content_title = (TextView)view.findViewById(R.id.txt_spot_content_title);
            iv_spot_content_delete = (ImageView)view.findViewById(R.id.iv_spot_content_delete);
            txt_spot_content_price = (TextView)view.findViewById(R.id.txt_spot_content_price);
            txt_spot_content_per = (TextView)view.findViewById(R.id.txt_spot_content_per);
            txt_spot_content_more = (TextView)view.findViewById(R.id.txt_spot_content_more);
            img_spot_content = (ImageView)view.findViewById(R.id.img_spot_content);

            try {
                txt_spot_content_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spotCallBack.onTitleClick(getData().get((int)v.getTag()).getCodeName());
                    }
                });

                txt_spot_content_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spotCallBack.onMore((int)v.getTag());
                    }
                });

                iv_spot_content_delete.setOnClickListener(new View.OnClickListener() {
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
