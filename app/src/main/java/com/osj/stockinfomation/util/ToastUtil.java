package com.osj.stockinfomation.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    /**
     *  2020-09-30
     * **/

    public static void toast(Context ctx, Context baseCtx, String msg) {
        if(android.os.Build.VERSION.SDK_INT >= 26){
            toast(ctx, msg);
        }else{
            toast(baseCtx, msg);
        }
    }

    /**
     *   2020-09-30
     * **/
    public static void toast(Context ctx, String msg) {
        if (null == toast) toast = toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        else toast.setText(msg);

        toast.show();
    }


    /**
     *  2020-09-30
     * **/
    public static void toast(Context ctx, int strId) {
        toast(ctx, ctx.getString(strId));
    }

}

