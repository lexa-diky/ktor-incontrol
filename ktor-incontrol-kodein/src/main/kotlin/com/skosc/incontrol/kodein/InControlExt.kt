package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
import org.kodein.di.ktor.di

fun InControl.enableKodeinIntegration() = registerModule {
    diContainer = KodeinDIContainerWrapper(application.di())
}
