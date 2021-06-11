package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory2;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.util.List;

public class AdapterMainpage3Cagegory2ContentList extends NsBaseRecyclerViewAdapter<AdapterMainpage3Cagegory2ContentList.ItemViewHolder, SpotUpDAOListCategory2> {

    int clickIndex = -1;

    private Activity activity;

    public interface onClickCallback {
        void onClick(SpotUpDAOListCategory2 item);
    }

    onClickCallback callback;

    public void setClickIndex(int clickIndex){
        this.clickIndex = clickIndex;
    }

    public AdapterMainpage3Cagegory2ContentList(Activity mContext, List<SpotUpDAOListCategory2> data, AdapterMainpage3Cagegory2ContentList.onClickCallback callback) {
        super(mContext, data);
        this.activity = mContext;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_page3_category2_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            SpotUpDAOListCategory2 item = data.get(position);
            if(clickIndex == position){
                holder.ll_page3_row.setBackgroundResource(R.drawable.img_spot2_on);
                holder.tv_page3_row.setTextColor(Color.parseColor("#C50000"));
            } else {
                holder.ll_page3_row.setBackgroundResource(R.drawable.img_spot2_off);
                holder.tv_page3_row.setTextColor(Color.parseColor("#999999"));
            }

            holder.itemView.setTag(position);
            holder.tv_page3_row.setText(item.getCodeName());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_page3_row;
        TextView tv_page3_row;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            try {
                ll_page3_row = (LinearLayout)view.findViewById(R.id.ll_page3_row);
                tv_page3_row = (TextView)view.findViewById(R.id.tv_page3_row);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setClickIndex((int)view.getTag());
                        notifyDataSetChanged();
                        callback.onClick(data.get((int)view.getTag()));
                    }
                });

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }

}
