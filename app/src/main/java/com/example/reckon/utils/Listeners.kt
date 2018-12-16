package com.example.reckon.utils

import androidx.annotation.StringRes
import com.google.firebase.firestore.DocumentSnapshot

interface ToolbarTitleListener {
    fun updateTitle(@StringRes title: Int)
}

interface OnLiveStockItemSelectedListener{
    fun onLiveStockSelected(livestock : DocumentSnapshot)
}

interface OnAgeExpandListener{
    fun onLiveStockSelected(livestockAge : DocumentSnapshot)
}