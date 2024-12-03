package com.example.appgourmetiseconcours.ui.theme;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


public class Gourmetise {
}
class Gourmetise (context : Context)
 : SQLiteOpenHelper (context, "baseTriathlon.db", null, 1){
    override fun onCreate(db: SQLiteDatabase) {
        // création des tables de la base embarquée
        // création de la table CONCURRENT
        db.execSQL("CREATE TABLE Concurrent ("
                + "dossard TEXT NOT NULL PRIMARY KEY,"
                + "nom TEXT NOT NULL,"
                + "genre TEXT NOT NULL,"
                + "categorie TEXT NOT NULL,"
                + "natation REAL NOT NULL,"
                + "cyclisme REAL NOT NULL,"
                + "course REAL NOT NULL);");
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Concurrent;");
        onCreate(db);
    }
}