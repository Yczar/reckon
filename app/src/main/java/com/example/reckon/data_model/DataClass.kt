package com.example.reckon.data_model

import java.util.*

//Making class be nullable
data class LiveStockList(
        var id: String? = null,
        val image : String? = null,
        val name : String? = null
)

data class AgeRange(
        val age_ranges: Map<String, Objects>? = null

)