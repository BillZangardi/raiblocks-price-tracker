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
import me.billzangardi.raiblocks.features.main.MainPresenter
import me.billzangardi.raiblocks.features.main.MainView
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.util.ViewUtil.getConversion
import timber.log.Timber


/**
 * Created by zangardiw on 12/24/17.
 */
class TickerWidgetProvider : AppWidgetProvider(), MainView {

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
        setup(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        setup(context)
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
        Timber.d("Update Data")
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.ticker_widget)
        remoteViews.setTextViewText(R.id.bitcoin_value_high, String.format(context!!.getString(R.string.bitcoin_value), prefs.xrbToBtcHigh))
        remoteViews.setTextViewText(R.id.usd_value_high, String.format(context!!.getString(R.string.usd_value), prefs.xrbToUsdHigh))
        remoteViews.setTextViewText(R.id.eur_value_high, String.format(context!!.getString(R.string.eur_value), prefs.xrbToEurHigh))
        remoteViews.setTextViewText(R.id.gbp_value_high, String.format(context!!.getString(R.string.gbp_value), prefs.xrbToGbpHigh))
        remoteViews.setTextViewText(R.id.bitcoin_value_last, String.format(context!!.getString(R.string.bitcoin_value), prefs.xrbToBtc))
        remoteViews.setTextViewText(R.id.usd_value_last, String.format(context!!.getString(R.string.usd_value), prefs.xrbToUsd))
        remoteViews.setTextViewText(R.id.eur_value_last, String.format(context!!.getString(R.string.eur_value), prefs.xrbToEur))
        remoteViews.setTextViewText(R.id.gbp_value_last, String.format(context!!.getString(R.string.gbp_value), prefs.xrbToGbp))
        remoteViews.setTextViewText(R.id.bitcoin_value_low, String.format(context!!.getString(R.string.bitcoin_value), prefs.xrbToBtcLow))
        remoteViews.setTextViewText(R.id.usd_value_low, String.format(context!!.getString(R.string.usd_value), prefs.xrbToUsdLow))
        remoteViews.setTextViewText(R.id.eur_value_low, String.format(context!!.getString(R.string.eur_value), prefs.xrbToEurLow))
        remoteViews.setTextViewText(R.id.gbp_value_low, String.format(context!!.getString(R.string.gbp_value), prefs.xrbToGbpLow))
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
        prefs.xrbToUsd = getConversion(data.btcToUsd, data.xrbToBtcLast)
        prefs.xrbToEur = getConversion(data.btcToEur, data.xrbToBtcLast)
        prefs.xrbToGbp = getConversion(data.btcToGbp, data.xrbToBtcLast)
        prefs.xrbToUsdHigh = getConversion(data.btcToUsd, data.xrbToBtcHigh)
        prefs.xrbToEurHigh = getConversion(data.btcToEur, data.xrbToBtcHigh)
        prefs.xrbToGbpHigh = getConversion(data.btcToGbp, data.xrbToBtcHigh)
        prefs.xrbToUsdLow = getConversion(data.btcToUsd, data.xrbToBtcLow)
        prefs.xrbToEurLow = getConversion(data.btcToEur, data.xrbToBtcLow)
        prefs.xrbToGbpLow = getConversion(data.btcToGbp, data.xrbToBtcLow)
    }
}
