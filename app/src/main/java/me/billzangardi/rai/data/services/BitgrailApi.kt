package me.billzangardi.rai.data.services

import io.reactivex.Single
import me.billzangardi.rai.data.model.TickerResponse
import retrofit2.http.GET

/**
 * Created by zangardiw on 12/22/17.
 */

interface BitgrailApi {
    @GET("BTC-XRB/ticker")
    fun getCurrentTicker(): Single<TickerResponse>
}