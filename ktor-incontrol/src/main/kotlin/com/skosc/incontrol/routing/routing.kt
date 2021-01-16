package com.skosc.incontrol.routing

import com.skosc.incontrol.InControl
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

/**
 * Routes request with [controller] as [HttpMethod.Get]
 */
@ContextDsl
fun Route.get(controller: Controller): Route = handle(controller, HttpMethod.Get)

/**
 * Routes request with [controller] as [HttpMethod.Post]
 */
@ContextDsl
fun Route.post(controller: Controller): Route = handle(controller, HttpMethod.Post)

/**
 * Routes request with [controller] as [HttpMethod.Delete]
 */
@ContextDsl
fun Route.delete(controller: Controller): Route = handle(controller, HttpMethod.Delete)

/**
 * Routes request with [controller] as [HttpMethod.Head]
 */
@ContextDsl
fun Route.head(controller: Controller): Route = handle(controller, HttpMethod.Head)

/**
 * Routes request with [controller] as [HttpMethod.Put]
 */
@ContextDsl
fun Route.put(controller: Controller): Route = handle(controller, HttpMethod.Put)

/**
 * Routes request with [controller] as [HttpMethod.Options]
 */
@ContextDsl
fun Route.options(controller: Controller): Route = handle(controller, HttpMethod.Options)

/**
 * Routes request with [controller] as [HttpMethod.Patch]
 */
@ContextDsl
fun Route.patch(controller: Controller): Route = handle(controller, HttpMethod.Patch)

/**
 * Universal method for routing request to [controller] as [method]
 */
@ContextDsl
fun Route.handle(controller: Controller, method: HttpMethod): Route {
    val feature = application.feature(InControl)
    val delegate = DelegatedController(feature, controller)
    return route(controller.route, method) {
        handle { delegate.handle(call) }
    }
}
