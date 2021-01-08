package com.skosc.incontrol.annotation

/**
 * Marks handler method parameter as [ParameterType.DEPENDENCY]
 *
 * @author a.yakovlev
 * @since indev
 */
annotation class Dependency(val name: String = DEFAULT_NAME_RESOLVE) {

    companion object {

        internal const val DEFAULT_NAME_RESOLVE = "__RESOLVE_DEFAULT"
    }
}

/**
 * Checks if name of parameter set to default
 */
val Dependency.isResolveDefault: Boolean get() = name == Dependency.DEFAULT_NAME_RESOLVE