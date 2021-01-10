package com.skosc.incontrol.sample

import com.skosc.incontrol.InControl
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.kodein.autowire.AutoRouting
import com.skosc.incontrol.kodein.autowire.autoRoute
import com.skosc.incontrol.kodein.enableAutoRoutedControllers
import com.skosc.incontrol.kodein.enableKodeinIntegration
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.bind
import org.kodein.di.jxinject.jxInjectorModule
import org.kodein.di.ktor.di
import org.kodein.di.provider
import javax.inject.Inject

@AutoRouting("GET")
class SampleAutoRoutingController @Inject constructor(private val dependency: SampleAutoRoutingControllerDependency) :
    Controller {

    suspend fun handle() = "Hello world: $dependency"
}

class SampleAutoRoutingControllerDependency @Inject constructor() {

    override fun toString(): String = "DEPENDENCY"
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Routing)
        install(InControl) {
            enableKodeinIntegration()
            enableAutoRoutedControllers()
        }

        di {
            import(jxInjectorModule)
            bind<SampleAutoRoutingControllerDependency>() with provider { SampleAutoRoutingControllerDependency() }
        }

        routing {
            autoRoute("com.skosc")
        }
    }.start()
}