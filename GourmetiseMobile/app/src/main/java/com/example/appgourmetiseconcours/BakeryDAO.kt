package com.example.appgourmetiseconcours

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

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

    // Define methods for updating, deleting, and querying bakeries as needed
}
