package com.example.reckon.eCommerce.fragmentsUi


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.reckon.R
import com.example.reckon.eCommerce.adapter.IngredientDetailsAdater
import com.example.reckon.eCommerce.database.CartData
import com.example.reckon.utils.PrefManager
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ec_include_ingredient_pack_content.*
import java.text.DecimalFormat

class FormulatedDetails : Fragment() {
    lateinit var recyclerView : RecyclerView
    lateinit var adapter : IngredientDetailsAdater
    var ingredients : Map<String, Double> = hashMapOf()
    lateinit var cartData : CartData
    lateinit var manager : PrefManager

    //TODO: Data passed from the [InCartFragment] Will be gotten here, "otan", decide how to handle the recycler view in this place

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.ec_fragment_formulated_details, container, false)
        manager = PrefManager(view.context)
        cartData = manager.getCartDataFromSF()
        Log.e("kkkkkkk", cartData.livestockType)
        recyclerView = view.findViewById(R.id.cart_details_rv)
        adapter = IngredientDetailsAdater(ingredients)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

//        Picasso.get().load(cartData.livestockImage).into(img_livestock)
        val formatter = DecimalFormat("#0.00")
        val liveStockType = view.findViewById<TextView>(R.id.tv_livestock_type)
        val liveStockAgeRange = view.findViewById<TextView>(R.id.tv_livestock_type_age_range)
        val totalDCP = view.findViewById<TextView>(R.id.tv_total_dcp)
        val totalSize = view.findViewById<TextView>(R.id.tv_kg)
        val totalPrice = view.findViewById<TextView>(R.id.tv_price)
        val totalPacks = view.findViewById<TextView>(R.id.tv_packs)
        liveStockType.text = cartData.livestockType.toString()
        liveStockAgeRange.text = cartData.livestockAgeRange.toString()
        totalDCP.text = formatter.format(cartData.totalDCP).toString()
        totalSize.text = formatter.format(cartData.totalSize).toString()
        totalPrice.text = formatter.format(cartData.totalPrice).toString()
        totalPacks.text = cartData.noOfPacks.toString()

        getData()

        val feedingDesc = view.findViewById<MaterialCardView>(R.id.feeding_desc)
        feedingDesc.setOnClickListener{
            //TODO get feeding description from cartdata and display it in a dialog or if you have a better design
            //TODO concept. Ben's work
            val feedingDesc = cartData.feedingDescription

        }
        return view
    }

    fun getData(){
        val gson = Gson()
        val type = object : TypeToken<Map<String, Double>>() {}.type
        ingredients = gson.fromJson(cartData.ingredient_list, type)
        adapter.update(ingredients)
    }


}
