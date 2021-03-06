package pl.adriankurek.pixabayphotosviewer.repositories;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import pl.adriankurek.pixabayphotosviewer.database.DbController;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class FavoritePhotosRepository {
    private static FavoritePhotosRepository instance;
    private List<PixabayPhoto> photos = new ArrayList<>();

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
        photos =  controller.getAllFavoritePhotos();
        MutableLiveData<List<PixabayPhoto>> data = new MutableLiveData<>();
        data.setValue(photos);
        return data;
    }
}