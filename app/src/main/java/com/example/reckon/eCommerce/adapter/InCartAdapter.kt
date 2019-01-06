package com.example.reckon.eCommerce.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.eCommerce.database.CartData
import com.example.reckon.eCommerce.view_model.AddToCartViewModel
import com.example.reckon.utils.OnAgeExpandListener
import com.example.reckon.utils.OnCartItemSelected
import com.example.reckon.utils.PrefManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ec_item_in_cart.view.*
import java.text.DecimalFormat
import java.util.*

class InCartAdapter(
        private var inComingCarts: List<CartData>,
        var listener: OnCartItemSelected):

        RecyclerView.Adapter<InCartViewHolder>() {
    override fun getItemCount(): Int {
        return inComingCarts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ec_item_in_cart, parent, false)

        return InCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: InCartViewHolder, position: Int) {
        holder.bind(inComingCarts = inComingCarts[position], listener = listener)
    }

    fun update(cartListData : List<CartData>){
        inComingCarts = cartListData
        notifyDataSetChanged()
    }
}

class InCartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(
            inComingCarts: CartData,
            listener: OnCartItemSelected
    ){
        //Load Image
        Picasso.get()
                .load(inComingCarts.livestockImage)
                .into(itemView.ec_img_livestock)
        val formatter = DecimalFormat("#0.00")

        itemView.tv_livestock_type.text = inComingCarts.livestockType
        itemView.tv_livestock_type_age_range.text = inComingCarts.livestockAgeRange
        itemView.tv_initial_price.text = formatter.format(inComingCarts.initialAmount).toString()
        itemView.ec_et_packs.text = inComingCarts.noOfPacks.toString()
        itemView.tv_total_price.text = formatter.format(inComingCarts.totalPrice).toString()

        itemView.setOnClickListener{
            listener.onItemSelected(inComingCarts)
        }
        val delete = itemView.findViewById<Button>(R.id.delete_cart_data)
        delete.setOnClickListener{
            listener.onDeleteCartDataClicked(inComingCarts)
        }
    }
}