package com.example.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.data_model.LiveStockList
import com.example.reckon.utils.OnLiveStockItemSelectedListener
import com.example.reckon.utils.PrefManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_livestocks.view.*

open class LiveStockAdapter(
        query: Query,
        private val listener: OnLiveStockItemSelectedListener):
        FirestoreAdapter<LivestockHolder>(query) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivestockHolder {
        //Inflate the lick stock here
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_livestocks, parent, false)
        return LivestockHolder(view)
    }

    override fun onBindViewHolder(holder: LivestockHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)
    }
}

class LivestockHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
            snapshot: DocumentSnapshot,
            listener: OnLiveStockItemSelectedListener
    ){
        val livestock = snapshot.toObject(LiveStockList::class.java) ?: return
        //Load Image
        Picasso.get()
                .load(livestock.image)
                .into(itemView.img_livestock)

        //Load Name
        itemView.tv_livestock.text = livestock.name

        //Click Listener
        itemView.setOnClickListener{
            listener.onLiveStockSelected(snapshot)
        }
    }
}