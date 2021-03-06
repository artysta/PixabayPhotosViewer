package pl.adriankurek.pixabayphotosviewer.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.models.JSONHelper;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class PixabayPhotosRepository {
    private static PixabayPhotosRepository instance;
    private JSONHelper helper;

    public static PixabayPhotosRepository getInstance() {
        if (instance == null) {
            instance = new PixabayPhotosRepository();
        }

        return instance;
    }

    // Get photos from data source.
    public MutableLiveData<List<PixabayPhoto>> getPhotos(Context ctx, String url) {
        helper = new JSONHelper(ctx);
        List<PixabayPhoto> photos = helper.getImagesFromJSONURL(url);
        MutableLiveData<List<PixabayPhoto>> data = new MutableLiveData<>();
        data.setValue(photos);
        return data;
    }

    public void setListener(RequestQueue.RequestFinishedListener<Object> listener) {
        helper.addRequestFinishedListener(listener);
    }
}