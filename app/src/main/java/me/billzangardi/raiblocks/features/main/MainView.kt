package me.billzangardi.raiblocks.features.main

import me.billzangardi.raiblocks.data.model.Data
import me.billzangardi.raiblocks.features.base.MvpView

/**
 * Created by zangardiw on 12/22/17.
 */
interface MainView : MvpView {
    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

    fun updateData()

    fun storeData(data: Data)
}