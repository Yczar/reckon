package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.data_model.AgeRange
import com.example.reckon.utils.OnAgeExpandListener
import com.example.reckon.utils.OnIngredientItemSelected
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_age_ingredient.view.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

open class AgeIngredientAdapter (
        query: Query,
        private val listener : OnAgeExpandListener) :
        FirestoreAdapter<AgeIngredientHolder>(query){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeIngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_age_ingredient, parent, false)
        return AgeIngredientHolder(view)
    }

    override fun onBindViewHolder(holder: AgeIngredientHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)

    }

}

class AgeIngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    fun bind(
            snapshot: DocumentSnapshot,
            listener: OnAgeExpandListener){
        val livestockAge = snapshot.toObject(AgeRange::class.java) ?: return

        //Load Ages
        itemView.tv_livestock_age.text = livestockAge.age_range

        //on item clicked
        itemView.setOnClickListener{
            listener.onLiveStockAgeSelected(livestockAge.ingredients!!)
        }
        //show ingredient for this particular age
        /*val ingredients = livestockAge.ingredients
        if (ingredients != null) {
            for ((key, value) in ingredients) {
                println("$key = $value")
            }
        }*/
    }
}
