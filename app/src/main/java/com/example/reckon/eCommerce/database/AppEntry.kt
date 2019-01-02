package com.example.reckon.eCommerce.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "eCommerce_db")
data class CartData (
        @PrimaryKey var uid: Int,
        @ColumnInfo(name = "livestock_image") var livestockImage: String?,
        @ColumnInfo(name = "livestock_type") var livestockType: String?,
        @ColumnInfo(name = "livestock_age_range") var livestockAgeRange: String?,
        @ColumnInfo(name = "initial_price") var initialAmount: String?,
        @ColumnInfo(name = "number_of_packs") var noOfPacks: Int,
        @ColumnInfo(name = "total_price") var totalPrice: String?,
        @ColumnInfo(name = "feed_duration") var feedDuration: String?,
        @ColumnInfo(name = "total_dcp") var totalDCP: String?,
        @ColumnInfo(name = "feeding_description") var feedingDescription: String?,
        @ColumnInfo(name = "ingredient_list") var ingredient_list: List<IngredientListData>?
)

data class IngredientListData(
     val ingredient_name: String,
     val size_in_kg: String,
     val total_price: String
)

