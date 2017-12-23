package me.billzangardi.raiblocks.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by zangardiw on 12/22/17.
 */
class Bpi {
    @SerializedName("USD")
    @Expose
    val uSD: Usd? = null
    @SerializedName("GBP")
    @Expose
    val gBP: Gbp? = null
    @SerializedName("EUR")
    @Expose
    val eUR: Eur? = null
}