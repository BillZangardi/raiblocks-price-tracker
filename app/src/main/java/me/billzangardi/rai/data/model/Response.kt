package me.billzangardi.rai.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by zangardiw on 12/22/17.
 */
class Response {

    @SerializedName("last")
    @Expose
    val last: String? = null
    @SerializedName("high")
    @Expose
    val high: String? = null
    @SerializedName("low")
    @Expose
    val low: String? = null
    @SerializedName("volume")
    @Expose
    val volume: String? = null
    @SerializedName("coinVolume")
    @Expose
    val coinVolume: String? = null
    @SerializedName("bid")
    @Expose
    val bid: String? = null
    @SerializedName("ask")
    @Expose
    val ask: String? = null
}