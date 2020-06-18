package pl.adriankurek.pixabayphotosviewer.database;

import android.app.Application;

import pl.adriankurek.pixabayphotosviewer.models.DaoMaster;
import pl.adriankurek.pixabayphotosviewer.models.DaoSession;

public class GreenDao extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        daoSession = new DaoMaster(new DbOpenHelper(this, "photos.db").getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}