package com.example.reckon.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.utils.OnLiveStockItemSelectedListener
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_livestocks.*

class Fish : Fragment(), OnLiveStockItemSelectedListener {

    private lateinit var baseActivity: BaseActivity
    private lateinit var db: FirebaseFirestore
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_livestocks, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Made the string parameter null -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.ls_aquatic, null)

        baseActivity = BaseActivity()
        db = FirebaseFirestore.getInstance()

        query = db.collection(baseActivity.aquaticCollection)
        baseActivity.getLivestockList(
                empty_view,
                recycler_view,
                query,this@Fish)

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

        //Wrote "Fish" to SharedPreferences - *Fave
        PrefManager(context!!).writeSelectedLiveStockToSP(PrefManager.FISH_LIVE_STOCK)

        val id = livestock.id
        val docId = FishDirections.ActionAquaticMenuToAge().setId("${baseActivity.aquaticCollection}/$id")
        Navigation.findNavController(this.view!!).navigate(docId)
    }
}