package pl.adriankurek.pixabayphotosviewer.models;

import androidx.annotation.NonNull;

// Class that describes images from pixabay.com (with just 2 fields at this moment)
// TODO add rest of fields
public class PixabayPhoto {
    private int id;
    private String previewURL;
    private String[] tags;
    private String webformatURL;
    private String user;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("[%s %s] previewURL: %s.", this.getClass().getSimpleName(), id, previewURL);
    }
}
