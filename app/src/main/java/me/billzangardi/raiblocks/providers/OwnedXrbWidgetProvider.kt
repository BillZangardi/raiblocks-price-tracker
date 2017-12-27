package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.data.DataManager
import me.billzangardi.raiblocks.data.model.Data
import me.billzangardi.raiblocks.data.services.BitgrailApiFactory
import me.billzangardi.raiblocks.data.services.CoindeskApiFactory
import me.billzangardi.raiblocks.features.main.MainActivity
import me.billzangardi.raiblocks.features.main.MainPresenter
import me.billzangardi.raiblocks.features.main.MainView
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.util.ViewUtil

/**
 * Created by zangardiw on 12/26/17.
 */

class OwnedXrbWidgetProvider : AppWidgetProvider(), MainView {

    private var mMainPresenter: MainPresenter? = null
    private var context: Context? = null
    private var appWidgetIds: IntArray? = null
    private var appWidgetManager: AppWidgetManager? = null

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        this.context = context
        mMainPresenter = MainPresenter(DataManager(BitgrailApiFactory.makeStarterService(), CoindeskApiFactory.makeStarterService()))
        mMainPresenter?.attachView(this)
        mMainPresenter?.fetchData()
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        this.context = context
        this.appWidgetIds = appWidgetIds
        this.appWidgetManager = appWidgetManager
        mMainPresenter = MainPresenter(DataManager(BitgrailApiFactory.makeStarterService(), CoindeskApiFactory.makeStarterService()))
        mMainPresenter?.attachView(this)
        mMainPresenter?.fetchData()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == MainActivity.DATA_CHANGED) {
            this.context = context
            mMainPresenter = MainPresenter(DataManager(BitgrailApiFactory.makeStarterService(), CoindeskApiFactory.makeStarterService()))
            mMainPresenter?.attachView(this)
            mMainPresenter?.fetchData()
        } else {
            super.onReceive(context, intent)

        }
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showError(error: Throwable) {
    }

    override fun updateData() {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.owned_xrb_widget)
        remoteViews.setTextViewText(R.id.owned, prefs.amountOwned.toString() + " " + context!!.getString(R.string.xrb))
        remoteViews.setTextViewText(R.id.bitcoin_value, String.format(context!!.getString(R.string.bitcoin_value), prefs.xrbToBtc * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.usd_value, String.format(context!!.getString(R.string.usd_value), prefs.xrbToUsd * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.eur_value, String.format(context!!.getString(R.string.eur_value), prefs.xrbToEur * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.gbp_value, String.format(context!!.getString(R.string.gbp_value), prefs.xrbToGbp * prefs.amountOwned))
        if (appWidgetIds != null) {
            val count = appWidgetIds!!.size
            (0 until count)
                    .map { appWidgetIds!![it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
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
}
