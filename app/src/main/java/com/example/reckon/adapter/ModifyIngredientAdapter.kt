package com.example.reckon.adapter

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.google.android.material.textfield.TextInputEditText
import android.text.Editable
import com.example.reckon.utils.AfterIngValueModified
import java.text.DecimalFormat


class ModifyIngredientAdapter(
        val toModify : Map<String, Double>,
        val priceValues : Map<String, Int>,
        val afterIngValueModified: AfterIngValueModified) : RecyclerView.Adapter<ModifyIngredientHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifyIngredientHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient_modifier, parent, false)
        return ModifyIngredientHolder(view)
    }

    override fun getItemCount(): Int {
        return toModify.size
    }

    override fun onBindViewHolder(holder: ModifyIngredientHolder, position: Int) {
        val key = toModify.keys
        val value = toModify.values

        holder.bind(key = key.elementAt(position),
                value = value.elementAt(position),
                price = priceValues,
                afterIngValueModified = afterIngValueModified)
    }
}

class ModifyIngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
            key: String,
            value: Double,
            price: Map<String, Int>,
            afterIngValueModified: AfterIngValueModified
    ){
        itemView.findViewById<TextView>(R.id.tv_ingredient_name).text = key
        val sizePerKg = itemView.findViewById<TextInputEditText>(R.id.et_dcp)
        val pricePerKg = itemView.findViewById<TextInputEditText>(R.id.et_price)

        val formatter = DecimalFormat("#0.00")
        sizePerKg.setText(formatter.format(value).toString())
        pricePerKg.setText(price[key].toString())

        sizePerKg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isEmpty()){
                    s.append("0")
                }
                afterIngValueModified.onValueModified(key, s.toString().toDouble())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}