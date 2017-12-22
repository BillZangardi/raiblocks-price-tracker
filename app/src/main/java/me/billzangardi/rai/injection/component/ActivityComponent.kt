package me.billzangardi.rai.injection.component

import dagger.Subcomponent
import me.billzangardi.rai.features.base.BaseActivity
import me.billzangardi.rai.features.main.MainActivity
import me.billzangardi.rai.injection.PerActivity
import me.billzangardi.rai.injection.module.ActivityModule

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(mainActivity: MainActivity)

}
