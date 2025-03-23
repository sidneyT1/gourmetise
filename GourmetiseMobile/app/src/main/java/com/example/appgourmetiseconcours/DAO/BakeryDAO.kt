package com.example.appgourmetiseconcours.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.annotation.SuppressLint
import com.example.appgourmetiseconcours.Business.Bakery
import com.example.appgourmetiseconcours.BakeryHelper

class BakeryDAO(private val context: Context) {  // Conserver 'context' comme membre de la classe
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase


    // Insérer une boulangerie
    fun insertBakery(
        siren: String,
        name: String,
        street: String,
        postcode: String,
        city: String,
        phonenumber: String,
        contactname: String,
        description: String?,
        ticketNum: String?,
        evaluationDate: String?
    ) {
        val values = ContentValues().apply {
            put("siren", siren)
            put("name", name)
            put("street", street)
            put("postcode", postcode)
            put("city", city)
            put("phonenumber", phonenumber)
            put("contactname", contactname)
            put("description", description)
            put("ticketNum", ticketNum)
            put("evaluationDate", evaluationDate)
        }

        db.insert("bakery", null, values)
    }


    // Vider la table des boulangeries
    fun clearAllBakeries() {
        db.delete("bakery", null, null)
    }

    // Obtenir toutes les boulangeries
    @SuppressLint("Range")
    fun getAllBakeries(): MutableList<Bakery> {
        val lesBakeries = mutableListOf<Bakery>()

        val curseur = db.rawQuery(
            "SELECT siren, name, street, postcode, city, phonenumber, contactname, description, ticketNum, evaluationDate FROM bakery",
            null
        )

        curseur.moveToFirst()
        while (!curseur.isAfterLast) {
            val siren = curseur.getString(curseur.getColumnIndex("siren"))
            val name = curseur.getString(curseur.getColumnIndex("name"))
            val street = curseur.getString(curseur.getColumnIndex("street"))
            val postcode = curseur.getString(curseur.getColumnIndex("postcode"))
            val city = curseur.getString(curseur.getColumnIndex("city"))
            val phonenumber = curseur.getString(curseur.getColumnIndex("phonenumber"))
            val contactname = curseur.getString(curseur.getColumnIndex("contactname"))
            val description = curseur.getString(curseur.getColumnIndex("description"))
            val ticketNum = curseur.getString(curseur.getColumnIndex("ticketNum"))  // Nouveau champ
            val evaluationDate = curseur.getString(curseur.getColumnIndex("evaluationDate"))  // Nouveau champ

            val bakery = Bakery(siren, name, street, postcode, city, phonenumber, contactname, description, ticketNum, evaluationDate)

            lesBakeries.add(bakery)

            curseur.moveToNext()
        }

        curseur.close()

        return lesBakeries
    }



    fun updateBakery(
        siren: String,
        name: String,
        street: String,
        postcode: String,
        city: String,
        phonenumber: String,
        contactname: String,
        description: String?,
        ticketNum: String?,
        evaluationDate: String?
    ) {
        val values = ContentValues().apply {
            put("name", name)
            put("street", street)
            put("postcode", postcode)
            put("city", city)
            put("phonenumber", phonenumber)
            put("contactname", contactname)
            put("description", description)
            put("ticketNum", ticketNum)
            put("evaluationDate", evaluationDate)
        }

        db.update("bakery", values, "siren = ?", arrayOf(siren))
    }


    fun getBakeryNameBySiren(siren: String): String {
        val curseur = db.rawQuery(
            "SELECT name FROM bakery WHERE siren = ?",
            arrayOf(siren)
        )

        var name = ""
        if (curseur.moveToFirst()) {
            name = curseur.getString(curseur.getColumnIndex("name"))
        }

        curseur.close()

        return name
    }


    fun updateTicketNum(siren: String, ticketNum: String) {
        val values = ContentValues().apply {
            put("ticketNum", ticketNum)
        }

        db.update("bakery", values, "siren = ?", arrayOf(siren))
    }


    fun isBakeryEvaluated(bakerySiren: String): Boolean {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM note WHERE bakery_siren = ?",
            arrayOf(bakerySiren)
        )

        var isEvaluated = false
        if (cursor.moveToFirst()) {
            isEvaluated = cursor.getInt(0) > 0
        }
        cursor.close()
        return isEvaluated
    }


    fun getBakeryScore(bakerySiren: String): Int {
        val noteDAO = NoteDAO(context)
        return noteDAO.getBakeryScore(bakerySiren)
    }

    fun updateBakeryEvaluation(
        siren: String,
        ticketNum: String,
        evaluationDate: String
    ) {
        val values = ContentValues().apply {
            put("ticketNum", ticketNum)
            put("evaluationDate", evaluationDate)
        }

        db.update("bakery", values, "siren = ?", arrayOf(siren))
    }

    fun isTicketUsed(ticketNum: String): Boolean {
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM bakery WHERE ticketNum = ?",
            arrayOf(ticketNum)
        )

        var isUsed = false
        if (cursor.moveToFirst()) {
            isUsed = cursor.getInt(0) > 0
        }
        cursor.close()
        return isUsed
    }
    fun getAllEvaluationsCount(): Int {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM note", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count
    }



}
