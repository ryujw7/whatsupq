package com.example.whatsupq.DB

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CartDBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, "DB_CART", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS $CART_ESSENTIAL (PRODUCT_ID VARCHAR(20) PRIMARY KEY NOT NULL, NAME VARCHAR(50), PRICE INTEGER, AMOUNT INTEGER, FREQUENCY INTEGER);")
        db!!.execSQL("CREATE TABLE IF NOT EXISTS $CART_BOX_THEME (PRODUCT_ID VARCHAR(20) PRIMARY KEY NOT NULL, NAME VARCHAR(50), PRICE INTEGER, AMOUNT INTEGER, FREQUENCY INTEGER);")
    }

    fun addToCart(cart: String, product_id: String, name: String, price: Int, amount: Int, frequency: Int) {
        val db = writableDatabase
        db.execSQL("INSERT INTO $cart VALUES('${product_id}' ,'$name', $price, $amount, $frequency) ")
    }

    fun removeAtCart(cart: String, product_id: String) {
        val db = writableDatabase
        db.execSQL("DELETE FROM $cart WHERE PRODUCT_ID = '$product_id'")
    }

    fun updateAmount(cart: String, product_id: String, amount: Int) {
        val db = writableDatabase
        db.execSQL("UPDATE $cart SET AMOUNT = $amount WHERE PRODUCT_ID = '$product_id'")
    }

    fun updateFrequency(cart: String, product_id: String, frequency: Int) {
        val db = writableDatabase
        db.execSQL("UPDATE $cart SET FREQUENCY = $frequency WHERE PRODUCT_ID = '$product_id'")
    }

    fun getCartProduct(cart: String, product_id: String? = null): Cursor {
        val db = readableDatabase
        var cursor: Cursor
        if (product_id == null) {
            cursor = db.rawQuery("SELECT * FROM $cart", null)
        } else {
            cursor = db.rawQuery("SELECT * FROM $cart WHERE PRODUCT_ID = '$product_id'", null)
        }

        return cursor
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    companion object {
        const val CART_ESSENTIAL = "CART_ESSENTIAL"
        const val CART_BOX_THEME = "CART_BOX_THEME"
    }

}