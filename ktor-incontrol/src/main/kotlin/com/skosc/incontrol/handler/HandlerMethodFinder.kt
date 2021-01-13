package com.skosc.incontrol.handler

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
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
            0 -> throwHandlerNotFoundError(instance)
            1 -> matchingMethods.first()
            else -> throwTooManyHandlersError(instance, matchingMethods)
        }
    }

    private fun throwHandlerNotFoundError(instance: Controller): Nothing {
        inControlError(
            code = InControlErrorCode.HANDLER_NOT_FOUND,
            reason = "Can't find handler method in controller: $instance",
            howToSolve = "Add handler method to controller"
        )
    }

    private fun throwTooManyHandlersError(
        instance: Controller,
        matchingMethods: List<KFunction<*>>,
    ): Nothing {
        inControlError(
            code = InControlErrorCode.HANDLER_TOO_MANY_MATCHING,
            reason = "To many matching methods in controller: $instance, methods: $matchingMethods",
            howToSolve = "Remove excess handler methods"
        )
    }

    private val KFunction<*>.isPublic get() = visibility == KVisibility.PUBLIC
}