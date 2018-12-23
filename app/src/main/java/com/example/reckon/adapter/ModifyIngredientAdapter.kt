package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import kotlinx.android.synthetic.main.item_ingredient_modifier.view.*

class ModifyIngredientAdapter(
        val toModify : Map<String, Int>) : RecyclerView.Adapter<ModifyIngredientHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifyIngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient_modifier, parent, false)
        return ModifyIngredientHolder(view)
    }

    override fun getItemCount(): Int {
        return toModify.size
    }

    override fun onBindViewHolder(holder: ModifyIngredientHolder, position: Int) {
        val key = toModify.keys.toTypedArray()
        val value = toModify.values.toTypedArray()

        holder.bind(key = key[position], value = value[position])
    }
}

class ModifyIngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
            key: String,
            value: Int
    ){
        itemView.tv_ingredient_name.text = key
        itemView.et_dcp.setText(value)
    }
}