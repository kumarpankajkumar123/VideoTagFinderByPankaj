package com.example.TagFinder.ModalOfApi;

import java.util.List;

public class TracksAllResponse {
    String href;
    List<Items> items;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public static class Items {
        Tracksall track;

        public Tracksall getTrack() {
            return track;
        }

        public void setTrack(Tracksall track) {
            this.track = track;
        }
    }

    public static class Tracksall {
        String preview_url;
        String name;
        Album album;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public String getPreview_url() {
            return preview_url;
        }

        public void setPreview_url(String preview_url) {
            this.preview_url = preview_url;
        }
    }

    public static class Album {
        List<Images> images;

        public List<Images> getImages() {
            return images;
        }

        public void setImages(List<Images> images) {
            this.images = images;
        }
    }

    public static class Images {
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
