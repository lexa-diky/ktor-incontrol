package com.skosc.incontrol.sample

import com.skosc.incontrol.InControl
import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.kodein.enableKodeinIntegration
import com.skosc.incontrol.kodein.get
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.jxinject.jxInjectorModule
import org.kodein.di.ktor.di
import javax.inject.Inject

class ClassDependency @Inject constructor() {
    fun retrieve(): String = "ClassDependency"
}

class ParameterDependency @Inject constructor() {
    fun retrieve(): String = "ParameterDependency"
}

class MySampleKodeinController @Inject constructor(private val classDependency: ClassDependency) : Controller {

    suspend fun handle(@Dependency parameterDependency: ParameterDependency, @Dependency call: ApplicationCall) =
        "(${classDependency.retrieve()}, ${parameterDependency.retrieve()}, ${call.request.headers})"
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Routing)
        install(InControl) {
            enableKodeinIntegration()
        }
        di {
            import( jxInjectorModule)
            bind<ClassDependency>() with instance(ClassDependency())
            bind<ParameterDependency>() with instance(ParameterDependency())
        }

        routing {
            get<MySampleKodeinController>()
        }
    }.start()
}