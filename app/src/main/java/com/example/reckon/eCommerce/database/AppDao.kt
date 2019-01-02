package com.example.reckon.eCommerce.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDao {
    /**For Carts*/

    //Insert a new Cart Data
    @Insert
    fun insertANewCart(vararg intoCart: CartData)

    //Get All Cart Data
    @Query("SELECT * FROM eCommerce_db")
    fun getAllCartData(): List<CartData>

    //Get Full Details
    @Query("SELECT * FROM eCommerce_db WHERE uid IN (:cartItemId)")
    fun getFullDetail(cartItemId: String) : CartData

}