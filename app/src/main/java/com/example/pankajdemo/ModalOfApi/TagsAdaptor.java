package com.example.pankajdemo.ModalOfApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pankajdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagsAdaptor extends RecyclerView.Adapter<TagsAdaptor.myViewHolder> {

    Context context;
    List<String> taglist;
    private List<VideoTagResponse.Items> videoItems;
    //    private final Map<String, Boolean> tagSelectionMap = new HashMap<>();
    private final Set<String> selectedTags = new HashSet<>();


    private final TagSelectionListener tagSelectionListener;

    public interface TagSelectionListener {
        void onTagSelected(String tag);

        void onTagUnselected(String tag);
    }
    public TagsAdaptor(List<String> taglist,TagSelectionListener tagSelectionListener) {
//        this.context = context;
        this.taglist = taglist;
//        for (String tag : taglist) {
//            tagSelectionMap.put(tag, false); // Initially, no tags are selected
//        }
        this.tagSelectionListener = tagSelectionListener;
    }

//    public TagsAdaptor(Context context, List<VideoTagResponse.Items> videoItems, TagSelectionListener tagSelectionListener) {
//        this.context = context;
//        this.videoItems = videoItems;
////                for (String tag : taglist) {
////            tagSelectionMap.put(tag, false); // Initially, no tags are selected
////        }
//        this.tagSelectionListener = tagSelectionListener;
//    }

    @NonNull
    @Override
    public TagsAdaptor.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designoftags, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdaptor.myViewHolder holder, int position) {


        String tags = taglist.get(position);
        holder.tags.setText(tags);
        holder.imageD.setVisibility(View.GONE);

//        VideoTagResponse.Items item = videoItems.get(position);
//        List<String> tagsN = item.getSnippet().getTags();
//        if (tagsN != null && !tagsN.isEmpty()) {
//            holder.tags.setText(tagsN.toString().replaceAll("[\\[\\]]", "")); // Show tags as comma-separated string
//        } else {
//            holder.tags.setText("No tags available");
//        }
//
//        String thumbnailUrl = item.getSnippet().getThumbnails().getMedium().getUrl();
//        if(thumbnailUrl.isEmpty()){
//            Toast.makeText(context, "url not found", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Glide.with(context)
//                    .load(thumbnailUrl)
////                .placeholder(R.drawable.placeholder_image) // Set placeholder image until the actual image is loaded
//                    .into(holder.imageD);
//        }

//        holder.checkbox.setChecked(tagSelectionMap.get(tags));
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(selectedTags.contains(tags));


        holder.itemView.setOnClickListener(v -> {
            if (selectedTags.contains(tags)) {
                selectedTags.remove(tags);
                tagSelectionListener.onTagUnselected(tags);
            } else {
                selectedTags.add(tags);
                tagSelectionListener.onTagSelected(tags);
            }
            holder.checkbox.setChecked(selectedTags.contains(tags));
        });

        holder.checkbox.setOnCheckedChangeListener((checkbox, isChecked) -> {
            if (isChecked) {
                selectedTags.add(tags);
            } else {
                selectedTags.remove(tags);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taglist.size();
    }

    //    public Map<String, Boolean> getSelectedTags() {
//        return tagSelectionMap;
//    }
    public Set<String> getSelectedTags() {
        return selectedTags;
    }

    public void selectAllTags() {
        selectedTags.clear();
        selectedTags.addAll(taglist);
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

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tags = itemView.findViewById(R.id.tags);
            checkbox = itemView.findViewById(R.id.checkbox);
            imageD = itemView.findViewById(R.id.imageD);
        }
    }
}
