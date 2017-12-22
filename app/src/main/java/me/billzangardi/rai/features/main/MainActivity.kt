package me.billzangardi.rai.features.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import me.billzangardi.rai.R
import me.billzangardi.rai.features.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {


    @Inject lateinit var mMainPresenter: MainPresenter

    override val layout: Int
        get() = R.layout.activity_main

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
    }
}
