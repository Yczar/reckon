package com.example.reckon.ui.fragment


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.reckon.R


/**
 * A simple [Fragment] subclass.
 */
class ModifyIngredient : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_ingredient, container, false)
    }

}// Required empty public constructor
