package pl.adriankurek.pixabayphotosviewer.models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.database.DbController;

public class FavoritePhotosRepository {
    private static FavoritePhotosRepository instance;

    public static FavoritePhotosRepository getInstance() {
        if (instance == null) {
            instance = new FavoritePhotosRepository();
        }

        return instance;
    }

    // Get favorite photos from data source.
    public MutableLiveData<List<PixabayPhoto>> getFavoritePhotos(Activity activity) {
        // Get all favorite photos from db.
        DbController controller = new DbController(activity);
        List<PixabayPhoto> photos = controller.getAllFavoritePhotos();
        MutableLiveData<List<PixabayPhoto>> data = new MutableLiveData<>();
        data.setValue(photos);
        return data;
    }
}