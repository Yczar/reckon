package com.example.reckon.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.reckon.BaseActivity
import com.example.reckon.R
import com.example.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.view.*
import kotlinx.android.synthetic.main.app_entry_point.*

class AppEntryPoint : BaseActivity(), ToolbarTitleListener{


    private var drawerLayout : DrawerLayout? = null
    lateinit var toolbarTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_entry_point)

        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbarTitle = main_toolbar.toolbar_title

        //[Setup] the nav host Fragment
        val fragmentHoster : NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.host_fragment) as NavHostFragment??: return


        //[Setup] Nav Controller
        val navController = fragmentHoster.navController

        //[Setup] Bottom Navigation
        setupBottomNavMenu(navController)
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

    /**
     * Using the NavigationUi and setting up with nav controller
     * check the fragment [ID] in the [navigation_graph]
     * and maps it to the[ID] in the [menu] attached to the bottom navigation view
     * if it matches then [navigation] is done
     * [it] means this this particular bottom navigation
     * */
    private fun setupBottomNavMenu(navController: NavController){
        bottom_nav_view.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    override fun onSupportNavigateUp()
            = findNavController(this, R.id.host_fragment).navigateUp()

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,
                Navigation.findNavController(this, R.id.host_fragment))
    }
}