package me.billzangardi.raiblocks.injection.component

import dagger.Subcomponent
import me.billzangardi.raiblocks.features.base.BaseActivity
import me.billzangardi.raiblocks.features.main.MainActivity
import me.billzangardi.raiblocks.injection.PerActivity
import me.billzangardi.raiblocks.injection.module.ActivityModule

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(mainActivity: MainActivity)

}
