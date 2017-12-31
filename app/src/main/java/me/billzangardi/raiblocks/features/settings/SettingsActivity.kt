package me.billzangardi.raiblocks.features.settings

import android.os.Bundle
import android.preference.ListPreference
import android.preference.MultiSelectListPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.view.MenuItem
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.prefs.Main

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActionBar()
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, GeneralPreferenceFragment())
                .commit()
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class GeneralPreferenceFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)
            setHasOptionsMenu(true)
            setup()
        }

        private fun setup() {
            val displayCurrencies = findPreference(Main.DISPLAY_CURRENCIES) as MultiSelectListPreference
            displayCurrencies.entries = getCurrencys()
            displayCurrencies.entryValues = getCurrencys()
            val widgetCurrency = findPreference(Main.SINGLE_WIDGET_CURRENCY) as ListPreference
            widgetCurrency.entries = getCurrencys()
            widgetCurrency.entryValues = getCurrencys()
        }

        private fun getCurrencys(): Array<String?> {
            val values = arrayOfNulls<String>(4)
            values[0] = Main.BITCOIN
            values[1] = Main.USD
            values[2] = Main.EURO
            values[3] = Main.POUND
            return values
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                activity.finish()
            }
            return super.onOptionsItemSelected(item)
        }
    }
}
