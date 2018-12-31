package com.example.reckon.ui.fragment


import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reckon.R
import com.example.reckon.adapter.ModifyIngredientAdapter
import com.example.reckon.data_model.LiveStockList
import com.example.reckon.utils.AfterIngValueModified
import com.example.reckon.utils.FeedFormulation
import com.example.reckon.utils.PrefManager
import com.example.reckon.utils.ToolbarTitleListener
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_totals.*
import java.text.DecimalFormat


class ModifyIngredient : Fragment(), AfterIngValueModified {

    lateinit var modifyIngredientAdapter: ModifyIngredientAdapter
    lateinit var manager : PrefManager
    lateinit var ingredientsMap : MutableMap<String, Double>
    lateinit var dcpValuesMap : MutableMap<String, Double>
    lateinit var priceMap : MutableMap<String, Double>
    lateinit var totalDCP : TextInputEditText
    lateinit var totalPrice : TextInputEditText
    private var feedSize : Double = 0.0
    lateinit var selectedLivestock : LiveStockList
    lateinit var remarkBtn : TextView

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modify_ingredient, container, false)

        manager = PrefManager(view.context)
        feedSize = manager.getFeedSizeValue()
        ingredientsMap = manager.getSelectedIngredientsValuesFromSP() as MutableMap<String, Double>
        dcpValuesMap = manager.getIngredientsDCPFromSP() as MutableMap<String, Double>
        priceMap = manager.getIngredientsPriceFromSP() as MutableMap<String, Double>
        selectedLivestock = manager.getSelectedLiveStockFromSP()
        remarkBtn = view.findViewById(R.id.remark_btn)

        //Added null value for titleString -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.modify_ingredients, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.modify_fragment_recycler)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context!!)

            modifyIngredientAdapter = ModifyIngredientAdapter(ingredientsMap,
                    priceMap as Map<String, Int>,
                    this@ModifyIngredient)

            layoutManager = LinearLayoutManager(context)
            adapter = modifyIngredientAdapter
        }
        totalDCP = view.findViewById(R.id.et_total_dcp)
        totalPrice = view.findViewById(R.id.et_total_price)
        computePriceAndDCP()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onValueModified(ingredient: String, value: Double) {
        ingredientsMap[ingredient] = value
        //TODO compute the DCP value and the total price. must be in the background
        computePriceAndDCP()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun computePriceAndDCP(){
        val dcpAndPrice : List<Double> = FeedFormulation
                .getTotalDCPandPrice(ingredientsMap, dcpValuesMap, priceMap, feedSize)

        val formatter = DecimalFormat("#0.00")
        val totalDcpValue = dcpAndPrice[0]
        val totalDcpPrice = dcpAndPrice[1]
        totalDCP.setText(formatter.format(totalDcpValue).toString())
        totalPrice.setText(formatter.format(totalDcpPrice).toString())

        if (totalDcpValue >= selectedLivestock.min_dcp!!
                && totalDcpValue < selectedLivestock.min_dcp!! + 2.1){
            //good
            remarkBtn.background = resources.getDrawable(R.drawable.remark_green)
            remarkBtn.text = "Very Good"
        }else if (totalDcpValue < selectedLivestock.min_dcp!!){
            //not good
            remarkBtn.background = resources.getDrawable(R.drawable.remark_red)
            remarkBtn.text = "Too Low"
        }else if(totalDcpValue > selectedLivestock.min_dcp!! + 2.1){
            //fair
            remarkBtn.text = "Too High"
            remarkBtn.background = resources.getDrawable(R.drawable.remark_yellow)
        }
    }


    private fun  refreshScreen(){
        //TODO implemented this method to show recommended ingredients
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
