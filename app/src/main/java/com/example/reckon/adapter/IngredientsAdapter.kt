package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import kotlinx.android.synthetic.main.item_ingredients.view.*

class IngredientsAdapter(
        val ingredients : Map<String, Number>):
        RecyclerView.Adapter<IngredientsAdapter.IngredientHolder>() {

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredients, parent, false)
        return IngredientHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val key  = ingredients.keys
        val value = ingredients.values

        holder.bind(key.elementAt(position), value.elementAt(position))
    }

    class IngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(
                key : String, value : Number
        ){
            itemView.tv_ingredient.text = key
        }
    }
}