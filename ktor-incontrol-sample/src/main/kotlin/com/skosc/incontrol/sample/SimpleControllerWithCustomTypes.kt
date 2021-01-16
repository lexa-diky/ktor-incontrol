package com.skosc.incontrol.sample

import com.skosc.incontrol.InControl
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.handler.parameter.adapter.PlainTypeAdapter
import com.skosc.incontrol.routing.get
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.*
import kotlin.reflect.KType
import kotlin.reflect.full.createType

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(Routing)
        install(InControl) {
            registerTypeAdapters(listOf(
                object : PlainTypeAdapter<UUID> {
                    override val type: KType = UUID::class.createType()

                    override fun convert(value: String): UUID = UUID.fromString(value)
                }
            ))
        }
        routing {
            get(object : Controller {
                override val route: String = "/{uuid}"

                suspend fun handle(uuid: UUID) = "Hello, $uuid, with leastSignificantBits = ${uuid.leastSignificantBits}"
            })
        }
    }.start()
}
