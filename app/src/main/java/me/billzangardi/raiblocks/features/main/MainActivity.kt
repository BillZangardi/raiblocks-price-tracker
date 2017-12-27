package me.billzangardi.raiblocks.features.main

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView
import butterknife.BindView
import butterknife.OnClick
import me.billzangardi.raiblocks.R
import me.billzangardi.raiblocks.data.model.Data
import me.billzangardi.raiblocks.features.base.BaseActivity
import me.billzangardi.raiblocks.prefs.MainPrefs
import me.billzangardi.raiblocks.providers.OwnedXrbWidgetProvider
import me.billzangardi.raiblocks.providers.TickerWidgetProvider
import me.billzangardi.raiblocks.util.ViewUtil
import me.billzangardi.raiblocks.util.ViewUtil.getConversion
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {

    @Inject lateinit var mMainPresenter: MainPresenter
    private var mDrawerToggle: ActionBarDrawerToggle? = null

    @BindView(R.id.edit_xrb_amount)
    lateinit var mXrbAmount: EditText
    @BindView(R.id.full_screen_progress)
    lateinit var mProgress: FrameLayout
    @BindView(R.id.usd_value)
    lateinit var mUsd: TextView
    @BindView(R.id.eur_value)
    lateinit var mEur: TextView
    @BindView(R.id.gbp_value)
    lateinit var mGbp: TextView
    @BindView(R.id.bitcoin_value)
    lateinit var mBitcoin: TextView
    @BindView(R.id.usd_value_high)
    lateinit var mUsdHigh: TextView
    @BindView(R.id.eur_value_high)
    lateinit var mEurHigh: TextView
    @BindView(R.id.gbp_value_high)
    lateinit var mGbpHigh: TextView
    @BindView(R.id.bitcoin_value_high)
    lateinit var mBitcoinHigh: TextView
    @BindView(R.id.usd_value_low)
    lateinit var mUsdLow: TextView
    @BindView(R.id.eur_value_low)
    lateinit var mEurLow: TextView
    @BindView(R.id.gbp_value_low)
    lateinit var mGbpLow: TextView
    @BindView(R.id.bitcoin_value_low)
    lateinit var mBitcoinLow: TextView
    @BindView(R.id.bitcoin_value_last)
    lateinit var mBitcoinlast: TextView
    @BindView(R.id.usd_value_last)
    lateinit var mUsdLast: TextView
    @BindView(R.id.eur_value_last)
    lateinit var mEurLast: TextView
    @BindView(R.id.gbp_value_last)
    lateinit var mGbpLast: TextView
    @BindView(R.id.navList)
    lateinit var mDrawerList: ListView
    @BindView(R.id.drawer_layout)
    lateinit var mDrawerLayout: DrawerLayout

    private var mAdapter: ArrayAdapter<String>? = null
    private var mHandler: Handler? = null

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        addDrawerItems()
        setupDrawer()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        mXrbAmount.setText(MainPrefs.get(this).amountOwned.toString())
        mXrbAmount.setOnClickListener {
            mXrbAmount.setText("")
        }
        mXrbAmount.setOnEditorActionListener { _, actionId, _ ->
            ViewUtil.hideKeyboard(this)
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                MainPrefs.get(this).amountOwned = mXrbAmount.text.toString().toFloat()
                updateData()
                mXrbAmount.isCursorVisible = false
                true
            }
            false
        }
    }

    private fun setupRefresh() {
        val delay: Long = 30000 //30 seconds
        var runnable: Runnable
        mHandler = Handler()
        mHandler!!.postDelayed(object : Runnable {
            override fun run() {
                if (mMainPresenter.isViewAttached) {
                    mMainPresenter.fetchData()
                }
                runnable = this
                mHandler?.postDelayed(runnable, delay)
            }
        }, delay)
        mMainPresenter.fetchData()
        updateData()
    }

    override fun onResume() {
        super.onResume()
        mMainPresenter.attachView(this)
        setupRefresh()
    }

    override fun onStop() {
        super.onStop()
        mHandler = null
    }

    private fun addDrawerItems() {
        val osArray = arrayOf("Wallet", "Website", "Exchange - BitGrail", "Reddit", "Discord", "Twitter", "Donations")
        mAdapter = ArrayAdapter(this, R.layout.nav_menu_item, osArray)
        mDrawerList.adapter = mAdapter
        mDrawerList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var url: String = ""
            when (position) {
                0 -> url = "https://raiwallet.com/"
                1 -> url = "https://raiblocks.net/"
                2 -> url = "https://bitgrail.com/"
                3 -> url = "https://www.reddit.com/r/RaiBlocks"
                4 -> url = "https://discordapp.com/invite/JphbBas"
                5 -> url = "https://twitter.com/raiblocks"
            }
            if (!TextUtils.isEmpty(url)) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else {
                showDonationDialog()
            }
        }
    }

    private fun showDonationDialog() {
        val alert = AlertDialog.Builder(this)
        val imageview = ImageView(this)
        imageview.setImageDrawable(resources.getDrawable(R.drawable.qrcode))
        alert.setView(imageview)
        alert.setMessage(getString(R.string.donation_address))
        alert.setNegativeButton(getString(R.string.generic_done)
        ) { dialog, id -> dialog.dismiss() }
        alert.show()
    }


    private fun setupDrawer() {
        mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            override fun onDrawerClosed(view: View?) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        mDrawerToggle?.isDrawerIndicatorEnabled = true
        mDrawerLayout.setDrawerListener(mDrawerToggle)
        supportActionBar?.title = ""
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (mDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }

    override fun showProgress(show: Boolean) {
        mProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        updateData()
    }

    override fun storeData(data: Data) {
        val prefs = MainPrefs.get(this)
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

    override fun updateData() {
        val prefs = MainPrefs.get(this)
        mBitcoin.text = String.format(getString(R.string.bitcoin_value), prefs.xrbToBtc * prefs.amountOwned)
        mUsd.text = String.format(getString(R.string.usd_value), prefs.xrbToUsd * prefs.amountOwned)
        mEur.text = String.format(getString(R.string.eur_value), prefs.xrbToEur * prefs.amountOwned)
        mGbp.text = String.format(getString(R.string.gbp_value), prefs.xrbToGbp * prefs.amountOwned)
        mBitcoinHigh.text = String.format(getString(R.string.bitcoin_value), prefs.xrbToBtcHigh)
        mUsdHigh.text = String.format(getString(R.string.usd_value), prefs.xrbToUsdHigh)
        mEurHigh.text = String.format(getString(R.string.eur_value), prefs.xrbToEurHigh)
        mGbpHigh.text = String.format(getString(R.string.gbp_value), prefs.xrbToGbpHigh)
        mBitcoinLow.text = String.format(getString(R.string.bitcoin_value), prefs.xrbToBtcLow)
        mUsdLow.text = String.format(getString(R.string.usd_value), prefs.xrbToUsdLow)
        mEurLow.text = String.format(getString(R.string.eur_value), prefs.xrbToEurLow)
        mGbpLow.text = String.format(getString(R.string.gbp_value), prefs.xrbToGbpLow)
        mBitcoinlast.text = String.format(getString(R.string.bitcoin_value), prefs.xrbToBtc)
        mUsdLast.text = String.format(getString(R.string.usd_value), prefs.xrbToUsd)
        mEurLast.text = String.format(getString(R.string.eur_value), prefs.xrbToEur)
        mGbpLast.text = String.format(getString(R.string.gbp_value), prefs.xrbToGbp)
        updateWidgets()
    }

    private fun updateWidgets() {
        updateTickerWidget()
        updateOwnedWidget()
    }

    private fun updateOwnedWidget() {
        val intent = Intent(this, OwnedXrbWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, OwnedXrbWidgetProvider::class.java))
        val myWidget = OwnedXrbWidgetProvider()
        myWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }

    private fun updateTickerWidget() {
        val intent = Intent(this, TickerWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(ComponentName(this, TickerWidgetProvider::class.java!!))
        val myWidget = TickerWidgetProvider()
        myWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }

    @OnClick(R.id.coindesk_logo)
    fun onCoindeskClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coindesk.com/")))
    }

    @OnClick(R.id.bitgrail_logo)
    fun onBitgrailClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://bitgrail.com/")))
    }
}
