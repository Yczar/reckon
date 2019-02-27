package dsc.app.reckon.eCommerce.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dsc.app.reckon.eCommerce.database.AppDatabse
import dsc.app.reckon.eCommerce.database.CartData

class InCartViewModel(application: Application) : AndroidViewModel(application) {
    val database : AppDatabse = AppDatabse.getAppDatabse(this.getApplication<Application>())!!

    fun getAllCartData() : LiveData<MutableList<CartData>>{
        return database.feedDao().getAllCartData()
    }
}