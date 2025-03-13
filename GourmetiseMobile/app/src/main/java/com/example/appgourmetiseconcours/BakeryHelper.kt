package com.example.appgourmetiseconcours

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BakeryHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "gourmetise_db"
        private const val DATABASE_VERSION = 8
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
                    "description LONGTEXT DEFAULT NULL," +
                    "ticketNum VARCHAR(100)," +
                    "evaluationDate DATETIME );"
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

        db.execSQL(
            "CREATE TABLE criteria (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR(100) NOT NULL);"

        )
        db.execSQL(
            "CREATE TABLE note (" +
                    "value INTEGER," +
                    "bakery_siren VARCHAR(14), " +
                    "criteria_id INTEGER, " +
                    "PRIMARY KEY (bakery_siren, criteria_id), " +
                    "FOREIGN KEY (bakery_siren) REFERENCES bakery(siren), " +
                    "FOREIGN KEY (criteria_id) REFERENCES criteria(id));"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS bakery;")
        db.execSQL("DROP TABLE IF EXISTS contest_params;")
        db.execSQL("DROP TABLE IF EXISTS criteria;")
        db.execSQL("DROP TABLE IF EXISTS note;")
        onCreate(db)
    }
}
