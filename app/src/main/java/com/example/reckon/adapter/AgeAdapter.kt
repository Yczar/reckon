package com.example.reckon.adapter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.R.id.parent
import com.example.reckon.data_model.AgeRange
import com.example.reckon.utils.OnAgeExpandListener
import com.example.reckon.utils.PrefManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_age_range.view.*

open class AgeAdapter (
        query: Query,
        private val listener : OnAgeExpandListener
) :
        FirestoreAdapter<AgeHolder>(query){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_age_range, parent, false)
        return AgeHolder(view)
    }

    override fun onBindViewHolder(holder: AgeHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)

        Log.d(ContentValues.TAG, "III: ${getSnapshot(position).get("age_range")}")
    }

    /*override fun getItemCount(): Int {
        return if (ingredients.isNotEmpty()){
            ingredients.size
        }else{
            0
        }
    }*/

}

class AgeHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    fun bind(
            snapshot: DocumentSnapshot,
            listener: OnAgeExpandListener
    ){
        val livestockAge = snapshot.toObject(AgeRange::class.java) ?: return

        //Load Ages
        itemView.tv_livestock_age.text = livestockAge.age_range

        //on item clicked
        itemView.setOnClickListener{
            listener.onLiveStockAgeSelected(livestockAge)

            Log.d(ContentValues.TAG, "III: ${livestockAge.age_range}")
            PrefManager(itemView.context).writeSelectedAgeRange(livestockAge.age_range!!)
        }
    }
}
