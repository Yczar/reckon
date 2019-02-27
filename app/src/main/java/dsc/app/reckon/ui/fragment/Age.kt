package dsc.app.reckon.ui.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import dsc.app.reckon.R
import dsc.app.reckon.data_model.AgeRange
import dsc.app.reckon.utils.OnAgeExpandListener
import dsc.app.reckon.utils.PrefManager
import dsc.app.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.fragment_age.*
import kotlinx.android.synthetic.main.include_subtitle.view.*

class Age : Fragment(), OnAgeExpandListener {

    lateinit var ARG_ID: String

    lateinit var db: FirebaseFirestore
    lateinit var query: Query
    lateinit var adapter: dsc.app.reckon.adapter.AgeAdapter
    lateinit var baseActivity: dsc.app.reckon.BaseActivity

    private var manager: PrefManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_age, container, false)

        manager = PrefManager(view.context)
        view.tv_sub_title.text = manager!!.getSelectedLiveStockName()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Made the string parameter null -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.ls_age_selector, null)

        baseActivity = dsc.app.reckon.BaseActivity()

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
        adapter = object : dsc.app.reckon.adapter.AgeAdapter(type, this) {
            override fun onDataChanged() {
                if(itemCount != 0){
                    emptyView.visibility = View.GONE
                    rv.visibility = View.VISIBLE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                //TODO Snackbar.make(find(android.R.id.content), "$e", Snackbar.LENGTH_LONG).show()

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

    override fun onLiveStockAgeSelected(livestockAgeRange: AgeRange){
        //Writing the whole AgeRange object to SharedPreferences -*Fave
        PrefManager(context!!).writeLivestockAgeRangeToPrefs(livestockAgeRange)
        //Navigating to SelectIngredient finally
        Navigation.findNavController(this.view!!).navigate(R.id.action_age_to_selectIngredient)
    }
}
