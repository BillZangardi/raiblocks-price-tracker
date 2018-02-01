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
 * Created by zangardiw on 12/26/17.
 */

class OwnedXrbWidgetProvider : BaseWidgetProvider() {
    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.owned_xrb_widget)
        if(!TextUtils.isEmpty(prefs.widgetBackgroundHex) && ViewUtil.isColorHex(prefs.widgetBackgroundHex!!)) {
            remoteViews.setInt(R.id.layout, "setBackgroundColor", Color.parseColor(prefs.widgetBackgroundHex))
        }
        remoteViews.setTextViewText(R.id.owned, prefs.amountOwned.toString() + " " + context.getString(R.string.nano))
        remoteViews.setTextViewText(R.id.bitcoin_value, String.format(context.getString(R.string.bitcoin_value), prefs.xrbToBtc * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.usd_value, String.format(context.getString(R.string.usd_value), prefs.xrbToUsd * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.eur_value, String.format(context.getString(R.string.eur_value), prefs.xrbToEur * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.gbp_value, String.format(context.getString(R.string.gbp_value), prefs.xrbToGbp * prefs.amountOwned))
        if(!TextUtils.isEmpty(prefs.widgetFontHex) && ViewUtil.isColorHex(prefs.widgetFontHex!!)) {
            remoteViews.setTextColor(R.id.owned, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.bitcoin_value, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.usd_value, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.eur_value, Color.parseColor(prefs.widgetFontHex))
            remoteViews.setTextColor(R.id.gbp_value, Color.parseColor(prefs.widgetFontHex))
        }
        remoteViews.setViewVisibility(R.id.bitcoin_value, if (displayBtc) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.usd_value, if (displayUsd) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.eur_value, if (displayEuro) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.gbp_value, if (displayPound) View.VISIBLE else View.GONE)
        if (appWidgetIds != null) {
            val count = appWidgetIds.size
            (0 until count)
                    .map { appWidgetIds[it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}
