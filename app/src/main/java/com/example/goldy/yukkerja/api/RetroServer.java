package com.example.goldy.yukkerja.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String base_url = "https://yukkerja.yukseminar.xyz/api/";
    String url = "https://yukkerja.yukseminar.xyz/";

    private static Retrofit retrofit;

    public static Retrofit getClient()
    {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public String url(){
        return url;
    }
}
