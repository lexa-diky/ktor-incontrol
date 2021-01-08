package com.skosc.incontrol.annotation

/**
 * Marks handler method parameter as [ParameterType.QUERY]
 *
 * @author a.yakovlev
 * @since indev
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(val name: String = DEFAULT_NAME_RESOLVE) {

    companion object {

        internal const val DEFAULT_NAME_RESOLVE = "__RESOLVE_DEFAULT"
    }
}

/**
 * Checks if name of parameter set to default
 */
val Query.isResolveDefault: Boolean get() = name == Query.DEFAULT_NAME_RESOLVE