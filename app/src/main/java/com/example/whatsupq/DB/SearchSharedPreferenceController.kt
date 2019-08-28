package com.example.whatsupq.DB

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.json.JSONArray

object SearchSharedPreferenceController {
    val SEARCH_WORD = "unique"

    fun setSearchWord(ctx: Context, key: String, values: ArrayList<String>) {
        val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx)
        val editor: SharedPreferences.Editor = preference.edit()
        var a: JSONArray = JSONArray()
        for (i in 0..values.size) {
            a.put(values.get(i))
        }
        if (values.isEmpty()) {
            editor.putString(key, null)
        } else {
            editor.putString(key, a.toString())
        }
        editor.apply()
    }
}