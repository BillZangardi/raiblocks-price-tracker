package me.billzangardi.raiblocks.injection.component

import dagger.Component
import me.billzangardi.raiblocks.injection.ConfigPersistent
import me.billzangardi.raiblocks.injection.module.ActivityModule
import me.billzangardi.raiblocks.injection.module.FragmentModule

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't
 * be destroy during configuration changes. Check [BaseActivity] and [BaseFragment] to
 * see how this components survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent

    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
