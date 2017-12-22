package me.billzangardi.rai.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import me.billzangardi.rai.data.services.BitgrailApi
import me.billzangardi.rai.data.services.BitgrailApiFactory
import me.billzangardi.rai.data.services.CoindeskApi
import me.billzangardi.rai.data.services.CoindeskApiFactory
import me.billzangardi.rai.injection.ApplicationContext
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    internal fun provideBitgrailApi(): BitgrailApi {
        return BitgrailApiFactory.makeStarterService()
    }

    @Provides
    @Singleton
    internal fun provideCoindeskApi(): CoindeskApi {
        return CoindeskApiFactory.makeStarterService()
    }
}
