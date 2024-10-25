package com.example.TagFinder;

import com.example.TagFinder.ModalOfApi.GetToken;
import com.example.TagFinder.ModalOfApi.MatchResponse;
import com.example.TagFinder.ModalOfApi.MusicResponse;
import com.example.TagFinder.ModalOfApi.TracksAllResponse;
import com.example.TagFinder.ModalOfApi.VideoTagResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apiresponse {

    @PUT("/products/{id}")
    Call<ProductModal> updateProduct(@Path("id") int id, @Body ProductModal productModal);

    @GET("youtube/v3/videos")
    Call<VideoTagResponse> getVideoDetails(
            @Query("part") String part,      // snippet part to fetch tags
            @Query("id") String videoId,     // YouTube video ID
            @Query("key") String apiKey      // YouTube API Key
    );

    @Headers({
            "x-rapidapi-host: cricbuzz-cricket.p.rapidapi.com",
            "x-rapidapi-key: 32f8bcbd6emsh6241cd5139dc1c8p157f48jsn22b4cef94b0e"
    })
    @GET("/matches/v1/recent")
    Call<MatchResponse> getMatchDetails();

    @FormUrlEncoded
    @POST("/api/token")
    Call<GetToken> getTokenCall(@Field("grant_type") String grant_type,
                                @Field("client_id") String clientId,
                                @Field("client_secret") String clientSId);

    @GET("v1/search")
    Call<MusicResponse> getMusicData(@Header("Authorization") String token,
                                     @Query("q") String serach,
                                     @Query("type") String type,
                                     @Query("market") String market,
                                     @Query("include_external") String inex);
    @GET("/v1/playlists/6XbRshpsOibcowLY0dN6Mp/tracks")
    Call<TracksAllResponse> getTracks(@Header("Authorization") String token);
}


