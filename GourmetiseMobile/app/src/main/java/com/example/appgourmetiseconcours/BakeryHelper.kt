package com.example.appgourmetiseconcours

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BakeryHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "gourmetise_db"
        private const val DATABASE_VERSION = 4
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
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS bakery;")
        onCreate(db)
    }
}
