package com.skosc.incontrol.kodein.autowire

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.kodein.handle
import io.ktor.http.*
import io.ktor.routing.*
import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf

@Suppress("UNCHECKED_CAST")
fun Route.autoRoute(pkg: String, group: String = AutoRouting.DEFAULT_GROUP) {
    val reflections = Reflections(pkg)
    // TODO Rewrite more efficiently
    val types = reflections.getTypesAnnotatedWith(AutoRouting::class.java)
        .filter { it.getDeclaredAnnotation(AutoRouting::class.java).group == group }
        .map { it.getDeclaredAnnotation(AutoRouting::class.java).method to it.kotlin }


    if (types.any { (_, type) -> !type.isSubclassOf(Controller::class) }) {
        inControlError(
            code = InControlErrorCode.CONTROLLER_NOT_IMPLEMENTING,
            reason = "AutoRouted class not implementing Controller interface",
            howToSolve = "Add Controller interface to:\n" +
                    types.filter { (_, type) -> !type.isSubclassOf(Controller::class) }
                        .joinToString(separator = "\n")

        )
    } else {
        (types as List<Pair<String, KClass<Controller>>>).forEach { (method, type) ->
            handle(type, HttpMethod(method))
        }
    }
}