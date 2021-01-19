package com.skosc.incontrol.sample

import com.skosc.incontrol.InControl
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.routing.get
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Routing)
        install(InControl)
        routing {
            get(object : Controller {
                fun handle() = "Hello"
            })
        }
    }.start()
}