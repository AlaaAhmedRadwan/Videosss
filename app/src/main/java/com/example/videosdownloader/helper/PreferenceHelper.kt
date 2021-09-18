package com.example.videosdownloader.helper


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.NullPointerException
import java.util.ArrayList
import java.util.HashSet

class PreferenceHelper(private val context: Context) {

    companion object {
        private var app_prefs: SharedPreferences? = null
        private const val app_ref = "userdetails"
        private const val CART_ARRAY = "CART_ARRAY"
        private const val IsEnabled = "IsEnabled"
        private val arrPackage: ArrayList<String> = ArrayList<String>()



        fun addItemtoDownLoaded(item_id: Int) {
            val gson = Gson()
            val json = app_prefs!!.getString(CART_ARRAY, "")
            val type = object : TypeToken<List<String?>?>() {}.type
            var arrayList = gson.fromJson<MutableList<String?>>(json, type)
            if (arrayList == null) arrayList = ArrayList()
            val editor = app_prefs!!.edit()
            arrayList.add(item_id.toString())
            val newdata = gson.toJson(arrayList, type)
            editor.putString(CART_ARRAY, newdata)
            editor.apply()
            editor.commit()
        }

        fun retriveDownloaded(): ArrayList<String>? {
            arrPackage.clear()
            val gson = Gson()
            val json = app_prefs!!.getString(CART_ARRAY, "")
            val type = object : TypeToken<List<String?>?>() {}.type
            val arrayList = gson.fromJson<List<String>>(json, type)
            if (arrayList == null) return null else arrPackage.addAll(arrayList)
            return arrPackage
        }


    }

    init {
        try {
            app_prefs = context.getSharedPreferences(app_ref, Context.MODE_PRIVATE)
        } catch (e: NullPointerException) {
        }
    }
}