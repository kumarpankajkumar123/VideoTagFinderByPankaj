package com.example.pankajdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pankajdemo.ModalOfApi.TagsAdaptor;
import com.example.pankajdemo.ModalOfApi.VideoTagResponse;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoTagsGet extends AppCompatActivity implements TagsAdaptor.TagSelectionListener {

    String MY_KEY = "AIzaSyCrdo85_ezkyP0tMC-rC52Hlpr2qPjD7E8";
    //    private static final String YOUTUBE_URL_PATTERN = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$";
    private static final String YOUTUBE_URL_PATTERN = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu\\.be))(\\/((watch\\?v=|embed\\/|v\\/|shorts\\/)?([\\w\\-]{11}))(\\S*)?)?$";
//    private static final String YOUTUBE_URL_PATTERNBOTH = "^(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/.*[?&]v=|youtu\\.be\\/|youtube\\.com\\/shorts\\/)?([\\w\\-]{11})(?:[\\s\\S]*)?$";


    EditText inputVideoUrl;
    Button submit,clear;
    RecyclerView recyclerView;
    TagsAdaptor tagsAdaptor;
    ProgressDialog progressDialog;
    private Set<String> selectedTags;
    TextView copy,copyAll,selectAll;
    private String lastRequestedVideoId = null;

    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_tags_get);

        inputVideoUrl = findViewById(R.id.inputVideoUrl);
        submit = findViewById(R.id.submit);
        clear = findViewById(R.id.clear);
        recyclerView = findViewById(R.id.videoTags);

        copy = findViewById(R.id.copy);
        copyAll = findViewById(R.id.copyAll);
        selectAll = findViewById(R.id.selectAll);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputVideoUrl.setText("");
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(VideoTagsGet.this,"Cleared url",Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("submit method", "call hua");
                String url = inputVideoUrl.getText().toString();
                boolean chek = IsValidUrl(url);

                if (chek) {
                    String videoId = getVideoIdFromUrl(url);
                    Log.e("video id", ":= " + videoId);
                    if (videoId != null && !videoId.equals(lastRequestedVideoId)) {
                        recyclerView.setVisibility(View.VISIBLE);
                        lastRequestedVideoId = videoId;
                        fetchVideoTags(videoId);
//                        progressDialog.show();
//                        progressDialog.setTitle("Loading..");
                    }
                    else if (videoId != null) {
                        // If it's the same video ID, notify the user
                        recyclerView.setVisibility(View.VISIBLE);
                        Toast.makeText(VideoTagsGet.this, "Already fetched this URL", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(VideoTagsGet.this, "Invalid url", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    videoTags.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(VideoTagsGet.this, "please enter correct url", Toast.LENGTH_SHORT).show();
//                    recyclerView.setVisibility(View.GONE);
                }

            }
        });

//        copy.setOnClickListener(v -> {
//            Set<String> selectedTags = tagsAdaptor.getSelectedTags();
//            if (!selectedTags.isEmpty()) {
//                copyToClipboard(String.join(", ", selectedTags));
//                Log.e("copy tags",":="+selectedTags);
//                Toast.makeText(this, "Copied selected tags", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No tags selected", Toast.LENGTH_SHORT).show();
//            }
//        });

        copy.setOnClickListener(view -> {
            if (tagsAdaptor == null || tagsAdaptor.getItemCount() == 0) {
                // No data loaded, show toast message
                Toast.makeText(VideoTagsGet.this, "No data to copy. Please enter a valid URL and load the tags.", Toast.LENGTH_SHORT).show();
            } else {
                // Logic for copying selected tags goes here
                Set<String> selectedTags = tagsAdaptor.getSelectedTags();
                if (selectedTags.isEmpty()) {
                    Toast.makeText(VideoTagsGet.this, "Please select tags to copy", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with copying the selected tags
                    copyToClipboard(selectedTags.toString());
                    Toast.makeText(VideoTagsGet.this, "Selected tags copied", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        selectAll.setOnClickListener(v -> {
//            tagsAdaptor.selectAllTags();
////            Log.e("All tags selected",":=");
//            Toast.makeText(this, "All tags selected", Toast.LENGTH_SHORT).show();
//        });

        selectAll.setOnClickListener(view -> {
            if (tagsAdaptor != null && tagsAdaptor.getItemCount() > 0) {
                tagsAdaptor.selectAllTags();
                Toast.makeText(VideoTagsGet.this, "All tags selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(VideoTagsGet.this, "No tags to select. Please load tags first.", Toast.LENGTH_SHORT).show();
            }
        });
        copyAll.setOnClickListener(v -> {
            if (tagsAdaptor != null && tagsAdaptor.getItemCount() > 0) { // Check if tags are available
                tagsAdaptor.selectAllTags(); // Select all tags first
                Set<String> allTags = tagsAdaptor.getSelectedTags(); // Get the selected tags

                if (!allTags.isEmpty()) { // Check if any tags were selected
                    copyToClipboard(String.join(", ", allTags)); // Copy tags to clipboard
                    Log.e("All copied tags", ":= " + allTags); // Log copied tags
                    Toast.makeText(this, "Copied all tags", Toast.LENGTH_SHORT).show(); // Show success toast
                } else {
                    Log.e("No tags selected to copy", ":= " + allTags); // Log empty tags case
                    Toast.makeText(this, "No tags available to copy", Toast.LENGTH_SHORT).show(); // Show error toast
                }
            } else {
                // Handle case where there are no tags loaded
                Log.e("No tags loaded", ":= No tags to copy");
                Toast.makeText(this, "No tags available to copy. Please load tags first.", Toast.LENGTH_SHORT).show();
            }
        });

//        copyAll.setOnClickListener(view -> {
//            if (tagsAdaptor == null || tagsAdaptor.getItemCount() == 0) {
//                Toast.makeText(VideoTagsGet.this, "No data to copy. Please load tags first.", Toast.LENGTH_SHORT).show();
//            } else {
//                Set<String> allTags = tagsAdaptor.getSelectedTags();
//                copyToClipboard(allTags.toString());
//                Toast.makeText(VideoTagsGet.this, "All tags copied", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    private void copyToClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(VideoTagsGet.this.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }

    public boolean IsValidUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return false;
        }
        // Check if there are spaces or multiple URLs (we allow only one URL)
        if (url.contains(" ") || url.contains(",")) {
            return false;
        }
        // Check if the URL is a valid web URL
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            return false;
        }

        // Check if the URL matches the YouTube URL pattern
        Pattern pattern = Pattern.compile(YOUTUBE_URL_PATTERN);
//        Pattern pattern = Pattern.compile(YOUTUBE_URL_PATTERNNEW);
        return pattern.matcher(url).matches();
    }
//    public boolean isYouTubeVideoUrl(String url){
//        Pattern pattern = Pattern.compile(YOUTUBE_VIDEO_URL_PATTERN);
//        Matcher matcher = pattern.matcher(url);
//        return matcher.matches();
//    }
//    public boolean isYouTubeShortsUrl(String url){
//        Pattern pattern = Pattern.compile(YOUTUBE_SHORTS_URL_PATTERN);
//        Matcher matcher = pattern.matcher(url);
//        return matcher.matches();
//    }

//    public String getVideoIdFromUrl(String url) {
//
//        Log.e("getid methods", ":= call hua");
//
//        String regex = "v=([a-zA-Z0-9_-]{11})";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(url);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            recyclerView.setVisibility(View.GONE);
////            Toast.makeText(this, "please enter correct url", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//    }

    public String getVideoIdFromUrl(String url) {
        Log.e("getid methods", ":= call hua");

        // Improved regex to capture video IDs from different URL formats
        String regex = "(?:youtube\\.com\\/.*[?&]v=|youtu\\.be\\/|youtube\\.com\\/shorts\\/)?([\\w\\-]{11})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "Please enter a correct URL", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public void fetchVideoTags(String id) {

        progressDialog.show();

        Log.e("getTags methods ", ";= call hua");
        Apiresponse apiresponse = RetrofitDataClass.getRetrofit().create(Apiresponse.class);
        Call<VideoTagResponse> getResponse = apiresponse.getVideoDetails("snippet", id, MY_KEY);
        Log.e("request", "Details := " + getResponse.request());
        getResponse.enqueue(new Callback<VideoTagResponse>() {
            @Override
            public void onResponse(Call<VideoTagResponse> call, Response<VideoTagResponse> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    progressDialog.dismiss();
                    VideoTagResponse videoTagResponse = response.body();
//                    Log.e("themnails",":="+videoTagResponse.getItems().get(0).getSnippet().getThumbnails().getMedium().getUrl());
                    if (videoTagResponse != null && videoTagResponse.getItems().size() > 0) {
                        // Get the tags from the response
                        VideoTagResponse.Items item = videoTagResponse.getItems().get(0);

//                        List<VideoTagResponse.Items> items = videoTagResponse.getItems();

                        if (item.getSnippet() != null && item.getSnippet().getTags() != null) {
                            List<String> tags = item.getSnippet().getTags();
                            // Set the adapter with the tags list
                            tagsAdaptor = new TagsAdaptor(tags,VideoTagsGet.this);
                            recyclerView.setAdapter(tagsAdaptor);
                        }
//                        if(items != null ){
//                            tagsAdaptor = new TagsAdaptor(getApplicationContext(),items,VideoTagsGet.this);
//                            recyclerView.setAdapter(tagsAdaptor);
//                        }
                        else {
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(VideoTagsGet.this, "no data available"+item.getSnippet().getTags(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(VideoTagsGet.this, "No tags found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(VideoTagsGet.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }

//                if (response.code() == 200 && response.isSuccessful()) {
//                    VideoTagResponse videoTagResponse = response.body();
//                    if (videoTagResponse != null && videoTagResponse.getItems().size() > 0) {
//                        // Check if the index you are accessing exists in the list
//                        if (videoTagResponse.getItems().size() > 1) {
//                            // Access the second element (index 1) safely
//                            VideoTagResponse.Items item = videoTagResponse.getItems().get(1);
//                            if (item.getSnippet() != null && item.getSnippet().getTags() != null) {
//                                List<String> tags = item.getSnippet().getTags();
//                                // Set the adapter with the tags list
//                                tagsAdaptor = new TagsAdaptor(tags);
//                                recyclerView.setAdapter(tagsAdaptor);
//                            }
//                        } else {
//                            // Handle the case where the list has fewer than 2 elements
//                            Log.e("VideoTagsGet", "List has fewer than 2 items");
//                            Toast.makeText(VideoTagsGet.this, "Not enough items in the response", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    else {
//                        Toast.makeText(VideoTagsGet.this, "No tags found", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(VideoTagsGet.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<VideoTagResponse> call, Throwable t) {
                Toast.makeText(VideoTagsGet.this, "failed response", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTagSelected(String tag) {
        Log.d("Tag Selected", tag);
    }

    @Override
    public void onTagUnselected(String tag) {
        Log.d("Tag Unselected", tag);
    }


    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            if (backToast != null) backToast.cancel();
            super.onBackPressed(); // Exit the app
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}