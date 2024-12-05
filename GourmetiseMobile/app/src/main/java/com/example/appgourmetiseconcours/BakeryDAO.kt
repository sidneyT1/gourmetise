package com.example.appgourmetiseconcours

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.annotation.SuppressLint

class BakeryDAO(context: Context) {
    private val db: SQLiteDatabase = BakeryHelper(context).writableDatabase

    fun insertBakery(siren: String, name: String, street: String, postcode: String, city: String, phonenumber: String, contactname: String, description: String?) {
        val values = ContentValues().apply {
            put("siren", siren)
            put("name", name)
            put("street", street)
            put("postcode", postcode)
            put("city", city)
            put("phonenumber", phonenumber)
            put("contactname", contactname)
            put("description", description)
        }
        db.insert("bakery", null, values)
    }

    fun clearAllBakeries() {
        db.delete("bakery", null, null)
    }

    @SuppressLint("Range")
    fun getAllBakeries(): MutableList<Bakery> {
        val lesBakeries = mutableListOf<Bakery>()


        val curseur = db.rawQuery("SELECT siren, name, street, postcode, city, phonenumber, contactname, description FROM bakery", null)


        curseur.moveToFirst()
        while (!curseur.isAfterLast()) {
            val siren = curseur.getString(curseur.getColumnIndex("siren"))
            val name = curseur.getString(curseur.getColumnIndex("name"))
            val street = curseur.getString(curseur.getColumnIndex("street"))
            val postcode = curseur.getString(curseur.getColumnIndex("postcode"))
            val city = curseur.getString(curseur.getColumnIndex("city"))
            val phonenumber = curseur.getString(curseur.getColumnIndex("phonenumber"))
            val contactname = curseur.getString(curseur.getColumnIndex("contactname"))
            val description = curseur.getString(curseur.getColumnIndex("description"))

            val bakery = Bakery(siren, name, street, postcode, city, phonenumber, contactname, description)


            lesBakeries.add(bakery)


            curseur.moveToNext()
        }


        curseur.close()

        return lesBakeries
    }



}
