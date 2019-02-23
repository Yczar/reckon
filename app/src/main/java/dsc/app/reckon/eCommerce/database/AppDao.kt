package dsc.app.reckon.eCommerce.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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
    fun getAllCartData(): LiveData<MutableList<CartData>>

    //Get Full Details
    @Query("SELECT * FROM eCommerce_db WHERE uid IN (:cartItemId)")
    fun getFullDetail(cartItemId: String) : CartData

    //delete Cart Data
    @Delete
    fun deleteCartData(cartItem : CartData)

    @Query("DELETE FROM eCommerce_db")
    fun deleteAllCartData()

}