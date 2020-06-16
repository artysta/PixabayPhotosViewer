package pl.adriankurek.pixabayphotosviewer.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pl.adriankurek.pixabayphotosviewer.models.DaoMaster;

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context ctx, String name) {
        super(ctx, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            // Here you can insert code that will be executed when the version of the database changes
            case 1:
            case 2:
            case 3:
        }
    }
}