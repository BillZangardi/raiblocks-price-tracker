package me.billzangardi.raiblocks.features.main

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
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
import me.billzangardi.raiblocks.util.ViewUtil
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
    @BindView(R.id.conversion_rate_xrb_btc)
    lateinit var mXrbToBtc: TextView
    @BindView(R.id.conversion_rate_xrb_usd)
    lateinit var mXrbToUsd: TextView
    @BindView(R.id.conversion_rate_xrb_eur)
    lateinit var mXrbToEur: TextView
    @BindView(R.id.conversion_rate_xrb_gbp)
    lateinit var mXrbToGbp: TextView
    @BindView(R.id.navList)
    lateinit var mDrawerList: ListView
    @BindView(R.id.drawer_layout)
    lateinit var mDrawerLayout: DrawerLayout

    private var mAdapter: ArrayAdapter<String>? = null

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        addDrawerItems()
        setupDrawer()

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeButtonEnabled(true);
        mMainPresenter.attachView(this)
        mMainPresenter.fetchData()
        mXrbAmount.setText(MainPrefs.get(this).amountOwned.toString())
        mXrbAmount.setOnClickListener {
            mXrbAmount.setText("")
        }
        mXrbAmount.setOnEditorActionListener { _, actionId, _ ->
            ViewUtil.hideKeyboard(this)
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                MainPrefs.get(this).amountOwned = mXrbAmount.text.toString().toFloat()
                updateData()
                true
            }
            false
        }
        updateData()
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
            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state.  */
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        // Activate the navigation drawer toggle
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
        prefs.xrbToBtc = data.xrbToBtc
        prefs.xrbToUsd = getConversion(data.btcToUsd, data.xrbToBtc)
        prefs.xrbToEur = getConversion(data.btcToEur, data.xrbToBtc)
        prefs.xrbToGbp = getConversion(data.btcToGbp, data.xrbToBtc)
    }

    private fun getConversion(bitcoinValue: Float, xrbValueInBtc: Float): Float {
        return bitcoinValue * xrbValueInBtc
    }

    override fun updateData() {
        val prefs = MainPrefs.get(this)
        mBitcoin.text = String.format(getString(R.string.bitcoin_value), prefs.xrbToBtc * prefs.amountOwned)
        mUsd.text = String.format(getString(R.string.usd_value), prefs.xrbToUsd * prefs.amountOwned)
        mEur.text = String.format(getString(R.string.eur_value), prefs.xrbToEur * prefs.amountOwned)
        mGbp.text = String.format(getString(R.string.gbp_value), prefs.xrbToGbp * prefs.amountOwned)
        mXrbToBtc.text = String.format(getString(R.string.conversion_rate_xrb_btc), prefs.xrbToBtc)
        mXrbToUsd.text = String.format(getString(R.string.conversion_rate_xrb_usd), prefs.xrbToUsd)
        mXrbToEur.text = String.format(getString(R.string.conversion_rate_xrb_eur), prefs.xrbToEur)
        mXrbToGbp.text = String.format(getString(R.string.conversion_rate_xrb_gbp), prefs.xrbToGbp)
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
