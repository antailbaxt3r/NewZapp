package com.antailbaxt3r.newzapp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static APIInterface apiInterface;
    public static String BASE_URL = "http://newsapi.org/v2/";

    public static APIInterface getClient() {
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }
    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
//                String authToken = App.getInstance().getSessionManager().getX_AUTH_TOKEN();
//                if (!StringUtils.isEmpty(authToken)) {
//                    builder.addHeader("X-AUTH-TOKEN", authToken);
//                }
//                request = builder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
