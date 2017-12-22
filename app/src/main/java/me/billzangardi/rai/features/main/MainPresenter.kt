package me.billzangardi.rai.features.main

import me.billzangardi.rai.data.DataManager
import me.billzangardi.rai.data.model.BitcoinResponse
import me.billzangardi.rai.data.model.Data
import me.billzangardi.rai.data.model.XrbResponse
import me.billzangardi.rai.features.base.BasePresenter
import me.billzangardi.rai.injection.ApplicationContext
import me.billzangardi.rai.injection.ConfigPersistent
import me.billzangardi.rai.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

/**
 * Created by zangardiw on 12/22/17.
 */
@ConfigPersistent
class MainPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MainView>() {
    override fun attachView(mvpView: MainView) {
        super.attachView(mvpView)
    }

    fun fetchData() {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getXrbPrice().compose<XrbResponse>(SchedulerUtils.ioToMain<XrbResponse>())
                .subscribe({ xrbResponse ->
                    mDataManager.getBitcoinPrice().compose<BitcoinResponse>(SchedulerUtils.ioToMain<BitcoinResponse>())
                            .subscribe({ bitcoinResponse ->
                                val data: Data = Data(bitcoinResponse.bpi!!.uSD!!.rateFloat, bitcoinResponse.bpi!!.eUR!!.rateFloat, bitcoinResponse.bpi!!.gBP!!.rateFloat, xrbResponse!!.response!!.last!!.toFloat())
                                mvpView?.storeData(data)
                                mvpView?.showProgress(false)
                                mvpView?.updateData()
                            }) { throwable ->
                            }
                }) { throwable ->
                }
    }

    private fun getConversion(currentRate: Double, bitcoinValue: Double): Double {
        return bitcoinValue * currentRate
    }
}