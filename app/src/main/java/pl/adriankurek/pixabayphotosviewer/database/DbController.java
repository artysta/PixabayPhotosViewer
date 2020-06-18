package pl.adriankurek.pixabayphotosviewer.database;

import android.app.Activity;

import java.util.List;

import pl.adriankurek.pixabayphotosviewer.models.PixabayPhoto;
import pl.adriankurek.pixabayphotosviewer.models.PixabayPhotoDao;

public class DbController {
    private PixabayPhotoDao photoDao;
    private Activity activity;

    public DbController(Activity activity) {
        this.activity = activity;
        this.photoDao = getPixabayPhotoDao();
    }

    private PixabayPhotoDao getPixabayPhotoDao() {
        return ((GreenDao) activity.getApplication()).getDaoSession().getPixabayPhotoDao();
    }

    public List<PixabayPhoto> getAllFavoritePhotos() {
        return photoDao.loadAll();
    }

    public boolean isOnFavoriteList(long id) {
        return photoDao.queryBuilder().where(PixabayPhotoDao.Properties.Id.eq(id)).list().size() != 0;
    }

    public void addNewFavoritePhoto(PixabayPhoto photo) {
        photoDao.insert(photo);
    }

    public void removePhotoFromFavorites(PixabayPhoto photo) {
        photoDao.delete(photo);
    }
}