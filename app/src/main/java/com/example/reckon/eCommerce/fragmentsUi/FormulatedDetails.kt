package com.example.reckon.eCommerce.fragmentsUi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reckon.R

class FormulatedDetails : Fragment() {

    //TODO: Data passed from the [InCartFragment] Will be gotten here, "otan", decide how to handle the recycler view in this place

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ec_fragment_formulated_details, container, false)
    }


}
