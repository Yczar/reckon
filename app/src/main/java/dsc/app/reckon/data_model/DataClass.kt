package dsc.app.reckon.data_model

//Making class be nullable
data class LiveStockList(
        var id: String? = null,
        val image : String? = null,
        val name : String? = null
)

data class AgeRange(
        val age_range : String? = null,
        val image_url : String? = null,
        val feeding_desc : String? = null,
        val ingredients: Map<String, Any>? = null,
        val sub_ingredients: Map<String, Any>? = null,
        val min_dcp : Double? = null
)

data class IngredientsPrice(
        val ingredients_price : Map<String, Any>? = null
)
data class IngredientsDCP(
        val ingredients_dcp : Map<String, Any>? = null
)