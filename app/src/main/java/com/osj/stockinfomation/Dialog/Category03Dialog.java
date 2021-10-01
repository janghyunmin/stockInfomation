package com.osj.stockinfomation.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.osj.stockinfomation.Adapter.ChildRootRecyclerViewAdapter;
import com.osj.stockinfomation.DataModel.NewCategory02Model;
import com.osj.stockinfomation.DataModel.NewCategory03Model;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.ToastUtil;

import java.util.ArrayList;

public class Category03Dialog extends Dialog {

    private View.OnClickListener okClickListener;
    ImageView iv_category3_close;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rc_category3;
    Context context;

    ArrayList<NewCategory03Model> newCategory03ArrayList;
    ChildRootRecyclerViewAdapter childRootRecyclerViewAdapter;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView( R.layout.dialog_custom_category3);


        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpWindow.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);



        iv_category3_close = findViewById(R.id.iv_category3_close);
        rc_category3 = findViewById(R.id.rc_category3);


        LogUtil.logE("newCategory03ArrayList : " + newCategory03ArrayList.size());
        if(newCategory03ArrayList.size() == 0 ){
            ToastUtil.toast(context,"데이터가 없습니다.");
        }else{
            rc_category3.setHasFixedSize( true );
            rc_category3.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            linearLayoutManager = new LinearLayoutManager(context);
            rc_category3.setLayoutManager( linearLayoutManager );
            childRootRecyclerViewAdapter = new ChildRootRecyclerViewAdapter(newCategory03ArrayList,context,activity);
            rc_category3.setAdapter(childRootRecyclerViewAdapter);
            childRootRecyclerViewAdapter.notifyDataSetChanged();

            iv_category3_close.setOnClickListener(okClickListener);
        }

    }

    public Category03Dialog(@NonNull Context context , View.OnClickListener okClickListener, ArrayList<NewCategory03Model> newCategory03ArrayList, Activity activity) {
        super(context);
        this.context = context;
        this.okClickListener = okClickListener;
        this.newCategory03ArrayList = newCategory03ArrayList;
        this.activity = activity;
    }
}
