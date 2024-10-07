package com.example.pankajdemo.ModalOfApi;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
//    public TagsAdaptor(List<String> taglist,TagSelectionListener tagSelectionListener) {
////        this.context = context;
//        this.taglist = taglist;
////        for (String tag : taglist) {
////            tagSelectionMap.put(tag, false); // Initially, no tags are selected
////        }
//        this.tagSelectionListener = tagSelectionListener;
//    }

    public TagsAdaptor(Context context, List<VideoTagResponse.Items> videoItems, TagSelectionListener tagSelectionListener) {
        this.context = context;
        this.videoItems = videoItems;
//                for (String tag : taglist) {
//            tagSelectionMap.put(tag, false); // Initially, no tags are selected
//        }
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


//        if (tagsN != null && !tagsN.isEmpty()) {
//            // Join tags into a single string separated by commas or any other delimiter
//            String tagsString = String.join(", ", tagsN);
//            holder.tags.setText(tagsString);
//        } else {
//            holder.tags.setText("No tags available");
//        }
//        if (tagsN != null && !tagsN.isEmpty()) {
////            holder.tags.setText(tagsN.toString().replaceAll("[\\[\\]]", ""));
//
//        } else {
//            holder.tags.setText("No tags available");
//        }

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
                break;  // We've found the correct item, no need to keep looping
            }
        }

//        holder.checkbox.setChecked(tagSelectionMap.get(tags));

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

//        holder.itemView.setOnClickListener(v -> {
//            if (selectedTags.contains(tag)) {
//                selectedTags.remove(tag);
//                tagSelectionListener.onTagUnselected(tag);
//            } else {
//                selectedTags.add(tag);
//                tagSelectionListener.onTagSelected(tag);
//            }
//            holder.checkbox.setChecked(selectedTags.contains(tag));
//        });

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


//        holder.checkbox.setOnCheckedChangeListener(null);
//        holder.checkbox.setChecked(selectedTags.contains(tags));
//
//
//        holder.itemView.setOnClickListener(v -> {
//            if (selectedTags.contains(tags)) {
//                selectedTags.remove(tags);
//                tagSelectionListener.onTagUnselected(tags);
//            } else {
//                selectedTags.add(tags);
//                tagSelectionListener.onTagSelected(tags);
//            }
//            holder.checkbox.setChecked(selectedTags.contains(tags));
//        });
//
//        holder.checkbox.setOnCheckedChangeListener((checkbox, isChecked) -> {
//            if (isChecked) {
//                selectedTags.add(tags);
//            } else {
//                selectedTags.remove(tags);
//            }
//        });
//    }

    @Override
    public int getItemCount() {
//        return taglist.size();
        return allTags.size();
    }

    //    public Map<String, Boolean> getSelectedTags() {
//        return tagSelectionMap;
//    }
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
        dialog.show();
    }
}
