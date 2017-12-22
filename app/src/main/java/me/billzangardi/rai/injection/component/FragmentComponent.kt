package me.billzangardi.rai.injection.component

import dagger.Subcomponent
import me.billzangardi.rai.injection.PerFragment
import me.billzangardi.rai.injection.module.FragmentModule

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent