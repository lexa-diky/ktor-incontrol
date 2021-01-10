package com.skosc.incontrol.kodein.autowire

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.kodein.handle
import io.ktor.http.*
import io.ktor.routing.*
import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Suppress("UNCHECKED_CAST")
fun Route.autoRoute(pkg: String, group: String = AutoRouting.DEFAULT_GROUP) {
    val reflections = Reflections(pkg)
    val types: List<KClass<*>> = reflections.getTypesAnnotatedWith(AutoRouting::class.java)
        .map { it.kotlin }

    if (types.any { !it.isSubclassOf(Controller::class) }) {
        inControlError(
            code = InControlErrorCode.CONTROLLER_NOT_IMPLEMENTING,
            reason = "AutoRouted class not implementing Controller interface",
            howToSolve = "Add Controller interface to:\n" +
                    types.filter { !it.isSubclassOf(Controller::class) }
                        .joinToString(separator = "\n")

        )
    } else {
        (types as List<KClass<Controller>>).forEach { controller ->
            handle(controller, HttpMethod.Get)
        }
    }
}