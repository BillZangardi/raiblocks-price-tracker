package me.billzangardi.rai.features.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import me.billzangardi.rai.R
import me.billzangardi.rai.data.model.Data
import me.billzangardi.rai.features.base.BaseActivity
import me.billzangardi.rai.prefs.MainPrefs
import me.billzangardi.rai.util.ViewUtil
import javax.inject.Inject
import android.content.Intent
import android.net.Uri


class MainActivity : BaseActivity(), MainView {

    @Inject lateinit var mMainPresenter: MainPresenter

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

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
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
