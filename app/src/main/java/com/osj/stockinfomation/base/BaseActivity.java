package com.osj.stockinfomation.base;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.osj.stockinfomation.R;

public class BaseActivity extends AppCompatActivity {

    ProgressBar progress;

    public interface OnClickListener {
        void onClick();
    }

    public interface OnCancelListener {
        void onClick();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setProgress(ProgressBar progress){
        this.progress = progress;
    }

    public void showProgress(){
        if(progress != null)
            progress.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        if(progress != null)
            progress.setVisibility(View.GONE);
    }

    /*
    사용법
    showCustomAlert(this, "title", "sub TItle", false, 0, 2, "", ""
                , new OnCancelListener() {
            @Override
            public void onClick() {
                ErrorController.showMessage("listener");
            }
        }, new OnClickListener() {
            @Override
            public void onClick() {
                ErrorController.showMessage("listener");
            }
        });

        showCustomAlert(this, "title", "sub TItle", false, 0, 2, "", ""
                , null, null);

        showCustomAlert(this, "title", "sub TItle", false, 0, 1, "", ""
                , null, new OnClickListener() {
                    @Override
                    public void onClick() {

                    }
                });

        showCustomAlert(this, "", "sub TItle", true , R.drawable.img_alert_error, 1, "", ""
                , null, null);

     */

    protected void showCustomAlert(Context mContext,  String title, String subTitle, boolean iconShow, int resId, int btnCount, String cancelText, String okText,
                                            OnCancelListener cancelListener, OnClickListener clickListener){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_one_btn, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        if(!title.isEmpty())
            ((TextView)alertDialog.findViewById(R.id.txt_dialog_title)).setText(title);
        else
            ((TextView)alertDialog.findViewById(R.id.txt_dialog_title)).setVisibility(View.GONE);

        if(!subTitle.isEmpty())
            ((TextView)alertDialog.findViewById(R.id.txt_dialog_subtitle)).setText(subTitle);
        else
            ((TextView)alertDialog.findViewById(R.id.txt_dialog_subtitle)).setVisibility(View.GONE);

        if(iconShow){
            ((ImageView)alertDialog.findViewById(R.id.iv_dialog_icon)).setVisibility(View.VISIBLE);
            ((ImageView)alertDialog.findViewById(R.id.iv_dialog_icon)).setBackgroundResource(resId);
        }
        else
            ((ImageView)alertDialog.findViewById(R.id.iv_dialog_icon)).setVisibility(View.GONE);

        if(!cancelText.isEmpty())
            ((Button)alertDialog.findViewById(R.id.btn_dialog_cancel)).setText(cancelText);

        if(!okText.isEmpty())
            ((Button)alertDialog.findViewById(R.id.btn_dialog_ok)).setText(okText);

        if(btnCount == 1){
            ((Button)alertDialog.findViewById(R.id.btn_dialog_cancel)).setVisibility(View.GONE);
            ((Button)alertDialog.findViewById(R.id.btn_dialog_ok)).setBackgroundResource(R.drawable.img_dialog_red_bg);


            ((Button)alertDialog.findViewById(R.id.btn_dialog_ok)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onClick();
                    }
                    alertDialog.dismiss();
                }
            });
        } else {
            ((Button)alertDialog.findViewById(R.id.btn_dialog_ok)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickListener != null){
                        clickListener.onClick();
                    }
                    alertDialog.dismiss();
                }
            });

            ((Button)alertDialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cancelListener != null){
                        cancelListener.onClick();
                    }
                    alertDialog.dismiss();
                }
            });
        }


    }

    protected void showPermisstionCustomAlert(Context mContext, OnCancelListener cancelListener, OnClickListener clickListener){
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_permisstion, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        ((Button)alertDialog.findViewById(R.id.btn_dialog_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListener != null){
                    clickListener.onClick();
                }
                alertDialog.dismiss();
            }
        });

        ((Button)alertDialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancelListener != null){
                    cancelListener.onClick();
                }
                alertDialog.dismiss();
            }
        });
    }
}
