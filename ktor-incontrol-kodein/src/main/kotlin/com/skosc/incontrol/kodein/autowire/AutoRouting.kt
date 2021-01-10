package com.skosc.incontrol.kodein.autowire

/**
 * @author a.yakovlev
 * @since indev
 *
 * Marks controller as suitable for auto routing
 *
 * @param method - defines witch HTTP method will be assosiated with this controller (e.g. GET or POST)
 * @param group - allows to filter controller routing position
 */
annotation class AutoRouting(val method: String, val group: String = DEFAULT_GROUP) {

    companion object {

        const val DEFAULT_GROUP = ""
    }
}