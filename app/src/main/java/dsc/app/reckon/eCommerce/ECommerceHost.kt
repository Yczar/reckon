package dsc.app.reckon.eCommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dsc.app.reckon.R

import kotlinx.android.synthetic.main.ec_activity_e_commerce_host.*

class ECommerceHost : AppCompatActivity() {
    private var drawerLayout : DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ec_activity_e_commerce_host)
        setSupportActionBar(toolbar)

    }

    override fun onSupportNavigateUp()
            = Navigation.findNavController(this, R.id.host_fragment).navigateUp()

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,
                Navigation.findNavController(this, R.id.host_fragment))
    }
}
