package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.Main
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.util.ViewUtil

class PriceWidgetProvider : BaseWidgetProvider() {
    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean, backgroundHex: String?, fontHex: String?) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.price_widget)
        val string = when (prefs.singleWidgetCurrency) {
            Main.BITCOIN -> String.format(" %.7f", prefs.xrbToBtc)
            Main.EURO -> String.format("€ %.2f", prefs.xrbToEur)
            Main.POUND -> String.format("£ %.2f", prefs.xrbToGbp)
            else -> String.format("$ %.2f", prefs.xrbToUsd)
        }
        if(!TextUtils.isEmpty(backgroundHex) && ViewUtil.isColorHex(backgroundHex!!)) {
            remoteViews.setInt(R.id.price, "setBackgroundColor", Color.parseColor(backgroundHex))
        }
        remoteViews.setTextViewText(R.id.price, string)
        if(!TextUtils.isEmpty(fontHex) && ViewUtil.isColorHex(fontHex!!)) {
            remoteViews.setTextColor(R.id.price, Color.parseColor(fontHex))
        }
        if (appWidgetIds != null) {
            val count = appWidgetIds.size
            (0 until count)
                    .map { appWidgetIds[it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}