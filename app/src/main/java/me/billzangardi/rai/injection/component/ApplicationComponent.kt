package me.billzangardi.rai.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import me.billzangardi.rai.data.DataManager
import me.billzangardi.rai.data.services.BitgrailApi
import me.billzangardi.rai.injection.ApplicationContext
import me.billzangardi.rai.injection.module.ApplicationModule
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
