package pl.adriankurek.pixabayphotosviewer.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;
import pl.adriankurek.pixabayphotosviewer.repositories.PixabayPhotosRepository;

public class PhotoViewModel extends ViewModel {
    private MutableLiveData<List<PixabayPhoto>> photos;
    private PixabayPhotosRepository repository;

    public void init() {
        if (photos != null) return;

        repository = PixabayPhotosRepository.getInstance();
    }

    public void reloadPhotos(Context ctx, String jsonURL, RequestQueue.RequestFinishedListener listener) {
        photos = repository.getPhotos(ctx, jsonURL, listener);
    }

    public LiveData<List<PixabayPhoto>> getPhotos() {
        return photos;
    }
}