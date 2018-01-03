package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.Main
import me.billzangardi.raiblocks.prefs.MainPrefs

class PriceWidgetProvider : BaseWidgetProvider() {
    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.price_widget)
        val string = when (prefs.singleWidgetCurrency) {
            Main.BITCOIN -> String.format(" %.7f", prefs.xrbToBtc)
            Main.EURO -> String.format("€ %.2f", prefs.xrbToEur)
            Main.POUND -> String.format("£ %.2f", prefs.xrbToGbp)
            else -> String.format("$ %.2f", prefs.xrbToUsd)
        }
        remoteViews.setTextViewText(R.id.price, string)
        if (appWidgetIds != null) {
            val count = appWidgetIds.size
            (0 until count)
                    .map { appWidgetIds[it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}