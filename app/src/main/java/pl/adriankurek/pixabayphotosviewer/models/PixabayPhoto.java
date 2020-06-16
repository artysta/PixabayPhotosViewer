package pl.adriankurek.pixabayphotosviewer.models;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

// Class that describes images from pixabay.com (with just 2 fields at this moment)
// TODO add rest of fields
@Entity(nameInDb = "photos")
public class PixabayPhoto {
    @Id
    private long id;
    private String previewURL;

    // Tags can be splitted to array by comma.
    private String tags;
    private String webformatURL;
    private String user;

    @Generated(hash = 371426933)
    public PixabayPhoto(long id, String previewURL, String tags, String webformatURL, String user) {
        this.id = id;
        this.previewURL = previewURL;
        this.tags = tags;
        this.webformatURL = webformatURL;
        this.user = user;
    }

    @Generated(hash = 578507231)
    public PixabayPhoto() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
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
