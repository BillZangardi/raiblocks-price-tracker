package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import me.billzangardi.raiblocks.data.DataManager
import me.billzangardi.raiblocks.data.model.Data
import me.billzangardi.raiblocks.data.services.BitgrailApiFactory
import me.billzangardi.raiblocks.data.services.CoindeskApiFactory
import me.billzangardi.raiblocks.features.main.MainActivity
import me.billzangardi.raiblocks.features.main.MainPresenter
import me.billzangardi.raiblocks.features.main.MainView
import me.billzangardi.raiblocks.prefs.Main
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.util.ViewUtil

/**
 * Created by zangardiw on 12/26/17.
 */
abstract class BaseWidgetProvider : AppWidgetProvider(), MainView {

    private var mMainPresenter: MainPresenter? = null
    private var context: Context? = null
    private var appWidgetIds: IntArray? = null
    private var appWidgetManager: AppWidgetManager? = null

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        setup(context)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        this.appWidgetIds = appWidgetIds
        this.appWidgetManager = appWidgetManager
        this.context = context
        updateData()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        this.context = context
        if (intent?.action == MainActivity.DATA_UPDATED) {
            updateData()
        } else {
            setup(context)
        }
    }

    private fun setup(context: Context?) {
        this.context = context
        mMainPresenter = MainPresenter(DataManager(BitgrailApiFactory.makeStarterService(), CoindeskApiFactory.makeStarterService()))
        mMainPresenter?.attachView(this)
        mMainPresenter?.fetchData()
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showError(error: Throwable) {
    }


    override fun updateData() {
        var displayBtc = false
        var displayUsd = false
        var displayEuro = false
        var displayPound = false
        MainPrefs.get(context).displayCurrencys.forEach { currency ->
            when (currency) {
                Main.BITCOIN -> displayBtc = true
                Main.USD -> displayUsd = true
                Main.EURO -> displayEuro = true
                Main.POUND -> displayPound = true
            }
        }
        updateViews(context, appWidgetManager, appWidgetIds, displayBtc, displayUsd, displayEuro, displayPound, MainPrefs.get(context).widgetBackgroundHex, MainPrefs.get(context).widgetFontHex)
    }

    override fun storeData(data: Data) {
        val prefs = MainPrefs.get(context)
        prefs.xrbToBtc = data.xrbToBtcLast
        prefs.xrbToBtcHigh = data.xrbToBtcHigh
        prefs.xrbToBtcLow = data.xrbToBtcLow
        prefs.xrbToUsd = ViewUtil.getConversion(data.btcToUsd, data.xrbToBtcLast)
        prefs.xrbToEur = ViewUtil.getConversion(data.btcToEur, data.xrbToBtcLast)
        prefs.xrbToGbp = ViewUtil.getConversion(data.btcToGbp, data.xrbToBtcLast)
        prefs.xrbToUsdHigh = ViewUtil.getConversion(data.btcToUsd, data.xrbToBtcHigh)
        prefs.xrbToEurHigh = ViewUtil.getConversion(data.btcToEur, data.xrbToBtcHigh)
        prefs.xrbToGbpHigh = ViewUtil.getConversion(data.btcToGbp, data.xrbToBtcHigh)
        prefs.xrbToUsdLow = ViewUtil.getConversion(data.btcToUsd, data.xrbToBtcLow)
        prefs.xrbToEurLow = ViewUtil.getConversion(data.btcToEur, data.xrbToBtcLow)
        prefs.xrbToGbpLow = ViewUtil.getConversion(data.btcToGbp, data.xrbToBtcLow)
    }

    abstract fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean, backgroundHex: String?, fontHex: String?)

}