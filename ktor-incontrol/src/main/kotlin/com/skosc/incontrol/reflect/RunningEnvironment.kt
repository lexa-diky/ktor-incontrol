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
        if (trace.isNotEmpty()) {
            trace[trace.size - 1].className
                .split(PACKAGE_SEPARATOR)
                .dropLast(1)
                .joinToString(separator = PACKAGE_SEPARATOR)
        } else {
            null
        }
    }

    companion object {
        private const val PACKAGE_SEPARATOR = "."
    }
}