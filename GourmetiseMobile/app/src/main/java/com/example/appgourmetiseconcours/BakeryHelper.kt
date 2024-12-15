package com.example.appgourmetiseconcours

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BakeryHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "gourmetise_db"
        private const val DATABASE_VERSION = 5
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE bakery (" +
                    "siren VARCHAR(14) NOT NULL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "street VARCHAR(100) NOT NULL," +
                    "postcode VARCHAR(5) NOT NULL," +
                    "city VARCHAR(20) NOT NULL," +
                    "phonenumber VARCHAR(10) NOT NULL," +
                    "contactname VARCHAR(30) NOT NULL," +
                    "description LONGTEXT DEFAULT NULL);"
        )


        db.execSQL(
            "CREATE TABLE contest_params (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR(255) NOT NULL," +
                    "description LONGTEXT NOT NULL," +
                    "start_registration DATETIME NOT NULL," +
                    "end_registration DATETIME NOT NULL," +
                    "start_evaluation DATETIME NOT NULL," +
                    "end_evaluation DATETIME NOT NULL);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS bakery;")
        db.execSQL("DROP TABLE IF EXISTS contest_params;")
        onCreate(db)
    }
}
