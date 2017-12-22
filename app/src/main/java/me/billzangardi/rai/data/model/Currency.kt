package me.billzangardi.rai.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by zangardiw on 12/22/17.
 */
open class Currency {
    @SerializedName("code")
    @Expose
    val code: String? = null
    @SerializedName("symbol")
    @Expose
    val symbol: String? = null
    @SerializedName("rate")
    @Expose
    val rate: String? = null
    @SerializedName("description")
    @Expose
    val description: String? = null
    @SerializedName("rate_float")
    @Expose
    val rateFloat: Float = 0.00f
}