package pl.adriankurek.pixabayphotosviewer.models;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;

import java.util.List;

public class PixabayPhotosRepository {
    private static PixabayPhotosRepository instance;

    public static PixabayPhotosRepository getInstance() {
        if (instance == null) {
            instance = new PixabayPhotosRepository();
        }

        return instance;
    }

    // Get photos from data source.
    public MutableLiveData<List<PixabayPhoto>> getPhotos(Context ctx, String url, RequestQueue.RequestFinishedListener listener) {
        JSONHelper helper = new JSONHelper(ctx);
        helper.addRequestFinishedListener(listener);
        List<PixabayPhoto> photos = helper.getImagesFromJSONURL(url);
        MutableLiveData<List<PixabayPhoto>> data = new MutableLiveData<>();
        data.setValue(photos);
        return data;
    }
}