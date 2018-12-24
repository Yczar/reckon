package com.example.reckon.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.adapter.IngredientsAdapter
import com.example.reckon.utils.ToolbarTitleListener
import com.example.reckon.utils.init


/**
 * A simple [Fragment] subclass.
 */
class ModifyIngredient : Fragment() {
    //
    //
    //
    //
    //This fragment has been replaced with the ModifyIngredientDialogFragment class
    //
    //
    //
    //


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modify_ingredient, container, false)

        //Added null value for titleString -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.modify_ingredients, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.modify_fragment_recycler)
        recyclerView.init(context!!)

        return view
    }

}// Required empty public constructor
