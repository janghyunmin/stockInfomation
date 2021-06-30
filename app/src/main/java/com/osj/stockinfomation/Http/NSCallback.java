package com.osj.stockinfomation.Http;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SJH on 2017-06-26.
 */

public class NSCallback<T> implements Callback<T> {

    public interface OnBackPressedListener {
        void onBackPressed();
    }

    private SingleObjectCallback<T> mCallback;

    public NSCallback(SingleObjectCallback<T> callback) {
        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        try {
            T res = response.body();

            if (res != null) {

                HttpErrorController.showMessage(res.toString());

                if (res instanceof BaseResponseModel) {

                    BaseResponseModel baseResponseModel = (BaseResponseModel) res;

                    if (baseResponseModel.isResult()) {

                        mCallback.onSuccess(res);
                    } else {

                        if (baseResponseModel.getMessage() == null || !baseResponseModel.getMessage().equalsIgnoreCase("")) {

                            mCallback.onFailed(baseResponseModel.getCode() + "");

                        } else {

                            mCallback.onFailed(baseResponseModel.getCode() + "");
                        }
                    }

                } else {

                    mCallback.onSuccess(res);

                }

            } else {

                mCallback.onFailed("서버로 부터 제대로 된 응답을 받지 못했습니다.");
            }
        } catch (Exception e) {
            HttpErrorController.showError(e);
            mCallback.onFailed("서버로 부터 제대로 된 응답을 받지 못했습니다.");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        HttpErrorController.showError(t);
        mCallback.onFailed("서버로 부터 제대로 된 응답을 받지 못했습니다.");
    }

    public interface SingleObjectCallback<T> {

        void onSuccess(T result);

        void onFailed(String fault);
    }
}
