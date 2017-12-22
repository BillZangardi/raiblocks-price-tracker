package me.billzangardi.rai.features.main

import me.billzangardi.rai.features.base.BasePresenter
import me.billzangardi.rai.injection.ConfigPersistent
import javax.inject.Inject

/**
 * Created by zangardiw on 12/22/17.
 */
@ConfigPersistent
class MainPresenter @Inject
constructor() : BasePresenter<MainView>() {
    override fun attachView(mvpView: MainView) {
        super.attachView(mvpView)
    }
}