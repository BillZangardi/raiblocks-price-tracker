package me.billzangardi.raiblocks.providers

import android.appwidget.AppWidgetManager
import android.content.Context
import android.view.View
import android.widget.RemoteViews
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.MainPrefs

/**
 * Created by zangardiw on 12/26/17.
 */

class OwnedXrbWidgetProvider : BaseWidgetProvider() {

    override fun updateViews(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?, displayBtc: Boolean, displayUsd: Boolean, displayEuro: Boolean, displayPound: Boolean) {
        val prefs = MainPrefs.get(context)
        val remoteViews = RemoteViews(context!!.packageName,
                R.layout.owned_xrb_widget)
        remoteViews.setTextViewText(R.id.owned, prefs.amountOwned.toString() + " " + context.getString(R.string.xrb))
        remoteViews.setTextViewText(R.id.bitcoin_value, String.format(context.getString(R.string.bitcoin_value), prefs.xrbToBtc * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.usd_value, String.format(context.getString(R.string.usd_value), prefs.xrbToUsd * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.eur_value, String.format(context.getString(R.string.eur_value), prefs.xrbToEur * prefs.amountOwned))
        remoteViews.setTextViewText(R.id.gbp_value, String.format(context.getString(R.string.gbp_value), prefs.xrbToGbp * prefs.amountOwned))
        remoteViews.setViewVisibility(R.id.bitcoin_value, if (displayBtc) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.usd_value, if (displayUsd) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.eur_value, if (displayEuro) View.VISIBLE else View.GONE)
        remoteViews.setViewVisibility(R.id.gbp_value, if (displayPound) View.VISIBLE else View.GONE)
        if (appWidgetIds != null) {
            val count = appWidgetIds!!.size
            (0 until count)
                    .map { appWidgetIds!![it] }
                    .forEach { appWidgetManager!!.updateAppWidget(it, remoteViews) }
        }
    }
}
