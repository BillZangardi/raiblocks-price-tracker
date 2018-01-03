package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.util.ViewUtil


/**
 * Created by zangardiw on 12/24/17.
 */
class TickerWidgetProvider : BaseWidgetProvider() {
    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName, R.layout.ticker_widget)
        if (!TextUtils.isEmpty(prefs.widgetBackgroundHex) && ViewUtil.isColorHex(prefs.widgetBackgroundHex!!)) {
            remoteViews.setInt(R.id.layout, "setBackgroundColor", Color.parseColor(prefs.widgetBackgroundHex))
        }
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
        if (!TextUtils.isEmpty(prefs.widgetFontHex) && ViewUtil.isColorHex(prefs.widgetFontHex!!)) {
            remoteViews.setTextColor(R.id.high, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.last, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.low, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.bitcoin_value_high, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.usd_value_high, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.eur_value_high, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.gbp_value_high, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.bitcoin_value_last, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.usd_value_last, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.eur_value_last, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.gbp_value_last, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.bitcoin_value_low, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.usd_value_low, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.eur_value_low, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.gbp_value_low, Color.parseColor(prefs.widgetFontHex))
        }
        remoteViews.setViewVisibility(R.id.bitcoin_value_high, if (displayBtc) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.bitcoin_value_last, if (displayBtc) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.bitcoin_value_low, if (displayBtc) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.usd_value_high, if (displayUsd) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.usd_value_last, if (displayUsd) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.usd_value_low, if (displayUsd) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.eur_value_high, if (displayEuro) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.eur_value_last, if (displayEuro) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.eur_value_low, if (displayEuro) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.gbp_value_high, if (displayPound) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.gbp_value_last, if (displayPound) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.gbp_value_low, if (displayPound) View.VISIBLE else View.GONE)
        if (appWidgetIds != null) {
            val count = appWidgetIds.size
            (0 until count)
                    .map { appWidgetIds[it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}
