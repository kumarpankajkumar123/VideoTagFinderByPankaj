package com.example.TagFinder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDataClass {

    private static final String BASE_URL = "https://www.googleapis.com/";
    private static final String BASE_URL1 = "https://cricbuzz-cricket.p.rapidapi.com";
    private static final String BASE_URL2 = "https://accounts.spotify.com";
    private static final String BASE_URL3 = "https://api.spotify.com";
    private static final String BASE_URL4 = "https://api.spotify.com";
    private static final String BASE_URL5 = "https://weatherapi-com.p.rapidapi.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }
    public static Retrofit getRetrofit1(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL1).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }

    public static Retrofit getToken(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static Retrofit getMusicData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL3).addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }

    public static Retrofit getAllTracks(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL4)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    public static Retrofit getTemp(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL5)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
