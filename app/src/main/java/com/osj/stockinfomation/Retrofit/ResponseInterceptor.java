package com.osj.stockinfomation.Retrofit;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.osj.stockinfomation.DAO.IndexDAO;
import com.osj.stockinfomation.util.DecryptUtil;
import com.osj.stockinfomation.util.EncryptUtil;
import com.osj.stockinfomation.util.LogUtil;
import com.osj.stockinfomation.util.ModelEncryptData;

import java.io.IOException;
import java.sql.Timestamp;

import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("apikey","l7xxb7342660d3bb4a3289781ab97a31f4d2").build();
        Response response = chain.proceed(request);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

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
