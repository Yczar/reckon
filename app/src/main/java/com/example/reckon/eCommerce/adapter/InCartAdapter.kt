package com.example.reckon.eCommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.eCommerce.database.CartData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ec_item_in_cart.view.*

class InCartAdapter(
        private val inComingCarts: List<CartData>):

        RecyclerView.Adapter<InCartViewHolder>() {
    override fun getItemCount(): Int {
        return inComingCarts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ec_item_in_cart, parent, false)

        return InCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: InCartViewHolder, position: Int) {
        var thisCart = inComingCarts[position]

        val liveStockImage = thisCart.livestockImage
        val liveStockType = thisCart.livestockType
        val liveStockAgeRange = thisCart.livestockAgeRange
        val livestockInitialAmount = thisCart.initialAmount
        val ingredientsPacks = thisCart.noOfPacks
        val totalAmount = thisCart.totalPrice
        val feedDuration = thisCart.feedDuration
        val totalDCP = thisCart.totalDCP
        val feedingDescription = thisCart.feedingDescription

        holder.bind(
                livestock_image = liveStockImage ,
                livestock_type = liveStockType,
                livestock_age_range = liveStockAgeRange,
                livestock_amount = livestockInitialAmount,
                number_of_packs = ingredientsPacks,
                total_price = totalAmount,
                feed_duration = feedDuration,
                total_dcp = totalDCP,
                feed_description = feedingDescription
        )
    }
}

class InCartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(
            livestock_image: String?,
            livestock_type: String?,
            livestock_age_range: String?,
            livestock_amount: String?,
            number_of_packs: Int = 1,
            total_price: String?,
            feed_duration: String?,
            total_dcp: String?,
            feed_description: String?
    ){
        //Load Image
        Picasso.get()
                .load(livestock_image)
                .into(itemView.ec_img_livestock)

        itemView.tv_livestock_type.text = livestock_type
        itemView.tv_livestock_type_age_range.text = livestock_age_range
        itemView.tv_initial_price.text = livestock_amount
        itemView.ec_et_packs.setText(number_of_packs)
        itemView.tv_total_price.text = livestock_amount
    }
}