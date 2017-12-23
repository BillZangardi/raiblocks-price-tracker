package me.billzangardi.raiblocks.data

import io.reactivex.Single
import me.billzangardi.raiblocks.data.model.BitcoinResponse
import me.billzangardi.raiblocks.data.model.XrbResponse
import me.billzangardi.raiblocks.data.services.BitgrailApi
import me.billzangardi.raiblocks.data.services.CoindeskApi
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