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
        val cursor = db.rawQuery("SELECT bakery_siren, criteria_id, value FROM note", null)

        if (cursor.moveToFirst()) {
            do {
                val bakerySirenIndex = cursor.getColumnIndex("bakery_siren")
                val criteriaIdIndex = cursor.getColumnIndex("criteria_id")
                val valueIndex = cursor.getColumnIndex("value")

                if (bakerySirenIndex != -1 && criteriaIdIndex != -1 && valueIndex != -1) {
                    val note = Note(
                        bakerySiren = cursor.getString(bakerySirenIndex),
                        criteriaId = cursor.getInt(criteriaIdIndex),
                        value = cursor.getInt(valueIndex)
                    )
                    notes.add(note)
                } else {
                    println("Erreur : Une ou plusieurs colonnes manquent dans la requête SQL.")
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
        return notes
    }



    fun clearAllNotes() {

        db.delete("note", null, null)
    }
    fun getBakeryScore(bakerySiren: String): Int {
        val cursor = db.rawQuery(
            "SELECT SUM(value) FROM note WHERE bakery_siren = ?",
            arrayOf(bakerySiren)
        )

        var totalScore = 0
        if (cursor.moveToFirst()) {
            totalScore = cursor.getInt(0)
        }
        cursor.close()
        return totalScore
    }




}
