package com.osj.stockinfomation.Http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.osj.stockinfomation.C.C;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitSender2 {

    /* real */
    public static String BASE_URL = C.BASE_URL;
    public static String URL_IMG_BASE = "http://183.111.125.16:8080";;

    private static Retrofit retrofit = null;

    private static OkHttpClient httpClientNoAuth = null;

    private static OkHttpClient httpClient = null;
    private static Context context = null;
    private static OkHttpClient httpAddressClient = null;

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    /**
     * SSL을 사용하는 경우 BaseApplication에서 Context를 셋팅해줘야한다. (init 단계)
     *
     * @param applcationContext
     */
    private static void setBaseApplcationContext(Context applcationContext) {
        try {

            context = applcationContext;

        } catch (Exception e) {
            HttpErrorController.showError(e);
        }
    }

    public static <T> T initAndGetBaseEndPoint(Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    public static <T> T initAndGetBaseEndPointXML(Class<T> classType) {

        T result = null;

        initHttpClient();


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }

    public static <T> T initAndGetFileEndPoint(Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(URL_IMG_BASE).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    public static <T> T initAndGetBaseEndPoint(String url, Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    public static <T> T initAndGetBaseEndPointXML(String url, Class<T> classType) {

        T result = null;

        initHttpClient();


        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }

    public static <T> T initAndGetFileEndPoint(String url, Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    private static void initHttpClient() {
        try {
            if (httpClient == null) {
                if (context != null) {

                    OkHttpClient.Builder tempOkhttp = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).
                            addInterceptor(new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {

                                    Request request = chain.request();

                                    Request.Builder newRequest = request.newBuilder().header("Content-Type", "application/json");

                                    HttpErrorController.showMessage("URL : " + request.url().toString());

                                    return chain.proceed(newRequest.build());
                                }
                            });

                    tempOkhttp.sslSocketFactory(SSLUtil.getSSLSocketFactory(context));

                    httpClient = tempOkhttp.build();
                } else {
                    httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                }
            }


        } catch (Exception e) {
            HttpErrorController.showError(e);
        }
    }

    public static <T> T initAddressEndPoint(Class<T> classType) {

        String url = "http://www.juso.go.kr";

        T result = null;

        initAddressClient();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().baseUrl(url).client(httpAddressClient).addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        result = (T) retrofit.create(classType);
        return result;
    }

    private static void initAddressClient() {
        try {

            if (httpAddressClient == null) {

                OkHttpClient.Builder tempOkhttp = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).
                        addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();

//                                    Request.Builder newRequest = request.newBuilder().header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)");
                                Request.Builder newRequest = request.newBuilder().header("Content-Type", "application/json");

                                HttpErrorController.showMessage("URL : " + request.url().toString());

                                okhttp3.Response response = chain.proceed(newRequest.build());

                                return response;

//                                    return chain.proceed(newRequest.build());
                            }
                        });

//                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                    tempOkhttp.addInterceptor(interceptor);

//                    tempOkhttp.sslSocketFactory(SSLUtil.getSSLSocketFactory(context));

                httpAddressClient = tempOkhttp.build();

            }


        } catch (Exception e) {
            HttpErrorController.showError(e);
        }
    }

}
