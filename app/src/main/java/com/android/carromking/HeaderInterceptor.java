package com.android.carromking;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HeaderInterceptor {

    //Used for Calling API where Authorization token is required

    private static final String BASE_URL = "https://ecommerce-checkout.herokuapp.com/";


    public OkHttpClient getInterceptor(String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", token)
                            .build();
                    return chain.proceed(newRequest);
                }).build();
    }

    public Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
