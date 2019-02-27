package dsc.app.reckon.utils

import androidx.annotation.StringRes
import com.google.firebase.firestore.DocumentSnapshot
import dsc.app.reckon.data_model.AgeRange
import dsc.app.reckon.data_model.LiveStockList
import dsc.app.reckon.eCommerce.database.CartData

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