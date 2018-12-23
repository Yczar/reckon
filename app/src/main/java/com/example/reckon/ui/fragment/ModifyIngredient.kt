package com.example.reckon.ui.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.adapter.ModifyIngredientAdapter
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.fragment_modify_ingredient.*
import kotlinx.android.synthetic.main.fragment_totals.*

class ModifyIngredient : Fragment() {

    lateinit var modifyIngredientAdapter: ModifyIngredientAdapter
    lateinit var manager : PrefManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modify_ingredient, container, false)

        setHasOptionsMenu(true)

        //Added null value for titleString -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.modify_ingredients, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.modify_fragment_recycler)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context!!)

            modifyIngredientAdapter = ModifyIngredientAdapter(manager.getIngredientsValuesFromSP() as Map<String, Int>)

            this@ModifyIngredient.modify_fragment_recycler.layoutManager = LinearLayoutManager(context)
            this@ModifyIngredient.modify_fragment_recycler.adapter = modifyIngredientAdapter
        }

        et_total_price.setText(R.string.naira_sign)
        et_total_dcp.setText(R.string.naira_sign)

        return view
    }

    private fun  refreshScreen(){
        makeText(context, "Refreshed clicked", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.main_menu, menu!!)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item!!.itemId

        when(itemId) {
            R.id.menu_action_refresh -> refreshScreen()
        }
        return super.onOptionsItemSelected(item)
    }
}
