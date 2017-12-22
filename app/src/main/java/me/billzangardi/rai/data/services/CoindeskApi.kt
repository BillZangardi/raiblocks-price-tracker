package me.billzangardi.rai.data.services

import io.reactivex.Single
import me.billzangardi.rai.data.model.BitcoinResponse
import retrofit2.http.GET

/**
 * Created by zangardiw on 12/22/17.
 */

interface CoindeskApi {
    @GET("currentprice.json")
    fun getCurrentBitcoinPrices(): Single<BitcoinResponse>
}