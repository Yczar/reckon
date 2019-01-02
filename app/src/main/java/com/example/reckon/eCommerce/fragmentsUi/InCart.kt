package com.example.reckon.eCommerce.fragmentsUi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reckon.R

class InCart : Fragment() {

    //TODO: Get the List of Item in the Cart from the DB Using the ViewModel
    //TODO: When the item is clicked pass the Data to the next fragment using save Args for the pack data, decide how to pass the list of ingredient
    //TODO: The Option to Add item for check out has not been implemented yet till v2 if it comes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ec_fragment_in_cart, container, false)
    }

}
