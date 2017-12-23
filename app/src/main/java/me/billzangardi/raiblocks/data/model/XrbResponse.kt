package me.billzangardi.raiblocks.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by zangardiw on 12/22/17.
 */

class XrbResponse {
    @SerializedName("success")
    @Expose
    var success: Int? = null
    @SerializedName("response")
    @Expose
    var response: Response? = null
}