package com.example.appgourmetiseconcours.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.appgourmetiseconcours.BakeryHelper
import com.example.appgourmetiseconcours.Business.Note


class NoteDAO(context: Context) {
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase


    fun insertNote(value: Int, bakerySiren: String, criteriaId: Int) {
        val values = ContentValues().apply {
            put("value", value)
            put("bakery_siren", bakerySiren)
            put("criteria_id", criteriaId)
        }
        db.insert("note", null, values)
    }


    fun getAllNotes(): MutableList<Note> {
        val notes = mutableListOf<Note>()
        val cursor = db.rawQuery(
            "SELECT bakery_siren, criteria_id, value FROM note", null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val note = Note(
                bakerySiren = cursor.getString(cursor.getColumnIndex("bakery_siren")),
                criteriaId = cursor.getInt(cursor.getColumnIndex("criteria_id")),
                value = cursor.getInt(cursor.getColumnIndex("value"))
            )
            notes.add(note)
            cursor.moveToNext()
        }
        cursor.close()
        return notes
    }


    fun clearAllNotes() {
        db.delete("note", null, null)
    }
}
