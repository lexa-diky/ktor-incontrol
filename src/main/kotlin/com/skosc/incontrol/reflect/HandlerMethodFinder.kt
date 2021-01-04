package com.skosc.incontrol.reflect

import com.skosc.incontrol.controller.Controller
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Class that is used to resolve witch method in passed [Controller] will be used as handler
 *
 * @author a.yakovlev
 * @since indev
 */
internal class HandlerMethodFinder {

    /**
     * Finds method that can act as handler according to following criteria:
     *   1. method is public
     *   2. methods is suspend
     *
     * @throws IllegalStateException if passed [instance] contains more then one matching method
     */
    fun findHandlerMethod(instance: Controller): KFunction<*> {
        val controllerClass = instance::class
        val matchingMethods = controllerClass.declaredMemberFunctions
            .filter { fn -> fn.isPublic && fn.isSuspend }
        return when (matchingMethods.size) {
            0 -> throw IllegalArgumentException("Can't find handler method in controller: $instance")
            1 -> matchingMethods.first()
            else -> throw IllegalArgumentException("To many matching methods in controller: $instance, methods: $matchingMethods")
        }
    }

    private val KFunction<*>.isPublic get() = visibility == KVisibility.PUBLIC
}