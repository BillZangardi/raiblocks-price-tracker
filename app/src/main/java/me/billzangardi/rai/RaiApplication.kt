package me.billzangardi.rai

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import me.billzangardi.rai.injection.component.ApplicationComponent
import me.billzangardi.rai.injection.component.DaggerApplicationComponent
import me.billzangardi.rai.injection.module.ApplicationModule
import timber.log.Timber

/**
 * Created by zangardiw on 12/22/17.
 */
class RaiApplication : MultiDexApplication() {

    private var mApplicationComponent: ApplicationComponent? = null

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        }
    }

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent
        get() {
            if (mApplicationComponent == null) {
                mApplicationComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .build()
            }
            return mApplicationComponent as ApplicationComponent
        }
        set(applicationComponent) {
            mApplicationComponent = applicationComponent
        }

    companion object {
        operator fun get(context: Context): RaiApplication {
            return context.applicationContext as RaiApplication
        }
    }
}