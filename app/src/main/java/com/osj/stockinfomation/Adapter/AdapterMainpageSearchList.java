package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.DAO.GetSearchMainDAOList;
import com.osj.stockinfomation.DAO.SpotUpDAOListCategory2;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;

import java.util.List;

public class AdapterMainpageSearchList extends NsBaseRecyclerViewAdapter<AdapterMainpageSearchList.ItemViewHolder, GetSearchMainDAOList> {

    private Activity activity;

    public interface onClickCallback {
        void onClick(GetSearchMainDAOList item);
    }

    onClickCallback callback;

    public AdapterMainpageSearchList(Activity mContext, List<GetSearchMainDAOList> data, AdapterMainpageSearchList.onClickCallback callback) {
        super(mContext, data);
        this.activity = mContext;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_main_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        try {
            holder.itemView.setTag(position);
            GetSearchMainDAOList item = data.get(position);

            String s = item.getLabel() + item.getWrSubject();
            if(s.length() > 20)
                holder.txt_maincontent_title.setText(s.substring(0, 20) + "...");
            else
                holder.txt_maincontent_title.setText(item.getLabel() + " " + item.getWrSubject());
            holder.txt_maincontent_fav_count.setText(item.getWrLike());
            holder.txt_maincontent_view_count.setText(item.getWrHit());
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_maincontent_title;
        TextView txt_maincontent_fav_count;
        TextView txt_maincontent_view_count;

        public ItemViewHolder(@NonNull View view) {
            super(view);

            try {
                txt_maincontent_title = (TextView)view.findViewById(R.id.txt_maincontent_title);
                txt_maincontent_fav_count = (TextView)view.findViewById(R.id.txt_maincontent_fav_count);
                txt_maincontent_view_count = (TextView)view.findViewById(R.id.txt_maincontent_view_count);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onClick(data.get((int)view.getTag()));
                    }
                });

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }

}
