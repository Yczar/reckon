package dsc.app.reckon.eCommerce.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "eCommerce_db")
data class CartData (
        @PrimaryKey(autoGenerate = true) var uid: Long = System.currentTimeMillis(),
        @ColumnInfo(name = "livestock_image") var livestockImage: String? = "",
        @ColumnInfo(name = "livestock_type") var livestockType: String? = "",
        @ColumnInfo(name = "livestock_age_range") var livestockAgeRange: String? = "",
        @ColumnInfo(name = "initial_price") var initialAmount: Double? = 0.0,
        @ColumnInfo(name = "number_of_packs") var noOfPacks: Int = 0,
        @ColumnInfo(name = "total_price") var totalPrice: Double? = 0.0,
        @ColumnInfo(name = "feed_duration") var feedDuration: String? = "",
        @ColumnInfo(name = "total_dcp") var totalDCP: Double? = 0.0,
        @ColumnInfo(name = "total_size") var totalSize: Double? = 0.0,
        @ColumnInfo(name = "feeding_description") var feedingDescription: String? = "",
        @ColumnInfo(name = "ingredient_list") var ingredient_list: String? = ""
)

data class IngredientListData(
     val ingredient_name: String,
     val size_in_kg: String,
     val total_price: String
)

