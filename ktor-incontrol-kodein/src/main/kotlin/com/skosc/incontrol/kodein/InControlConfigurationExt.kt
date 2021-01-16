package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
import com.skosc.incontrol.InControlConfiguration
import org.kodein.di.ktor.di

/**
 * @author a.yakovlev
 * @since indev
 *
 * Enables integration with kodein framework
 */
fun InControlConfiguration.enableKodeinIntegration() {
        diContainer = KodeinDIContainerWrapper(application.di())
}

/**
 * @author a.yakovlev
 * @since indev
 *
 * Enables auto routing feature
 */
fun InControlConfiguration.enableAutoRoutedControllers() {

}
