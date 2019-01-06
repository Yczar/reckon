package com.example.reckon.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.data_model.AgeRange
import com.example.reckon.data_model.LiveStockList
import com.example.reckon.eCommerce.database.CartData
import com.example.reckon.ui.activity.AppEntryPoint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefManager(var context: Context) {

    private lateinit var pref : SharedPreferences
    private lateinit var ingredientsSP: SharedPreferences
    private lateinit var liveStockSp: SharedPreferences

    init {
        getSp()
    }

    companion object {
        //Const Values for SP manipulation -*Fave
        const val LIVESTOCK_KEY = "livestock_key"
        const val MYLIVESTOCK_KEY = "my_livestock_key"
        const val INGREDIENT_PREFS_NAME = "Ingredients"
        const val LIVESTOCK_PREFS_NAME = "Livestock"

        const val FISH_LIVE_STOCK = "Fish"
        const val POULTRY_LIVE_STOCK = "Poultry"
        const val INGREDIENTS = "ingredientsvalue"
        const val CART_DATA = "cart_data"
        const val LIVESTOCK_AGE_RANGE = "livestock_age_range"
        const val SUB_INGREDIENTS = "subingredientsvalue"
        const val SELECTED_INGREDIENTS = "selectedIngredients"
        const val INGREDIENTS_PRICE = "IngredientsPrice"
        const val INGREDIENTS_DCP = "IngredientsDCP"
        const val FEEDSIZE = "feedsize"

        const val AGE_RANGE_KEY = "age_range"
        const val LIVE_STOCK_NAME_KEY = "livestock_name"
    }

    private fun getSp() {
        pref = context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE)
    }

    //Getting the unique SharePreferences for Ingredients -*Fave
    private fun getIngredientSp(): SharedPreferences {
        ingredientsSP = context.getSharedPreferences(INGREDIENT_PREFS_NAME, Context.MODE_PRIVATE)
        return ingredientsSP
    }

    //Getting the unique SharePreferences for Livestock -*Fave
    private fun getLiveStockPrefs(): SharedPreferences{
        liveStockSp = context.getSharedPreferences(LIVESTOCK_PREFS_NAME, Context.MODE_PRIVATE)
        return liveStockSp
    }

    fun writeSp(){
        val editor : SharedPreferences.Editor = pref.edit()
        editor.putString(context.getString(R.string.pref_key), "NEXT")
        editor.apply()
    }

    fun checkPref() : Boolean{
        return !pref.getString(context.getString(R.string.pref_key),"null").equals("null")
    }

    fun clearPreference(){
        pref.edit().clear().apply()
        context.startActivity(Intent(context, AppEntryPoint::class.java))
        (context as BaseActivity).finish()
    }

    //Writing the map values to its specified shared preferences -*Fave
    fun writeLivestockAgeRangeToPrefs(livestockAgeRange: AgeRange){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(livestockAgeRange) // myObject - instance of MyObject
        editor.putString(LIVESTOCK_AGE_RANGE, json)
        editor.commit()
    }
    fun writeSubIngredientsValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(SUB_INGREDIENTS, json)
        editor.commit()
    }
    fun writeSelectedValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(SELECTED_INGREDIENTS, json)
        editor.commit()
    }
    fun writePriceValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(INGREDIENTS_PRICE, json)
        editor.commit()
    }
    fun writeDCPValuesToPrefs(mapValuesAndKeys: Map<String ,Any>){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()

        val gson = Gson()
        val json = gson.toJson(mapValuesAndKeys) // myObject - instance of MyObject
        editor.putString(INGREDIENTS_DCP, json)
        editor.commit()
    }
    fun writeCardDataToPrefs(inComingCarts: CartData) {
        val editor : SharedPreferences.Editor = getIngredientSp().edit()
        val gson = Gson()
        val json = gson.toJson(inComingCarts)
        editor.putString(CART_DATA, json)
        editor.commit()
    }

    fun writeFeedSizeValueToPrefs(feedsize : Double){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()
        editor.putFloat(FEEDSIZE, feedsize.toFloat())
        editor.commit()
    }

    fun getFeedSizeValue() : Double{
        return getIngredientSp().getFloat(FEEDSIZE, 0F).toDouble()
    }

    //Getting all the values for the Ingredient SharedPreferences -*Fave
    fun getLiveStockAgeRangeFromSP(): AgeRange{
        val gson = Gson()
        val json = getIngredientSp().getString(LIVESTOCK_AGE_RANGE, "")
        return gson.fromJson(json, AgeRange::class.java)
    }
    fun getSubIngredientsValuesFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(SUB_INGREDIENTS, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }
    fun getSelectedIngredientsValuesFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(SELECTED_INGREDIENTS, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }
    fun getIngredientsPriceFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(INGREDIENTS_PRICE, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }
    fun getIngredientsDCPFromSP(): Map<String, Any>{
        val gson = Gson()
        val json = getIngredientSp().getString(INGREDIENTS_DCP, "")
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(json, type)
    }

    //Writing the selected livestock to its specified SP -*Fave
    fun writeMySelectedLiveStockToSP(selectedLiveStock: LiveStockList){
        val editor : SharedPreferences.Editor = getIngredientSp().edit()
        val gson = Gson()
        val json = gson.toJson(selectedLiveStock) // myObject - instance of MyObject
        editor.putString(MYLIVESTOCK_KEY, json)
        editor.commit()
    }
    //Getting the selected livestock -*Fave
    fun getSelectedLiveStockFromSP(): LiveStockList{
        val gson = Gson()
        val json = getIngredientSp().getString(MYLIVESTOCK_KEY, "")
        return gson.fromJson(json, LiveStockList::class.java)
    }
    //Writing the selected livestock to its specified SP -*Fave
    fun writeSelectedLiveStockToSP(selectedLiveStock: String){
        val editor = getLiveStockPrefs().edit()
        editor.putString(LIVESTOCK_KEY, selectedLiveStock)
        editor.apply()

    }

    //Writing the selected age range
    fun writeSelectedAgeRange(selectedLiveStock: String){
        val editor = getLiveStockPrefs().edit()
        editor.putString(AGE_RANGE_KEY, selectedLiveStock)
        editor.apply()
    }

    fun getSelectedAgeRange() : String?{
        return getLiveStockPrefs().getString(AGE_RANGE_KEY, "")
    }

    //Writing the selected age range
    fun writeSelectedLiveStockName(selectedLiveStock: String){
        val editor = getLiveStockPrefs().edit()
        editor.putString(LIVE_STOCK_NAME_KEY, selectedLiveStock)
        editor.apply()
    }

    fun getSelectedLiveStockName() : String?{
        return getLiveStockPrefs().getString(LIVE_STOCK_NAME_KEY, "")
    }

    //Getting the selected livestock -*Fave
    fun getSelectedLiveStockToSP(): String?{
        return getLiveStockPrefs().getString(LIVESTOCK_KEY, "")
    }

    fun getCartDataFromSF(): CartData {
        val gson = Gson()
        val json = getIngredientSp().getString(CART_DATA, "")
        return gson.fromJson(json, CartData::class.java)
    }


}

fun RecyclerView.init(context: Context){
    this.setHasFixedSize(true)
    this.layoutManager = LinearLayoutManager(context)
}