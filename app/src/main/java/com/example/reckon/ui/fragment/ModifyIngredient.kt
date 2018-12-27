package com.example.reckon.ui.fragment


import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.adapter.ModifyIngredientAdapter
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.fragment_totals.view.*


class ModifyIngredient : Fragment() {

    lateinit var modifyIngredientAdapter: ModifyIngredientAdapter
    lateinit var manager : PrefManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modify_ingredient, container, false)

        manager = PrefManager(view.context)

        //Added null value for titleString -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.modify_ingredients, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.modify_fragment_recycler)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context!!)

            modifyIngredientAdapter = ModifyIngredientAdapter(manager.getIngredientsValuesFromSP() as Map<String, Int>)

            layoutManager = LinearLayoutManager(context)
            adapter = modifyIngredientAdapter
        }
        view.et_total_price.setText("0")
        view.et_total_dcp.setText("0")

        return view
    }

    private fun  refreshScreen(){
        makeText(context, "Refreshed clicked", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.menu_modify_ingredient, menu!!)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item!!.itemId

        when(itemId) {
            R.id.menu_action_refresh -> refreshScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }*/
    }

    private fun showFullScreen() {
        if (Build.VERSION.SDK_INT in 12..18) { // for lower api
            val v = this.activity!!.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = this.activity!!.window.decorView// for fragment use getActivity().getWindow().getDecorView();
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onResume() {
        super.onResume()

        /*activity!!.window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }*/
    }
}
