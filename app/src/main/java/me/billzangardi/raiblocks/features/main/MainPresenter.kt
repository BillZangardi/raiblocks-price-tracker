package me.billzangardi.raiblocks.features.main

import me.billzangardi.raiblocks.data.DataManager
import me.billzangardi.raiblocks.data.model.BitcoinResponse
import me.billzangardi.raiblocks.data.model.Data
import me.billzangardi.raiblocks.data.model.XrbResponse
import me.billzangardi.raiblocks.features.base.BasePresenter
import me.billzangardi.raiblocks.injection.ConfigPersistent
import me.billzangardi.raiblocks.util.rx.scheduler.SchedulerUtils
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
                                val data: Data = Data(bitcoinResponse.bpi!!.uSD!!.rateFloat, bitcoinResponse.bpi!!.eUR!!.rateFloat, bitcoinResponse.bpi!!.gBP!!.rateFloat, xrbResponse!!.response!!.last!!.toFloat(), xrbResponse!!.response!!.high!!.toFloat(), xrbResponse!!.response!!.low!!.toFloat())
                                mvpView?.storeData(data)
                                mvpView?.showProgress(false)
                                mvpView?.updateData()
                            }) { throwable ->
                                mvpView?.showError(throwable)
                            }
                }) { throwable ->
                    mvpView?.showError(throwable)
                }
    }
}