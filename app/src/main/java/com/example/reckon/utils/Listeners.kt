package com.example.reckon.utils

import androidx.annotation.StringRes
import com.example.reckon.data_model.AgeRange
import com.example.reckon.data_model.LiveStockList
import com.example.reckon.eCommerce.database.CartData
import com.google.firebase.firestore.DocumentSnapshot

interface ToolbarTitleListener {

    //Update this SAM to take two parameters to update said toolbar -*Fave
    fun updateTitle(@StringRes titleRes: Int?, titleString: String?)

}

interface OnLiveStockItemSelectedListener{
    fun onLiveStockSelected(snapshot : DocumentSnapshot,
                            livestock : LiveStockList)
}

interface OnAgeExpandListener{
    //Change the value type from Objects to Any -*Fave
    fun onLiveStockAgeSelected(livestockAgeRange : AgeRange)
}

interface OnIngredientItemSelected{
    fun onItemSelected(ingredient: String, value: Double)

    fun onItemDeSelected(ingredient: String, value: Double)
}

interface AfterIngValueModified{
    fun onValueModified(ingredient: String, value: Double)
}

interface OnCartItemSelected{
    fun onItemSelected(cartData: CartData)
    fun onDeleteCartDataClicked(inComingCarts: CartData) {
    }
}

interface OnModifyDialogDoneButtonClicked{
    fun onDoneButtonClicked()
}