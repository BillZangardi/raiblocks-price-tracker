package me.billzangardi.rai.data

import io.reactivex.Single
import me.billzangardi.rai.data.model.BitcoinResponse
import me.billzangardi.rai.data.model.XrbResponse
import me.billzangardi.rai.data.services.BitgrailApi
import me.billzangardi.rai.data.services.CoindeskApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by zangardiw on 12/22/17.
 */
@Singleton
class DataManager @Inject
constructor(private val mBitgrailApi: BitgrailApi, private val mCoindeskApi: CoindeskApi) {
    fun getXrbPrice(): Single<XrbResponse> {
        return mBitgrailApi.getCurrentTicker()
    }

    fun getBitcoinPrice(): Single<BitcoinResponse> {
        return mCoindeskApi.getCurrentBitcoinPrices()
    }
}