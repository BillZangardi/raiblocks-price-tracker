package me.billzangardi.rai.injection


import javax.inject.Scope

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * [ConfigPersistentComponent]
 */
@Scope
@Retention annotation class ConfigPersistent