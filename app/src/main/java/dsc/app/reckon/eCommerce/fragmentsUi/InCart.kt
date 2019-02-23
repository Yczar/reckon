package dsc.app.reckon.eCommerce.fragmentsUi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.reckon.R
import com.example.reckon.adapter.ModifyIngredientAdapter
import com.example.reckon.eCommerce.adapter.InCartAdapter
import com.example.reckon.eCommerce.database.CartData
import com.example.reckon.eCommerce.view_model.AddToCartViewModel
import com.example.reckon.eCommerce.view_model.InCartViewModel
import com.example.reckon.utils.OnCartItemSelected
import com.example.reckon.utils.PrefManager

class InCart : Fragment(), OnCartItemSelected {

    lateinit var inCartAdapter : InCartAdapter
    var cartListData : MutableList<CartData> = arrayListOf()

    //TODO: Get the List of Item in the Cart from the DB Using the ViewModel
    //TODO: When the item is clicked pass the Data to the next fragment using save Args for the pack data, decide how to pass the list of ingredient
    //TODO: The Option to Add item for check out has not been implemented yet till v2 if it comes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.ec_fragment_in_cart, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.in_cart_rv)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context!!)

            inCartAdapter = InCartAdapter(cartListData, this@InCart)

            layoutManager = LinearLayoutManager(context)
            adapter = inCartAdapter
        }

        getAllCartData()

        return view
    }

    override fun onItemSelected(cartData: CartData) {
        PrefManager(context!!).writeCardDataToPrefs(cartData)
        Navigation.findNavController(this.view!!).navigate(R.id.action_inCart_to_formulatedDetails)
    }
    override fun onDeleteCartDataClicked(cartData: CartData) {
        val addToCartViewModel =
                ViewModelProviders.of(this).get(AddToCartViewModel::class.java)
        addToCartViewModel.deleteCartData(cartData)

    }

    fun getAllCartData(){
        val inCartViewModel =
                ViewModelProviders.of(this).get(InCartViewModel::class.java)
        val liveData : LiveData<MutableList<CartData>> = inCartViewModel.getAllCartData()

        liveData.observe(this, Observer<MutableList<CartData>> { cartData ->
            cartListData.clear()
            if (cartData != null) {
                cartListData = cartData
            }
            inCartAdapter.update(cartListData)
        })
    }

}
