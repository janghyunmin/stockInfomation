package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory3;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.util.List;

public class AdapterMainpage4ContentList extends NsBaseRecyclerViewAdapter<AdapterMainpage4ContentList.ItemViewHolder, ResultMarketConditionsDAOList> {

    private Activity activity;

    AdapterMainpage4ContentList.onClickCallback onclick;

    public interface onClickCallback {
        void onClick(ResultMarketConditionsDAOList item);
    }

    public AdapterMainpage4ContentList(Activity mContext, List<ResultMarketConditionsDAOList> data, AdapterMainpage4ContentList.onClickCallback onclick) {
        super(mContext, data);
        this.activity = mContext;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_maincontent_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            ResultMarketConditionsDAOList item = data.get(position);
            Glide.with(mContext).load(item.getWrFile()).asBitmap().into(holder.iv_maincontent_row);

            holder.txt_maincontent_title.setText(item.getWrName());
            holder.txt_maincontent_subtitle.setText(item.getWrContent());
            String[] str = item.getWrDatetime().split(" ");
            if(str.length == 2)
                holder.txt_maincontent_date.setText(str[0]);
            else
                holder.txt_maincontent_date.setText(item.getWrDatetime());

            holder.itemView.setTag(position);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_maincontent_row;
        TextView txt_maincontent_title;
        TextView txt_maincontent_subtitle;
        TextView txt_maincontent_date;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            try {
            iv_maincontent_row = (ImageView)view.findViewById(R.id.iv_maincontent_row);
            txt_maincontent_title = (TextView)view.findViewById(R.id.txt_maincontent_title);
            txt_maincontent_subtitle = (TextView)view.findViewById(R.id.txt_maincontent_subtitle);
            txt_maincontent_date = (TextView)view.findViewById(R.id.txt_maincontent_date);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclick.onClick(data.get((int)view.getTag()));
                }
            });

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }

}
