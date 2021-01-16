package com.skosc.incontrol.reflect

/**
 * Utils for determining properties of current running environment
 *
 * @author a.yakovlev
 * @since indev
 */
class RunningEnvironment {

    val mainPackage: String? by lazy {
        val trace = Thread.currentThread().stackTrace
        trace.takeIf { it.isNotEmpty() }
            ?.last()
            ?.className
            ?.split(PACKAGE_SEPARATOR)
            ?.dropLast(1)
            ?.joinToString(separator = PACKAGE_SEPARATOR)
    }

    companion object {
        private const val PACKAGE_SEPARATOR = "."
    }
}