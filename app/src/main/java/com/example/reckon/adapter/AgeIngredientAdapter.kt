package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.data_model.AgeRange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_age_ingredient.view.*

open class AgeIngredientAdapter (
        query: Query) :
        FirestoreAdapter<AgeIngredientHolder>(query){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeIngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_age_ingredient, parent, false)
        return AgeIngredientHolder(view)
    }

    override fun onBindViewHolder(holder: AgeIngredientHolder, position: Int) {
        holder.bind(getSnapshot(position))
    }

}

class AgeIngredientHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    fun bind(
            snapshot: DocumentSnapshot){
        val livestockAge = snapshot.toObject(AgeRange::class.java) ?: return

        //Load Ages
        itemView.tv_livestock_age.text = livestockAge.age_ranges.toString()
    }
}
