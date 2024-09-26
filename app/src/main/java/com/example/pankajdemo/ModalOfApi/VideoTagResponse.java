package com.example.pankajdemo.ModalOfApi;

import java.util.List;

public class VideoTagResponse {

    List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public class Items{
        Snippet snippet;

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }
    }

    public  class Snippet{
        private Thumbnails thumbnails;
        List<String> tags;

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(Thumbnails thumbnails) {
            this.thumbnails = thumbnails;
        }
    }


    public class Thumbnails {
        private ThumbnailItem defaultThumbnail;
        private ThumbnailItem medium;
        private ThumbnailItem high;
        private ThumbnailItem standard;
        private ThumbnailItem maxres;

        // Getters and Setters
        public ThumbnailItem getDefaultThumbnail() { return defaultThumbnail; }
        public void setDefaultThumbnail(ThumbnailItem defaultThumbnail) { this.defaultThumbnail = defaultThumbnail; }
        public ThumbnailItem getMedium() { return medium; }
        public void setMedium(ThumbnailItem medium) { this.medium = medium; }
        public ThumbnailItem getHigh() { return high; }
        public void setHigh(ThumbnailItem high) { this.high = high; }
        public ThumbnailItem getStandard() { return standard; }
        public void setStandard(ThumbnailItem standard) { this.standard = standard; }
        public ThumbnailItem getMaxres() { return maxres; }
        public void setMaxres(ThumbnailItem maxres) { this.maxres = maxres; }
    }
    public class ThumbnailItem {
        private String url;
        private int width;
        private int height;

        // Getters and Setters
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public int getWidth() { return width; }
        public void setWidth(int width) { this.width = width; }
        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }
    }


}
