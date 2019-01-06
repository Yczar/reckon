package com.example.reckon.eCommerce.view_model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.reckon.eCommerce.database.AppDatabse
import com.example.reckon.eCommerce.database.CartData

class InCartViewModel(application: Application) : AndroidViewModel(application) {
    val database : AppDatabse = AppDatabse.getAppDatabse(this.getApplication<Application>())!!

    fun getAllCartData() : LiveData<MutableList<CartData>>{
        return database.feedDao().getAllCartData()
    }
}