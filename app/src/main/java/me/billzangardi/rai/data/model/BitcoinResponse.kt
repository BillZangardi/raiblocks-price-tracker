package me.billzangardi.rai.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by zangardiw on 12/22/17.
 */

class BitcoinResponse {
    @SerializedName("disclaimer")
    @Expose
    val disclaimer: String? = null
    @SerializedName("chartName")
    @Expose
    val chartName: String? = null
    @SerializedName("bpi")
    @Expose
    val bpi: Bpi? = null
}