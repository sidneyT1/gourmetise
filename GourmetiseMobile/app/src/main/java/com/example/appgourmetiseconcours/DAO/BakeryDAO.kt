package com.example.appgourmetiseconcours.DAO

import com.example.appgourmetiseconcours.Business.Evaluation
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.annotation.SuppressLint
import com.example.appgourmetiseconcours.Business.Bakery
import com.example.appgourmetiseconcours.BakeryHelper
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull


class BakeryDAO(private val context: Context) {
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase



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



    fun clearAllBakeries() {
        db.delete("bakery", null, null)
    }


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


        val groupCount = count / 3

        return groupCount
    }

    fun exportEvaluationsToServer() {

        val cursor = db.rawQuery(
            """
        SELECT bakery_siren, ticketNum, evaluationDate, SUM(value) AS totalScore
        FROM note
        INNER JOIN bakery ON bakery_siren = siren
        GROUP BY bakery_siren
        """, null
        )

        val evaluations = mutableListOf<Evaluation>()

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val bakerySiren = cursor.getString(cursor.getColumnIndex("bakery_siren"))
            val ticketNum = cursor.getString(cursor.getColumnIndex("ticketNum"))
            val evaluationDate = cursor.getString(cursor.getColumnIndex("evaluationDate"))
            val totalScore = cursor.getInt(cursor.getColumnIndex("totalScore"))


            evaluations.add(Evaluation(bakerySiren, totalScore, evaluationDate ?: "", ticketNum))

            cursor.moveToNext()
        }

        cursor.close()


        val jsonEvaluations = JSONArray()


        for (evaluation in evaluations) {
            val jsonEvaluation = JSONObject().apply {
                put("ticketNum", evaluation.ticketNum)
                put("score", evaluation.score)
                put("evaluationDate", evaluation.evaluation_date)
                put("siren", evaluation.bakery_siren)
            }
            jsonEvaluations.put(jsonEvaluation)
        }


        val jsonBody = JSONObject().apply {
            put("evaluations", jsonEvaluations)
        }


        val client = OkHttpClient()
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonBody.toString()
        )
        println("JSON envoyé: $jsonBody")

        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/export/evaluations")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseBody = response.body?.string()
                    if (!response.isSuccessful) {
                        println("Erreur lors de l'exportation: ${response.code}, Réponse: $responseBody")
                    } else {
                        println("Évaluations exportées avec succès")
                        clearEvaluationsAfterExport()
                    }
                }
            }
        })
    }




    private fun clearEvaluationsAfterExport() {
        val db = BakeryHelper(context).writableDatabase
        db.delete("note", null, null)
    }

    fun getTicketAndDateBySiren(siren: String): Pair<String?, String?> {
        val cursor = db.rawQuery(
            "SELECT ticketNum, evaluationDate FROM bakery WHERE siren = ?",
            arrayOf(siren)
        )

        var ticketNum: String? = null
        var evaluationDate: String? = null

        if (cursor.moveToFirst()) {
            ticketNum = cursor.getString(cursor.getColumnIndex("ticketNum"))
            evaluationDate = cursor.getString(cursor.getColumnIndex("evaluationDate"))
        }

        cursor.close()
        return Pair(ticketNum, evaluationDate)
    }


    private fun insertEvaluation(evaluation: Evaluation) {
        val values = ContentValues().apply {
            put("bakery_siren", evaluation.bakery_siren)
            put("score", evaluation.score)
            put("evaluation_date", evaluation.evaluation_date)
        }


        db.insert("evaluation", null, values)
    }



}
