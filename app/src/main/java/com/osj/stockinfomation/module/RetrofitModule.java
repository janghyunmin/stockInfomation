package com.osj.stockinfomation.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.osj.stockinfomation.Http.EndpointMain;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitModule {
    private static final String BASE_URL = "http://158.247.226.177";



    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static EndpointMain getService(){
        return getInstance().create(EndpointMain.class);
    }


//    private static Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//
//    public static <S> S createService(Class<S> serviceClass) {
//        return retrofit.create(serviceClass);
//    }

}
