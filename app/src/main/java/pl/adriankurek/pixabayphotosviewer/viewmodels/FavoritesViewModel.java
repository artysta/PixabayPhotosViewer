package pl.adriankurek.pixabayphotosviewer.viewmodels;


import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.models.FavoritePhotosRepository;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;

public class FavoritesViewModel extends ViewModel {
    private MutableLiveData<List<PixabayPhoto>> photos;
    private FavoritePhotosRepository repository;

    public void init() {
        if (photos != null) return;

        repository = FavoritePhotosRepository.getInstance();
    }

    public void reloadPhotos(Activity activity) {
        photos = repository.getFavoritePhotos(activity);
    }

    public LiveData<List<PixabayPhoto>> getAllFavoritePhotos() {
        return photos;
    }
}