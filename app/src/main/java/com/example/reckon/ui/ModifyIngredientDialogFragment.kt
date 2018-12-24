package com.example.reckon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reckon.R
import com.example.reckon.adapter.ModifyIngredientsDialogEditTextAdapter
import com.example.reckon.utils.OnModifyDialogDoneButtonClicked
import com.example.reckon.utils.init
import kotlinx.android.synthetic.main.modify_dialog_layout.*
import java.lang.IllegalStateException

/**
 * Created by Big-Nosed Developer on the Edge of Infinity.
 */
class ModifyIngredientDialogFragment: DialogFragment() {
    lateinit var listener: OnModifyDialogDoneButtonClicked
    lateinit var adapter: ModifyIngredientsDialogEditTextAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.modify_dialog_layout, container, false)

       if(targetFragment is OnModifyDialogDoneButtonClicked){
           listener = targetFragment as OnModifyDialogDoneButtonClicked
       }else{
           throw IllegalStateException("Target Fragment must be implement OnModifyDialogDoneButtonClicked")
       }

        modify_dialog_done_btn.setOnClickListener {
            listener.onDoneButtonClicked()
        }

        modify_dialog_recycler.init(context!!)
        return view
    }



}