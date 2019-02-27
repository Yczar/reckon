package dsc.app.reckon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import dsc.app.reckon.R
import dsc.app.reckon.data_model.LiveStockList
import dsc.app.reckon.utils.OnLiveStockItemSelectedListener
import dsc.app.reckon.utils.PrefManager
import kotlinx.android.synthetic.main.item_livestocks.view.*

open class LiveStockAdapter(
        query: Query,
        private val listener: OnLiveStockItemSelectedListener):
        dsc.app.reckon.adapter.FirestoreAdapter<LivestockHolder>(query) {

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
            listener.onLiveStockSelected(snapshot, livestock)
            /*listener.onLiveStockSelected(snapshot)*/
            PrefManager(itemView.context).writeSelectedLiveStockName(livestock.name!!)
        }
    }
}