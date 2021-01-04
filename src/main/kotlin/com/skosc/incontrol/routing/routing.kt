package com.skosc.incontrol.routing

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.controller.DelegatedController
import io.ktor.application.*
import io.ktor.routing.*

/**
 * DSL methods for integrating with default Routing module
 *
 * @author a.yakovlev
 * @since indev
 */

fun Route.get(controller: Controller) = get(controller.route) {
    DelegatedController(controller).handle(call)
}

fun Route.post(controller: Controller) = post(controller.route) {
    DelegatedController(controller).handle(call)
}
fun Route.delete(controller: Controller) = delete(controller.route) {
    DelegatedController(controller).handle(call)
}

fun Route.head(controller: Controller) = head(controller.route) {
    DelegatedController(controller).handle(call)
}

fun Route.put(controller: Controller) = put(controller.route) {
    DelegatedController(controller).handle(call)
}

fun Route.options(controller: Controller) = options(controller.route) {
    DelegatedController(controller).handle(call)
}

fun Route.patch(controller: Controller) = patch(controller.route) {
    DelegatedController(controller).handle(call)
}