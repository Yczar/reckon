package com.example.reckon.ui.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.adapter.AgeIngredientAdapter
import com.example.reckon.utils.OnAgeExpandListener
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_age.*
import org.jetbrains.anko.support.v4.find

class Age : Fragment(), OnAgeExpandListener {

    lateinit var ARG_ID: String

    lateinit var db: FirebaseFirestore
    lateinit var query: Query
    lateinit var adapter: AgeIngredientAdapter
    lateinit var baseActivity: BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_age, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Made the string parameter null -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.ls_age_selector, null)

        baseActivity = BaseActivity()

        val safeArgs = AgeArgs.fromBundle(arguments)
        ARG_ID = safeArgs.id

        Log.d(TAG, "Reference: $ARG_ID/${baseActivity.ageCollection}")

        db = FirebaseFirestore.getInstance()
        query = db.collection("$ARG_ID/${baseActivity.ageCollection}")

        getLiveStocksAge(tv, rv_age, query)
    }

    private fun getLiveStocksAge(
            emptyView: TextView,
            rv: RecyclerView,
            type: Query) {
        adapter = object : AgeIngredientAdapter(type, this) {
            override fun onDataChanged() {
                if(itemCount != 0){
                    emptyView.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                Snackbar.make(find(android.R.id.content), "$e", Snackbar.LENGTH_LONG).show()

            }
        }
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }
    //Setup fire store
    fun setUp() {
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        db.firestoreSettings = settings
    }

    //Start listening for dat in the db
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onLiveStockAgeSelected(ingredients: Map<String, Any>) {

        //Writing the map values and keys to SharedPreferences -*Fave
        PrefManager(context!!).writeMapValuesToPrefs(ingredients)
    }
}
