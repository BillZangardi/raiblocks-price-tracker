package me.billzangardi.raiblocks.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import me.billzangardi.raiblocks.data.DataManager
import me.billzangardi.raiblocks.data.services.BitgrailApi
import me.billzangardi.raiblocks.injection.ApplicationContext
import me.billzangardi.raiblocks.injection.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun bitgrailApi(): BitgrailApi

    fun dataManager(): DataManager
}
