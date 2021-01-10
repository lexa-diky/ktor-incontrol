package com.skosc.incontrol.annotation

/**
 * Marks handler method parameter as [ParameterType.DEPENDENCY]
 *
 * @author a.yakovlev
 * @since indev
 */
annotation class Dependency(val tag: String = DEFAULT_TAG_RESOLVE) {

    companion object {

        internal const val DEFAULT_TAG_RESOLVE = "__RESOLVE_DEFAULT"
    }
}

/**
 * Checks if name of parameter set to default
 */
val Dependency.isResolveDefault: Boolean get() = tag == Dependency.DEFAULT_TAG_RESOLVE