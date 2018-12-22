package com.example.reckon.utils

import androidx.annotation.StringRes
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*

interface ToolbarTitleListener {

    //Update this SAM to take two parameters to update said toolbar -*Fave
    fun updateTitle(@StringRes titleRes: Int?, titleString: String?)

}

interface OnLiveStockItemSelectedListener{
    fun onLiveStockSelected(livestock : DocumentSnapshot)
}

interface OnAgeExpandListener{
    //Change the value type from Objects to Any -*Fave
    fun onLiveStockAgeSelected(ingredients : Map<String, Any>)
}

interface OnIngredientItemSelected{
    fun onItemSelected(ingredient: String)

    fun onItemDeSelected(ingredient: String)
}