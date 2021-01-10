package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
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
