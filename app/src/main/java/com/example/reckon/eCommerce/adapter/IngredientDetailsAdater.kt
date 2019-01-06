package com.example.reckon.eCommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ec_item_formulated_details.view.*
import kotlinx.android.synthetic.main.ec_item_in_cart.view.*
import java.text.DecimalFormat

class IngredientDetailsAdater(var ingredients : Map<String, Double>)
    : RecyclerView.Adapter<IngDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.ec_item_formulated_details, parent, false)
        return IngDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
       return ingredients.size
    }

    override fun onBindViewHolder(holder: IngDetailsViewHolder, position: Int) {
        val key  = ingredients.keys.elementAt(position)
        val value = ingredients.values.elementAt(position)
        holder.bind(key, value)
    }
    fun update(inns: Map<String, Double>){
        ingredients = inns
        notifyDataSetChanged()
    }
}

class IngDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(
            ingredientName : String?,
            ingredientSize : Double?
    ){
        val formatter = DecimalFormat("#0.00")
        itemView.tv_ingredient_name.text = ingredientName
        itemView.tv_ingredient_size.text = formatter.format(ingredientSize).toString()
    }
}