package com.example.appgourmetiseconcours;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BakeryDAO {
    private SQLiteDatabase db;

    public BakeryDAO(Context context) {
        BakeryHelper helper = new BakeryHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insertBakery(String name, String street, String postcode, String city, String phonenumber, String contactname, String description) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("street", street);
        values.put("postcode", postcode);
        values.put("city", city);
        values.put("phonenumber", phonenumber);
        values.put("contactname", contactname);
        values.put("description", description);
        db.insert("bakery", null, values);
    }

    // Define methods for updating, deleting, and querying bakeries as needed
}
