package com.example.reckon.eCommerce.view_model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import com.example.reckon.eCommerce.database.AppDatabse
import com.example.reckon.eCommerce.database.CartData

class AddToCartViewModel(application: Application) : AndroidViewModel(application) {
    val database : AppDatabse = AppDatabse.getAppDatabse(this.getApplication<Application>())!!

    fun insertCartDataToDB(cartData: CartData){
        InsertTask().execute(cartData)
    }

    fun deleteCartData(cartData: CartData){
        DeleteTask().execute(cartData)
    }

    fun deleteAllCartData(){
        DeleteAllTask().execute()
    }

    private inner class InsertTask : AsyncTask<CartData, Void, Void>() {
        override fun doInBackground(vararg cartData: CartData): Void? {
            database.feedDao().insertANewCart(cartData[0])
            return null
        }
    }

    private inner class DeleteTask : AsyncTask<CartData, Void, Void>() {
        override fun doInBackground(vararg cartData: CartData): Void? {
            database.feedDao().deleteCartData(cartData[0])
            return null
        }
    }

    private inner class DeleteAllTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            database.feedDao().deleteAllCartData()
            return null
        }


    }
}