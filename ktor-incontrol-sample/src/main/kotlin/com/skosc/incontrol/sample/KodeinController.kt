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

class ClassDependency(private val idx: Int) {
    fun retrieve(): String = "ClassDependency$idx"
}

class ParameterDependency(private val idx: Int) {
    fun retrieve(): String = "ParameterDependency$idx"
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
            import(jxInjectorModule)
            bind<ClassDependency>() with instance(ClassDependency(1))
            bind<ParameterDependency>() with instance(ParameterDependency(2))
        }

        routing {
            get<MySampleKodeinController>()
        }
    }.start()
}