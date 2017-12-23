package me.billzangardi.raiblocks.injection.component

import dagger.Subcomponent
import me.billzangardi.raiblocks.injection.PerFragment
import me.billzangardi.raiblocks.injection.module.FragmentModule

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent