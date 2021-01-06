package com.skosc.incontrol.reflect

import com.skosc.incontrol.controller.Controller
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
    val instanceParameter: KParameter = kFunction.instanceParameter
        ?: throw IllegalArgumentException("ControllerHandler is required to be member of class")

    suspend fun call(instance: Controller, parameters: Map<ControllerHandlerParameter, Any>): Any? {
        return kFunction
            .also { it.isAccessible = true }
            .callSuspendBy(parameters.mapKeys { it.key.kParameter } + (instanceParameter to instance))
    }
}