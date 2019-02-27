package dsc.app.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dsc.app.reckon.R
import dsc.app.reckon.utils.OnIngredientItemSelected
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class IngredientsAdapter(
        val onIngredientItemSelected: OnIngredientItemSelected,
        val ingredients : Map<String, Any>):
        RecyclerView.Adapter<IngredientsAdapter.IngredientHolder>() {

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredients_selector, parent, false)
        return IngredientHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {

        val key  = ingredients.keys.toTypedArray()
        val value = ingredients.values.toTypedArray()
        holder.bind(key[position])

        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.item_ingredient_checkbox)
        checkBox.isChecked = true

        checkBox.onCheckedChange { buttonView, isChecked ->
            if (isChecked) onIngredientItemSelected.onItemSelected(key[position], value[position] as Double)
            else onIngredientItemSelected.onItemDeSelected(key[position], value[position] as Double)
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