package com.example.reckon.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.ui.activity.AppEntryPoint

class PrefManager(var context: Context) {
    lateinit var pref : SharedPreferences

    init {
        getSp()
    }

    private fun getSp() {
        pref = context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE)
    }

    fun writeSp(){
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putString(context.getString(R.string.pref_key), "NEXT")
        editor.apply()
    }

    fun checkPref() : Boolean{
        return !pref.getString(context.getString(R.string.pref_key),"null").equals("null")
    }

    fun clearPreference(){
        pref.edit().clear().apply()
        context.startActivity(Intent(context, AppEntryPoint::class.java))
        (context as BaseActivity).finish()
    }
}