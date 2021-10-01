package com.osj.stockinfomation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DataModel.NewCategory01Model;
import com.osj.stockinfomation.DataModel.NewCategory02Model;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.ErrorController;
import com.osj.stockinfomation.util.LogUtil;

import java.util.ArrayList;

public class ParentRecyclerViewAdapter extends RecyclerView.Adapter<ParentRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<NewCategory01Model> parentModelArrayList;
    public Context cxt;
    private CustomerMainPresenter mPresenter;
    private Activity activity;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category;
        public RecyclerView childRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.tv_page3_row);
            childRecyclerView = itemView.findViewById(R.id.child_rv);
        }
    }

    public ParentRecyclerViewAdapter(ArrayList<NewCategory01Model> parentModelArrayList, Context context , Activity activity) {
        this.parentModelArrayList = parentModelArrayList;
        this.cxt = context;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_page3_row, parent, false);
        mPresenter = new CustomerMainPresenter();

        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return parentModelArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        NewCategory01Model currentItem = parentModelArrayList.get(position); // 대분류가 담긴 ArrayList
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false);
        holder.childRecyclerView.setLayoutManager(layoutManager);
        holder.childRecyclerView.setHasFixedSize(true);
        holder.category.setText(currentItem.getCodeName());

        ArrayList<NewCategory02Model> arrayList = new ArrayList<>();
        mPresenter.getCategory02(currentItem.getCaId(),currentItem.getCode(), new CommonCallback.SingleObjectCallback<SpotUpDAOCategory2>() {
            @Override
            public void onSuccess(SpotUpDAOCategory2 result) {

                for(int index = 0; index < result.getList().size(); index++){
                    arrayList.add(new NewCategory02Model(R.drawable.architect,
                            result.getList().get(index).getCodeName(),
                            result.getList().get(index).getCaId(),
                            result.getList().get(index).getCode()));
                }

                // 받아온 중분류 ArrayList를 넘겨준다.
                ChildRecyclerViewAdapter childRecyclerViewAdapter = new ChildRecyclerViewAdapter(
                        arrayList
                        ,holder.childRecyclerView.getContext()
                        , activity);
                holder.childRecyclerView.setAdapter(childRecyclerViewAdapter);
            }

            @Override
            public void onFailed(String fault) {
                LogUtil.logE("fail.." + fault);
            }
        });


    }
}
