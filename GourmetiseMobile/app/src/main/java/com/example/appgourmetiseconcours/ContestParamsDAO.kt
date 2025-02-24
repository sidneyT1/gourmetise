package com.example.appgourmetiseconcours

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.annotation.SuppressLint

class ContestParamsDAO(context: Context) {
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase

    fun insertContestParams(
        title: String,
        description: String,
        startRegistration: String,
        endRegistration: String,
        startEvaluation: String,
        endEvaluation: String
    ) {
        val values = ContentValues().apply {
            put("title", title)
            put("description", description)
            put("start_registration", startRegistration)
            put("end_registration", endRegistration)
            put("start_evaluation", startEvaluation)
            put("end_evaluation", endEvaluation)
        }
        db.insert("contest_params", null, values)
    }

    fun clearAllContestParams() {
        db.delete("contest_params", null, null)
    }

    @SuppressLint("Range")
    fun getContestParams(): ContestParams? {
        val cursor = db.rawQuery("SELECT * FROM contest_params WHERE id = 1", null)
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val startRegistration = cursor.getString(cursor.getColumnIndex("start_registration"))
            val endRegistration = cursor.getString(cursor.getColumnIndex("end_registration"))
            val startEvaluation = cursor.getString(cursor.getColumnIndex("start_evaluation"))
            val endEvaluation = cursor.getString(cursor.getColumnIndex("end_evaluation"))

            cursor.close()
            ContestParams(
                title = title,
                description = description,
                startRegistration = startRegistration,
                endRegistration = endRegistration,
                startEvaluation = startEvaluation,
                endEvaluation = endEvaluation
            )
        } else {
            cursor.close()
            null
        }
    }
}
