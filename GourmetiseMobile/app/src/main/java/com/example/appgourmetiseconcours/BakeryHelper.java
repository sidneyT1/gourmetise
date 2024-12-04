package com.example.appgourmetiseconcours;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BakeryHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gourmetise_db";
    private static final int DATABASE_VERSION = 1;

    public BakeryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bakery ("
                + "name VARCHAR(50) NOT NULL,"
                + "street VARCHAR(100) NOT NULL,"
                + "postcode VARCHAR(5) NOT NULL,"
                + "city VARCHAR(20) NOT NULL,"
                + "phonenumber VARCHAR(10) NOT NULL,"
                + "contactname VARCHAR(30) NOT NULL,"
                + "description LONGTEXT DEFAULT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bakery;");
        onCreate(db);
    }
}
