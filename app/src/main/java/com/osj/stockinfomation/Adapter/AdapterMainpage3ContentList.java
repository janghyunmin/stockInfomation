package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory2;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.util.List;

public class AdapterMainpage3ContentList extends NsBaseRecyclerViewAdapter<AdapterMainpage3ContentList.ItemViewHolder, SpotUpDAOList> {

    int clickIndex = -1;

    private Activity activity;

    public interface onClickCallback {
        void onClick(SpotUpDAOList item);
    }

    onClickCallback callback;

    public void setClickIndex(int clickIndex){
        this.clickIndex = clickIndex;
    }

    public AdapterMainpage3ContentList(Activity mContext, List<SpotUpDAOList> data, AdapterMainpage3ContentList.onClickCallback callback) {
        super(mContext, data);
        this.activity = mContext;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_page3_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            SpotUpDAOList item = data.get(position);

            holder.itemView.setTag(position);

            if(clickIndex == position){
                holder.ll_page3_row.setBackgroundResource(R.drawable.img_spot_on);
                holder.tv_page3_row.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.ll_page3_row.setBackgroundResource(R.drawable.img_spot_off);
                holder.tv_page3_row.setTextColor(Color.parseColor("#999999"));
            }

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
