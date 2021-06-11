package com.osj.stockinfomation.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOList;
import com.osj.stockinfomation.R;

public class AdapterGridPage3ContentList extends BaseAdapter {

    SpotUpDAO spotUpDAO;
    Context mContext;
    int clickIndex = -1;

    CommonCallback.SingleObjectCallback<SpotUpDAOList> callback;

    public AdapterGridPage3ContentList(SpotUpDAO spotUpDAO){
        this.spotUpDAO = spotUpDAO;
    }

    public void setCallback(CommonCallback.SingleObjectCallback<SpotUpDAOList> callback){
        this.callback = callback;
    }

    public void setList(SpotUpDAO spotUpDAO){
        this.spotUpDAO = spotUpDAO;
    }

    @Override
    public int getCount() {
        return spotUpDAO.getList().size();
    }

    @Override
    public Object getItem(int i) {
        return spotUpDAO.getList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mContext = viewGroup.getContext();

        SpotUpDAOList item = spotUpDAO.getList().get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.gridview_page3_row, viewGroup, false);
        }

        view.setTag(i);
        LinearLayout ll_page3_row = (LinearLayout)view.findViewById(R.id.ll_page3_row);
        TextView tv_page3_row = (TextView)view.findViewById(R.id.tv_page3_row);

        if(clickIndex == i){
            ll_page3_row.setBackgroundResource(R.drawable.img_spot_on);
            tv_page3_row.setTextColor(Color.parseColor("#ffffff"));
        } else {
            ll_page3_row.setBackgroundResource(R.drawable.img_spot_off);
            tv_page3_row.setTextColor(Color.parseColor("#999999"));
        }

        tv_page3_row.setText(item.getCodeName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = (int)view.getTag();
                notifyDataSetChanged();

                if(callback != null){
                    callback.onSuccess(spotUpDAO.getList().get((int)view.getTag()));
                }
            }
        });

        return view;
    }
}
