package dsc.app.reckon.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.reckon.R
import com.example.reckon.data_model.AgeRange
import com.example.reckon.data_model.IngredientsDCP
import com.example.reckon.data_model.IngredientsPrice
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.view.*

class AppEntryPoint : dsc.app.reckon.BaseActivity(), ToolbarTitleListener{


    private var drawerLayout : DrawerLayout? = null
    lateinit var toolbarTitle : TextView
    lateinit var manager : PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_entry_point)

        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        manager = PrefManager(this)
        toolbarTitle = main_toolbar.toolbar_title

        //[Setup] the nav host Fragment
        val fragmentHoster : NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.host_fragment) as NavHostFragment??: return


        //[Setup] Nav Controller
        val navController = fragmentHoster.navController

        /*//[Setup] Bottom Navigation
        setupBottomNavMenu(navController)*/
        getPriceAbdDCPValues()
    }

    //Make title update
    override fun updateTitle(@StringRes titleRes: Int?, titleString: String?) {

        //Handling the usage of the two nullable values in updateTitle -*Fave
        try {

            if(titleRes != null && titleString == null){
                toolbarTitle.text = getString(titleRes)
            }
            else if(titleString != null && titleRes == null){
                toolbarTitle.text = titleString
            }
            else if (titleRes== null && titleString == null){
                toolbarTitle.text = getString(R.string.app_name)
            }else{
                throw IllegalStateException("Two of the values cannot be not null!")
            }

        }catch (e: IllegalStateException){
            Log.d(AppEntryPoint::class.java.simpleName, "Both of the updateTitle SAM cannot be non_null")
        }

    }

    private fun getPriceAbdDCPValues() {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("PriceAndDCPValues")
                .document("PRICE")
                .addSnapshotListener(EventListener<DocumentSnapshot>{ snapshot, e ->
                    if (e != null){
                        return@EventListener
                    }
                    if (snapshot != null && snapshot.exists()){
                        val ingredientPrice = snapshot.toObject(IngredientsPrice::class.java)
                        manager.writePriceValuesToPrefs(ingredientPrice?.ingredients_price!!)
                    }
                })
        db.collection("PriceAndDCPValues")
                .document("DCP")
                .addSnapshotListener(EventListener<DocumentSnapshot>{ snapshot, e ->
                    if (e != null){
                        return@EventListener
                    }
                    if (snapshot != null && snapshot.exists()){
                        val ingredientsDcp = snapshot.toObject(IngredientsDCP::class.java)
                        manager.writeDCPValuesToPrefs(ingredientsDcp?.ingredients_dcp!!)
                    }
                })
    }


    /**
     * Using the NavigationUi and setting up with nav controller
     * check the fragment [ID] in the [navigation_graph]
     * and maps it to the[ID] in the [menu] attached to the bottom navigation view
     * if it matches then [navigation] is done
     * [it] means this this particular bottom navigation
     * */
    /*private fun setupBottomNavMenu(navController: NavController){
        bottom_nav_view.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }*/

    override fun onSupportNavigateUp()
            = findNavController(this, R.id.host_fragment).navigateUp()

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,
                Navigation.findNavController(this, R.id.host_fragment))
    }
}