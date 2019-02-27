package dsc.app.reckon.ui.fragment


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import dsc.app.reckon.R
import dsc.app.reckon.data_model.LiveStockList
import dsc.app.reckon.utils.OnLiveStockItemSelectedListener
import dsc.app.reckon.utils.PrefManager
import dsc.app.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.fragment_livestocks.*

class Poultry : Fragment(), OnLiveStockItemSelectedListener {

    private lateinit var baseActivity: dsc.app.reckon.BaseActivity
    private lateinit var db: FirebaseFirestore
    private lateinit var query: Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the livestock_sub_category for this fragment
        return inflater.inflate(R.layout.fragment_livestocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Made the string parameter null -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.ls_poultry, null)

        baseActivity = dsc.app.reckon.BaseActivity()
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

    override fun onLiveStockSelected(snapshot : DocumentSnapshot, livestock: LiveStockList) {

        PrefManager(context!!).writeMySelectedLiveStockToSP(livestock)

        //Wrote "Fish" to SharedPreferences - *Fave
        PrefManager(context!!).writeSelectedLiveStockToSP(PrefManager.POULTRY_LIVE_STOCK)

        val id = snapshot.id
        val docId =
                PoultryDirections.ActionPoultryMenuToAge().setId("${baseActivity.poultryCollection}/$id")
        Navigation.findNavController(this.view!!).navigate(docId)
    }
}// Required empty public constructor
