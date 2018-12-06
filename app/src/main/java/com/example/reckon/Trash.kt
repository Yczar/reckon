package com.example.reckon

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Trash {

    fun trash() {
        val rootRef = FirebaseFirestore.getInstance()
        val codesRef = rootRef.collection("CodeClinic").document("Codes")
        codesRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val list = ArrayList<String>()
                val map = it.result!!.data
                for ((key) in map!!) {
                    list.add(key)
                    Log.d(TAG, "The Key is: $key")
                }
                //Do what you want to do with your list
            }
        }
    }
}