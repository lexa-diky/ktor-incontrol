package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.kodein.autowire.AutoRouting
import io.ktor.routing.*
import org.kodein.di.ktor.di

/**
 * @author a.yakovlev
 * @since indev
 *
 * Enables integration with kodein framework
 */
fun InControl.enableKodeinIntegration() = registerModule {
    diContainer = KodeinDIContainerWrapper(application.di())
}

/**
 * @author a.yakovlev
 * @since indev
 *
 * Enables auto routing feature
 */
fun InControl.enableAutoRoutedControllers() = registerModule {

}
