package com.example.reckon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R

/**
 * Created by Big-Nosed Developer on the Edge of Infinity.
 */
class ModifyIngredientsDialogEditTextAdapter(val context: Context,
                                             val lisOfIngredients: List<String>): RecyclerView.Adapter<ModifyIngredientsDialogEditTextAdapter.ModifyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifyViewHolder = ModifyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.modify_dialog_layout, parent, false)
    )

    override fun getItemCount(): Int = lisOfIngredients.size

    override fun onBindViewHolder(holder: ModifyViewHolder, position: Int) {
        val labelTextView = holder.itemView.findViewById<TextView>(R.id.modify_item_layout_ingredient_label)
        labelTextView.text = lisOfIngredients[position]

        //TODO: The best amout for the ingredient should be loaded into the editText below
        val editText = holder.itemView.findViewById<EditText>(R.id.modify_item_layout_ingredient_edit_text)

    }

    class ModifyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}