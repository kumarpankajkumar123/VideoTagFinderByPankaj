package com.example.TagFinder;

import static com.example.TagFinder.R.drawable.baseline_play_circle_filled_24;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.TagFinder.ModalOfApi.GetToken;
import com.example.TagFinder.ModalOfApi.MusicResponse;
import com.example.TagFinder.ModalOfApi.TracksAllResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MultiPartImage extends AppCompatActivity {

    TextInputEditText searchSongs;
    MaterialButton btn;
    TextView text;
    ImageView play_pause,nextMusic,previousMusic,videoStream;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;
    String token;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String getSearch_value;
    boolean isPlaying = false;
    String url;
    List<TracksAllResponse.Items> itemsList;
    int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_part_image);

        searchSongs = findViewById(R.id.searchSongs);
        text = findViewById(R.id.text);
        play_pause = findViewById(R.id.play_pause);
        nextMusic = findViewById(R.id.nextMusic);
        previousMusic = findViewById(R.id.previousMusic);
        videoStream = findViewById(R.id.videoStream);

//        url = "https://p.scdn.co/mp3-preview/0b03804d2526528d4d761098b18f8b2fcb852367?cid=6ff8931ba024411ebf8ddc845231f0f7";

        mediaPlayer = new MediaPlayer();
        sp = getSharedPreferences("token_access", MODE_PRIVATE);
        editor = sp.edit();

        getToken();
    }

    public void musicstart(String url,String imgUrl) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    isPlaying = true;
                    play_pause.setImageDrawable(getDrawable(R.drawable.baseline_pause_24));

                   Glide.with(getApplicationContext()).load(imgUrl).into(videoStream);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stoply() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            isPlaying = false;
            play_pause.setImageDrawable(getDrawable(baseline_play_circle_filled_24));
        }
    }

    public void getToken() {
        Retrofit retrofit = RetrofitDataClass.getToken();
        Apiresponse apiresponse = retrofit.create(Apiresponse.class);
        Call<GetToken> getResponse = apiresponse.getTokenCall("client_credentials",
                "6ff8931ba024411ebf8ddc845231f0f7",
                "3f8f6b4922884ffbb5f7c8d84554ba24");

        Log.e("response request", ":=" + getResponse.request());

        getResponse.enqueue(new Callback<GetToken>() {
            @Override
            public void onResponse(Call<GetToken> call, Response<GetToken> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e("token", ":=" + response.body().getAccess_token());
                    GetToken mtoken = response.body();
                    token = mtoken.getAccess_token();
                    storeToken(token);
//                    getMusicData("Bearer " + token);
                    getTracks("Bearer " + token);
                } else {
                    Log.e("token not access", ":=" + response.message());
                }
            }

            @Override
            public void onFailure(Call<GetToken> call, Throwable t) {
                Log.e("response failed", ":=" + t.getMessage());
            }
        });
    }

    public void getTracks(String token) {
        Apiresponse apiresponse = RetrofitDataClass.getAllTracks().create(Apiresponse.class);
        Call<TracksAllResponse> allResponse = apiresponse.getTracks(token);

        Log.e("track request", ":= " + allResponse.request());

        allResponse.enqueue(new Callback<TracksAllResponse>() {
            @Override
            public void onResponse(Call<TracksAllResponse> call, Response<TracksAllResponse> response) {
                TracksAllResponse tracksAllResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {

                    Log.e("inside response",":= methods");
                    Log.e("the url", ":=" + tracksAllResponse.getItems()
                            .get(0).getTrack().getPreview_url());

                    itemsList = tracksAllResponse.getItems();
                    nextMusic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playNextTrack();
                        }
                    });

                    previousMusic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playPreviousTrack();
                        }
                    });
                    play_pause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playTrack(currentIndex);
                        }
                    });

                } else {
                    Log.e("the response", ":= failed");
                }
            }

            @Override
            public void onFailure(Call<TracksAllResponse> call, Throwable t) {
                Log.e("inside failure"," methods");
            }
        });
    }

    private void playPreviousTrack() {
        isPlaying = false;
        if (currentIndex > 0) {
            currentIndex--;
            playTrack(currentIndex);
        }
        else{
            currentIndex = itemsList.size()-1;
        }
    }

    private void playNextTrack() {
        isPlaying = false;
        if (currentIndex < itemsList.size() - 1) {
            currentIndex++;
            playTrack(currentIndex);
        }
        else {
            currentIndex = 0;
        }
    }

    private void playTrack(int index) {
        if (itemsList != null && !itemsList.isEmpty()) {
            String previewUrl = itemsList.get(index).getTrack().getPreview_url();
            String imageurl = itemsList.get(index).getTrack().getAlbum().getImages().get(0).getUrl();
            if (previewUrl != null && !previewUrl.isEmpty()) {
                if(!isPlaying){
                    musicstart(previewUrl,imageurl);
                }
                else {
                    stoply();
                }
            }
        }
    }

    public void getMusicData(String ansToken) {
        Retrofit retrofit1 = RetrofitDataClass.getMusicData();
        Apiresponse apiresponse1 = retrofit1.create(Apiresponse.class);
//        getSearch_value = searchSongs.getText().toString();
        Call<MusicResponse> musicResponseCall = apiresponse1.getMusicData(ansToken, "hindi songs", "playlist", "IN", "music");
//        getAllData(musicResponseCall);
    }
    public void storeToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }
    public void getAllData(Call<MusicResponse> mMusRes) {

        Log.e("music response request", ":=" + mMusRes.request());
        mMusRes.enqueue(new Callback<MusicResponse>() {
            @Override
            public void onResponse(Call<MusicResponse> call, Response<MusicResponse> response) {
                MusicResponse musicResponse = response.body();
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("parent name", ":=" + musicResponse.getPlaylists()
                            .getItems().get(0).getName());
                    Log.e("artist name", ":=" + musicResponse.getPlaylists()
                            .getItems().get(0).getOwner().getDisplay_name());
                    text.setText(musicResponse.getPlaylists().getItems().get(0).getTracks().getHref());

                } else {

                }
            }

            @Override
            public void onFailure(Call<MusicResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop(); // Stop the media player if it's playing
            }
            mediaPlayer.release(); // Release the MediaPlayer resources
            mediaPlayer = null;
        }
    }
}



