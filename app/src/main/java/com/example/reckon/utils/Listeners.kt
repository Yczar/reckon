package com.example.reckon.utils

import androidx.annotation.StringRes
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

interface ToolbarTitleListener {
    fun updateTitle(@StringRes title: Int)
}

interface OnLiveStockItemSelectedListener{
    fun onLiveStockSelected(livestock : DocumentSnapshot)
}

interface OnAgeExpandListener{
    fun onLiveStockAgeSelected(ingredients : Map<String, Objects>)
}