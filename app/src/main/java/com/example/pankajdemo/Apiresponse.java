package com.example.pankajdemo;

import com.example.pankajdemo.ModalOfApi.VideoTagResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apiresponse {

 @PUT("/products/{id}")
 Call<ProductModal> updateProduct(@Path("id") int id,@Body ProductModal productModal);

 @GET("youtube/v3/videos")
 Call<VideoTagResponse> getVideoDetails(
         @Query("part") String part,      // snippet part to fetch tags
         @Query("id") String videoId,     // YouTube video ID
         @Query("key") String apiKey      // YouTube API Key
 );

}
