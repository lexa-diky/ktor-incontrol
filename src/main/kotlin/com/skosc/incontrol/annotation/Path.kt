package com.skosc.incontrol.annotation

/**
 * Marks handler method parameter as [ParameterType.Path]
 *
 * @author a.yakovlev
 * @since indev
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val name: String = DEFAULT_NAME_RESOLVE) {

    companion object {

        internal const val DEFAULT_NAME_RESOLVE = "__RESOLVE_DEFAULT"
    }
}

val Path.isResolveDefault: Boolean get() = name == Path.DEFAULT_NAME_RESOLVE