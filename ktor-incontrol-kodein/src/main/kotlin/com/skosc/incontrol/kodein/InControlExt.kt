package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
import io.ktor.routing.*
import org.kodein.di.ktor.di

fun InControl.enableKodeinIntegration() = registerModule {
    diContainer = KodeinDIContainerWrapper(application.di())
}

fun InControl.enableAutoRoutedControllers() = registerModule {
    with(application) {
        routing {

        }
    }
}
