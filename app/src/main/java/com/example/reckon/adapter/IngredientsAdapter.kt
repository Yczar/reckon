package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.utils.OnIngredientItemSelected
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class IngredientsAdapter(
        val onIngredientItemSelected: OnIngredientItemSelected,
        val ingredients : Map<String, *>):
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

        val key  = ingredients.keys.toTypedArray()
        holder.bind(key[position])

        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.item_ingredient_checkbox)

        checkBox.onCheckedChange { buttonView, isChecked ->
            if (isChecked) onIngredientItemSelected.onItemSelected(key[position]) else onIngredientItemSelected.onItemDeSelected(key[position])
        }
    }

    class IngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(
                key : String
        ){
            itemView.findViewById<TextView>(R.id.item_ingredient_text).text = key
        }
    }
}