package dsc.app.reckon.ui.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import dsc.app.reckon.R
import dsc.app.reckon.adapter.ModifyIngredientAdapter
import dsc.app.reckon.data_model.AgeRange
import dsc.app.reckon.data_model.LiveStockList
import dsc.app.reckon.eCommerce.ECommerceHost
import dsc.app.reckon.eCommerce.database.CartData
import dsc.app.reckon.eCommerce.view_model.AddToCartViewModel
import dsc.app.reckon.utils.AfterIngValueModified
import dsc.app.reckon.utils.PrefManager
import dsc.app.reckon.utils.ToolbarTitleListener
import kotlinx.android.synthetic.main.include_subtitle.view.*
import java.text.DecimalFormat


class ModifyIngredient : Fragment(), AfterIngValueModified {

    lateinit var modifyIngredientAdapter: ModifyIngredientAdapter
    lateinit var manager : PrefManager
    lateinit var ingredientsMap : MutableMap<String, Double>
    lateinit var dcpValuesMap : MutableMap<String, Double>
    lateinit var priceMap : MutableMap<String, Double>
    lateinit var totalDCP : TextInputEditText
    lateinit var totalPrice : TextInputEditText
    lateinit var liveStockAgeRange : AgeRange
    private var feedSize : Double = 0.0
    lateinit var selectedLivestock : LiveStockList
    lateinit var remarkBtn : TextView
    var totalDcpValue : Double = 0.0
    var totalDcpPrice : Double = 0.0

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
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
        liveStockAgeRange = manager.getLiveStockAgeRangeFromSP()

        //Added null value for titleString -*Fave
        (activity as ToolbarTitleListener).updateTitle(R.string.modify_ingredients, null)

        view.tv_sub_title.text = "Age range: ${manager.getSelectedAgeRange().age_range}"

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
        val dcpAndPrice : List<Double> = dsc.app.reckon.utils.FeedFormulation
                .getTotalDCPandPrice(ingredientsMap, dcpValuesMap, priceMap)

        val formatter = DecimalFormat("#0.00")
        totalDcpValue = dcpAndPrice[0]
        totalDcpPrice = dcpAndPrice[1]
        feedSize = dcpAndPrice[2]
        totalDCP.setText(formatter.format(totalDcpValue).toString())
        totalPrice.setText(formatter.format(totalDcpPrice).toString())

        if (totalDcpValue >= liveStockAgeRange.min_dcp!!
                && totalDcpValue < liveStockAgeRange.min_dcp!! + 2.1){
            //good
            remarkBtn.background = resources.getDrawable(R.drawable.remark_green)
            remarkBtn.text = "Very Good"
        }else if (totalDcpValue < liveStockAgeRange.min_dcp!!){
            //not good
            remarkBtn.background = resources.getDrawable(R.drawable.remark_red)
            remarkBtn.text = "Too Low"
        }else if(totalDcpValue > liveStockAgeRange.min_dcp!! + 2.1){
            //fair
            remarkBtn.text = "Too High"
            remarkBtn.background = resources.getDrawable(R.drawable.remark_yellow)
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private fun  refreshScreen(){
        val ft = fragmentManager!!.beginTransaction()
        ft.detach(this).attach(this).commit()

    }

    /**
     * Here is where we go into the [ECommercePacakge]
     * We will use the [AppDatabase] here
     * And the [AppDao]
     * Because we need to add [Cart] details
     * Here
     * */
    private fun addIngredientToCart(){
        val gson = Gson()
        var noOfPacks = 1
        //TODO fix this error
        val cartData = CartData()
        cartData.livestockImage = liveStockAgeRange.image_url
        cartData.livestockType = selectedLivestock.name
        cartData.livestockAgeRange = liveStockAgeRange.age_range
        cartData.initialAmount = totalDcpPrice
        cartData.noOfPacks = noOfPacks
        cartData.totalPrice = totalDcpPrice * noOfPacks
        cartData.totalDCP = totalDcpValue
        cartData.totalSize = feedSize
        cartData.feedingDescription =  liveStockAgeRange.feeding_desc
        cartData.ingredient_list = gson.toJson(ingredientsMap)

        val addToCartViewModel  =
                ViewModelProviders.of(this).get(AddToCartViewModel::class.java)
        addToCartViewModel.insertCartDataToDB(cartData)
        makeText(context, "Added To Cart", LENGTH_SHORT).show()
        val intent = Intent(context, ECommerceHost::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.menu_modify_ingredient, menu!!)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item!!.itemId

        when(itemId) {
            R.id.menu_action_refresh -> refreshScreen()
            R.id.menu_add_to_cart -> addIngredientToCart()
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
