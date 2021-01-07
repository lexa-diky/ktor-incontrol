package com.skosc.incontrol.reflect

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import java.lang.IllegalArgumentException
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.valueParameters
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
    val instanceParameter: KParameter = kFunction.instanceParameter ?: throwMemberClassException()

    suspend fun call(instance: Controller, parameters: Map<ControllerHandlerParameter, Any?>): Any? {
        return kFunction
            .also { it.isAccessible = true }
            .callSuspendBy(parameters.mapKeys { it.key.kParameter } + (instanceParameter to instance))
    }

    private fun throwMemberClassException(): Nothing = inControlError(
        code = InControlErrorCode.HANDLER_NOT_IN_CLASS_INSTANCE,
        reason = "ControllerHandler is required to be member of class",
        howToSolve = "Move handler method into class implementing Controller interface or use vanilla router handler instead"
    )
}