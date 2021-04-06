package com.osj.stockinfomation.MVP;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.osj.stockinfomation.Http.EndpointMain;
import com.osj.stockinfomation.Http.NSCallback;
import com.osj.stockinfomation.Http.RetrofitSender2;

/**
 * Created by YJK on 2019-03-14
 **/
public class CustomerMainPresenter {

    private int page = 1;

//    public void loadList(final boolean isFirst, final Activity context, final NSCallback.SingleObjectCallback<AdapterAttendanceList> callback) {
//
//        try {
//
//            if (isFirst) {
//
//                page = 1;
//
//            } else {
//                page++;
//            }
//
//            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getAttendanceList(page, dataModel.getParentPhone(), "").enqueue(new NSCallback<AttendanceListResponseModel>(new NSCallback.SingleObjectCallback<AttendanceListResponseModel>() {
//                @Override
//                public void onSuccess(AttendanceListResponseModel result) {
//
//                    if (result.isResult()) {
//
//
//                        if (result.getList() != null && result.getList().size() > 0) {
//
//                            if (isFirst) {
//
//                                adapterAttendanceList = new AdapterAttendanceList(context, result.getList());
//                                callback.onSuccess(adapterAttendanceList);
//
//                            } else {
//
//                                adapterAttendanceList.addAll(result.getList());
//                                callback.onSuccess(null);
//
//                            }
//
//                        } else {
//
//                            callback.onSuccess(null);
//
//                        }
//
//                    } else {
//                        callback.onFailed(result.getCode());
//                    }
//
//                }
//
//                @Override
//                public void onFailed(String fault) {
//                    callback.onFailed(fault);
//                }
//            }));
//
//        } catch (Exception e) {
//            ErrorController.showError(e);
//        }
//    }

}
