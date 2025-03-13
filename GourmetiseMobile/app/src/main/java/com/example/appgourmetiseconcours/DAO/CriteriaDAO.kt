package com.example.appgourmetiseconcours.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.appgourmetiseconcours.BakeryHelper

class CriteriaDAO(context: Context) {
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase


    fun getCriteriaIdByTitle(title: String): Int? {
        val cursor = db.query(
            "criteria",
            arrayOf("id"),
            "title = ?",
            arrayOf(title),
            null, null, null
        )
        return if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndex("id"))
        } else {
            null
        }.also {
            cursor.close()
        }
    }


    fun insertCriteria(title: String): Int {
        val values = ContentValues().apply {
            put("title", title)
        }
        val id = db.insert("criteria", null, values)
        return id.toInt()
    }
}
