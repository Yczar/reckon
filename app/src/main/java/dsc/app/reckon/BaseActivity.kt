package dsc.app.reckon

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.reckon.adapter.LiveStockAdapter
import com.example.reckon.utils.OnLiveStockItemSelectedListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import org.jetbrains.anko.find

open class BaseActivity : AppCompatActivity(){

    val aquaticCollection : String = "Aquatic"
    val poultryCollection : String = "Poultry"
    val ageCollection : String = "Age"

    /*----------------------------------*/
    /**Adapter Helper*/
    /*----------------------------------*/
    lateinit var adapter: LiveStockAdapter
    /**
     * Get the list of lives tocks based on the
     * [Collection] type parsed in
     * */
    fun getLivestockList(
            ll: LinearLayout,
            rv: RecyclerView,
            type : Query,
            listener: OnLiveStockItemSelectedListener) {
        adapter = object : LiveStockAdapter(type,listener){
            override fun onDataChanged() {
                if (itemCount != 0){
                    rv.visibility = View.VISIBLE
                    ll.visibility = View.GONE
                }else{
                    rv.visibility = View.GONE
                    ll.visibility = View.VISIBLE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                Snackbar.make(find(android.R.id.content), "$e", Snackbar.LENGTH_LONG).show()
            }
        }
        rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv.adapter = adapter
    }
}
