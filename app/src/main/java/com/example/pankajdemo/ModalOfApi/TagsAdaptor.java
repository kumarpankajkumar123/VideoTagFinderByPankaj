package com.example.pankajdemo.ModalOfApi;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.pankajdemo.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagsAdaptor extends RecyclerView.Adapter<TagsAdaptor.myViewHolder> {


    Context context;
    List<String> taglist;
    List<String> tagsN;
    private List<String> allTags = new ArrayList<>();
    private List<VideoTagResponse.Items> videoItems;
    //    private final Map<String, Boolean> tagSelectionMap = new HashMap<>();
    private final Set<String> selectedTags = new HashSet<>();


    private final TagSelectionListener tagSelectionListener;

    public interface TagSelectionListener {
        void onTagSelected(String tag);

        void onTagUnselected(String tag);
    }

    public TagsAdaptor(Context context, List<VideoTagResponse.Items> videoItems, TagSelectionListener tagSelectionListener) {
        this.context = context;
        this.videoItems = videoItems;
        for (VideoTagResponse.Items item : videoItems) {
            List<String> tags = item.getSnippet().getTags();
            if (tags != null) {
                allTags.addAll(tags);
            }
        }
        this.tagSelectionListener = tagSelectionListener;
    }

    @NonNull
    @Override
    public TagsAdaptor.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designoftags, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdaptor.myViewHolder holder, int position) {


//        String tags = taglist.get(position);
//        holder.tags.setText(tags);
//        holder.imageD.setVisibility(View.GONE);

        String tag = allTags.get(position);
        holder.tags.setText(tag);



        for (VideoTagResponse.Items item : videoItems) {
            List<String> tags = item.getSnippet().getTags();
            if (tags != null && tags.contains(tag)) {
                String thumbnailUrl = item.getSnippet().getThumbnails().getMedium().getUrl();
                if (thumbnailUrl.isEmpty()) {
                    holder.imageD.setVisibility(View.GONE);
                    Toast.makeText(context, "url not found", Toast.LENGTH_SHORT).show();
                } else {
                    Glide.with(context)
                            .load(thumbnailUrl)
                            .placeholder(R.drawable.person_new)
                            .into(holder.imageD);
                }
                break;
            }
        }


        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(selectedTags.contains(tag));
        holder.cardM.setOnClickListener(v -> {
            if (selectedTags.contains(tag)) {
                selectedTags.remove(tag);
                tagSelectionListener.onTagUnselected(tag);
            } else {
                selectedTags.add(tag);
                tagSelectionListener.onTagSelected(tag);
            }
            holder.checkbox.setChecked(selectedTags.contains(tag));
        });

        holder.imageD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (VideoTagResponse.Items item : videoItems) {
                    List<String> tags = item.getSnippet().getTags();
                    if (tags != null && tags.contains(tag)) {
                        String thumbnailUrl1 = item.getSnippet().getThumbnails().getMedium().getUrl();
                        String tittle = item.getSnippet().getTittle().toString();
                        Log.e("the tittle",""+tittle);
                        if (thumbnailUrl1.isEmpty()) {
                            holder.imageD.setVisibility(View.GONE);
                            Toast.makeText(context, "url not found", Toast.LENGTH_SHORT).show();
                        }
                        else if(tittle.isEmpty()){
                            Toast.makeText(context, "tittle is not found", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("the url",""+thumbnailUrl1);
                            Log.e("the tittle",""+tittle);
                            showZoomableImageDialog(thumbnailUrl1,tittle);
                        }
                        break;  // We've found the correct item, no need to keep looping
                    }
                }
            }
        });

        holder.checkbox.setOnCheckedChangeListener((checkbox, isChecked) -> {
            if (isChecked) {
                selectedTags.add(tag);
            } else {
                selectedTags.remove(tag);
            }
        });
    }
    @Override
    public int getItemCount() {
//        return taglist.size();
        return allTags.size();
    }

    public Set<String> getSelectedTags() {
        return selectedTags;
    }

    public void selectAllTags() {
        selectedTags.clear();
//        selectedTags.addAll(taglist);
        selectedTags.addAll(allTags);
        notifyDataSetChanged();
    }

    public void clearSelectedTags() {
        selectedTags.clear();
        notifyDataSetChanged();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tags;
        CheckBox checkbox;
        ImageView imageD;
        MaterialCardView cardM;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tags = itemView.findViewById(R.id.tags);
            checkbox = itemView.findViewById(R.id.checkbox);
            imageD = itemView.findViewById(R.id.circularImage);
            cardM = itemView.findViewById(R.id.cardM);
        }
    }

    private void showZoomableImageDialog(String imageUrl,String tittle) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_image_zommable);
        TouchImageView imageView = dialog.findViewById(R.id.zoomable_image);
        TextView textView = dialog.findViewById(R.id.tittle);
        Button cancel = dialog.findViewById(R.id.cancel);
        Button download = dialog.findViewById(R.id.download);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
        textView.setText(tittle);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startImageDownload(context, imageUrl, "youtube");
            }
        });

        dialog.show();
    }
    private void startImageDownload(Context context, String imageUrl, String imageName) {
        // Create a ProgressDialog to show the download progress
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading image...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();

        // Create the request for DownloadManager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setTitle("Downloading " + imageName);
        request.setDescription("Downloading in progress...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, imageName + ".jpg"); // Use ".jpg" extension
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        // Get DownloadManager system service
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);

        // Register a BroadcastReceiver to listen for when the download completes
        BroadcastReceiver onCompleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == id) {
                    progressDialog.dismiss(); // Dismiss on success
                    Toast.makeText(context, "Image downloaded successfully!", Toast.LENGTH_SHORT).show();
                    context.unregisterReceiver(this); // Unregister the receiver
                }
            }
        };

        // Register the receiver for download complete action
        context.registerReceiver(onCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // Update progress periodically
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    @SuppressLint("Range") int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (bytesTotal > 0) {
                        int progress = (int) ((bytesDownloaded * 100L) / bytesTotal);
                        progressDialog.setProgress(progress);
                    }
                }
                cursor.close();
                if (progressDialog.isShowing()) {
                    handler.postDelayed(this, 1000); // Repeat every second
                }
            }
        };
        handler.postDelayed(runnable, 0); // Start the update loop
    }
}


