package com.example.reckon.ui.fragment


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.reckon.BaseActivity

import com.example.reckon.R
import com.example.reckon.utils.OnLiveStockItemSelectedListener
import com.example.reckon.utils.ToolbarTitleListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_livestocks.*

class Poultry : Fragment(), OnLiveStockItemSelectedListener {

    private lateinit var baseActivity: BaseActivity
    private lateinit var db: FirebaseFirestore
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the livestock_sub_category for this fragment
        return inflater.inflate(R.layout.fragment_livestocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ToolbarTitleListener).updateTitle(R.string.ls_poultry)

        baseActivity = BaseActivity()
        db = FirebaseFirestore.getInstance()

        query = db.collection(baseActivity.poultryCollection)
        baseActivity.getLivestockList(
                empty_view,
                recycler_view,
                query, this@Poultry)

        setUp()
    }

    //Setup fire store
    fun setUp(){
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        db.firestoreSettings = settings
    }

    //Start listening for dat in the db
    override fun onStart() {
        super.onStart()
        baseActivity.adapter.startListening()
    }

    override fun onLiveStockSelected(livestock: DocumentSnapshot) {
        val id = livestock.id
        val docId =
                PoultryDirections.ActionPoultryMenuToAge().setId("${baseActivity.poultryCollection}/$id")
        Navigation.findNavController(this.view!!).navigate(docId)
    }
}// Required empty public constructor
