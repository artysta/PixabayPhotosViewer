package pl.adriankurek.pixabayphotosviewer.models;

import androidx.annotation.NonNull;

// Class that describes images from pixabay.com (with just 2 fields at this moment)
// TODO add rest of fields
public class PixabayImage {
    private int id;
    private String previewURL;

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

    @NonNull
    @Override
    public String toString() {
        return String.format("[%s %s] previewURL: %s.", this.getClass().getSimpleName(), id, previewURL);
    }
}
