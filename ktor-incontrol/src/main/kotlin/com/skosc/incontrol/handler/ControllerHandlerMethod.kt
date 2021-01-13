package com.skosc.incontrol.handler

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.handler.parameter.ControllerHandlerParameter
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.jvm.isAccessible

/**
 * Class wrapping [KFunction] describing [Controller]s handler method.
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ControllerHandlerMethod(
    val kFunction: KFunction<*>,
    val parameters: List<ControllerHandlerParameter>,
) {
    private val instanceParameter: KParameter = kFunction.instanceParameter ?: throwMemberClassException()

    /**
     * Invokes handler function of [controller] with [parameters]
     */
    suspend fun call(controller: Controller, parameters: Map<ControllerHandlerParameter, Any?>): Any? {
        return kFunction
            .also { it.isAccessible = true }
            .callSuspendBy(parameters.mapKeys { it.key.kParameter } + (instanceParameter to controller))
    }

    private fun throwMemberClassException(): Nothing = inControlError(
        code = InControlErrorCode.HANDLER_NOT_IN_CLASS_INSTANCE,
        reason = "ControllerHandler is required to be member of class",
        howToSolve = "Move handler method into class implementing Controller interface or use vanilla router handler instead"
    )
}