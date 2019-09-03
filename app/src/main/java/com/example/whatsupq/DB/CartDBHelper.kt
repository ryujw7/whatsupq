package com.example.whatsupq.DB

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CartDBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "DB_CART", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS CART_ESSENTIAL (PRODUCT_ID VARCHAR(20) PRIMARY KEY NOT NULL, AMOUNT INTEGER);")
        db!!.execSQL("CREATE TABLE IF NOT EXISTS CART_BOX_THEME (PRODUCT_ID VARCHAR(20) PRIMARY KEY NOT NULL, AMOUNT INTEGER);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun addToCart(cart : String ,product_id: String, amount: Int) {
        val db = writableDatabase
        db.execSQL("INSERT INTO $cart VALUES('$product_id' ,$amount) ")
    }

    fun removeAtCart(cart : String, product_id: String) {
        val db = writableDatabase
        db.execSQL("DELETE FROM $cart WHERE PRODUCT_ID = '$product_id'")
    }
    fun updateAmount(cart : String, product_id: String, amount: Int) {
        val db = writableDatabase
        db.execSQL("UPDATE CART_ESSENTIAL SET AMOUNT = $amount WHERE PRODUCT_ID = '$product_id'")
    }

    fun getCartProduct(cart: String, specific : String? = null): Cursor? {
        val db = readableDatabase
        var cursor : Cursor?
        if(specific == null){
            cursor = db.rawQuery("SELECT * FROM $cart", null)
        }
        else {
            cursor = db.rawQuery("SELECT * FROM $cart WHERE PRODUCT_ID = '$specific'", null)
        }

        return cursor
    }

}