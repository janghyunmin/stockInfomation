package com.osj.stockinfomation.Retrofit;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.osj.stockinfomation.Http.EndpointMain;
import com.osj.stockinfomation.util.EncryptUtil;
import com.osj.stockinfomation.util.ModelEncryptData;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String baseUrl = "https://sandbox-apigw.koscom.co.kr/v2/market/stocks/"; // 현재 코스콤 kospi , kosdaq 지수 api 주소
    private static int CONNECT_TIMEOUT = 30;
    private static int WRITE_TIMEOUT = 25;
    private static int READ_TIMEOUT = 25;

    private static Context mContext;

    private static RetrofitClient ourInstance = new RetrofitClient();
    public static RetrofitClient getInstance(){
        return ourInstance;
    }
    public static RetrofitClient getInstance(Context context){
        mContext = context.getApplicationContext();
        return ourInstance;
    }

    public RetrofitClient() {
    }

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(request.body() != null) {
                            String time = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
                            String decryptedConent = EncryptUtil.getInstance().EncryptToDecrypt(bodyToString(request.body()).replace("\\n", "").replace(" ", "")
                                    .replace("\\", "").replace("\"{", "{").replace("}\"", "}"), time);
                            ModelEncryptData modelEncryptData = new ModelEncryptData(time, decryptedConent, "aa");
                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), modelEncryptData.toString());
                            request = request.newBuilder()
                                    .method(request.method(), body)
                                    .header("apikey","l7xxb7342660d3bb4a3289781ab97a31f4d2 ").build();
                        }else{

                            request = request.newBuilder()
                                    .header("apikey","l7xxb7342660d3bb4a3289781ab97a31f4d2 ").build();
                        }
                        return chain.proceed(request);
                    }
                }).addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new ResponseInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //jhm 2020-05-27 retrofit 실패시 다시 연결시도
                .build();

        return client;
    }
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    EndpointMain service = retrofit.create(EndpointMain.class);
    public EndpointMain getService(){
        return service;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

}
