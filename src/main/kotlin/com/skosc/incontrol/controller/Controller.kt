package com.skosc.incontrol.controller

/**
 * Class including handler methods
 *
 * @author a.yakovlev
 * @since indev
 */
interface Controller {

    val route: String get() = DEFAULT_ROUTE

    companion object {
        private const val DEFAULT_ROUTE = "/"
    }
}