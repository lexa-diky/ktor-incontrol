package com.skosc.incontrol.controller

/**
 * Class including handler methods
 *
 * @author a.yakovlev
 * @since indev
 */
interface Controller {

    /**
     * Route that will be used for this controller. Will work properly with vanilla Routing module
     */
    val route: String get() = DEFAULT_ROUTE

    companion object {
        private const val DEFAULT_ROUTE = "/"
    }
}