package com.skosc.incontrol.routing

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.controller.DelegatedController
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

/**
 * DSL methods for integrating with default Routing module
 *
 * @author a.yakovlev
 * @since indev
 */

@ContextDsl
fun Route.get(controller: Controller): Route = handle(controller, HttpMethod.Get)

@ContextDsl
fun Route.post(controller: Controller): Route = handle(controller, HttpMethod.Post)

@ContextDsl
fun Route.delete(controller: Controller): Route = handle(controller, HttpMethod.Delete)

@ContextDsl
fun Route.head(controller: Controller): Route = handle(controller, HttpMethod.Head)

@ContextDsl
fun Route.put(controller: Controller): Route = handle(controller, HttpMethod.Put)

@ContextDsl
fun Route.options(controller: Controller): Route = handle(controller, HttpMethod.Options)

@ContextDsl
fun Route.patch(controller: Controller): Route = handle(controller, HttpMethod.Patch)

@ContextDsl
private fun Route.handle(controller: Controller, method: HttpMethod): Route {
    val delegate = DelegatedController(controller)
    return route(controller.route, method) {
        handle { delegate.handle(call) }
    }
}
