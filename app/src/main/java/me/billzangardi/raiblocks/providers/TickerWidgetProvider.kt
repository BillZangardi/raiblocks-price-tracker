package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.MainPrefs


/**
 * Created by zangardiw on 12/24/17.
 */
class TickerWidgetProvider : BaseWidgetProvider() {
    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName, R.layout.ticker_widget)
        remoteViews.setTextViewText(R.id.bitcoin_value_high, String.format(context.getString(R.string.bitcoin_value), prefs.xrbToBtcHigh))
        remoteViews.setTextViewText(R.id.usd_value_high, String.format(context.getString(R.string.usd_value), prefs.xrbToUsdHigh))
        remoteViews.setTextViewText(R.id.eur_value_high, String.format(context.getString(R.string.eur_value), prefs.xrbToEurHigh))
        remoteViews.setTextViewText(R.id.gbp_value_high, String.format(context.getString(R.string.gbp_value), prefs.xrbToGbpHigh))
        remoteViews.setTextViewText(R.id.bitcoin_value_last, String.format(context.getString(R.string.bitcoin_value), prefs.xrbToBtc))
        remoteViews.setTextViewText(R.id.usd_value_last, String.format(context.getString(R.string.usd_value), prefs.xrbToUsd))
        remoteViews.setTextViewText(R.id.eur_value_last, String.format(context.getString(R.string.eur_value), prefs.xrbToEur))
        remoteViews.setTextViewText(R.id.gbp_value_last, String.format(context.getString(R.string.gbp_value), prefs.xrbToGbp))
        remoteViews.setTextViewText(R.id.bitcoin_value_low, String.format(context.getString(R.string.bitcoin_value), prefs.xrbToBtcLow))
        remoteViews.setTextViewText(R.id.usd_value_low, String.format(context.getString(R.string.usd_value), prefs.xrbToUsdLow))
        remoteViews.setTextViewText(R.id.eur_value_low, String.format(context.getString(R.string.eur_value), prefs.xrbToEurLow))
        remoteViews.setTextViewText(R.id.gbp_value_low, String.format(context.getString(R.string.gbp_value), prefs.xrbToGbpLow))
        if (appWidgetIds != null) {
            val count = appWidgetIds.size
            (0 until count)
                    .map { appWidgetIds[it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}
