package com.skosc.incontrol.handler.parameter

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.handler.parameter.ParameterType
import kotlin.reflect.KParameter
import kotlin.reflect.KType

/**
 * Class wrapping [KParameter] describing [Controller]s handler methods parameter
 *
 * @author a.yakovlev
 * @since indev
 */
internal data class ControllerHandlerParameter(
    val kParameter: KParameter,
    val type: ParameterType,
    val name: String,
) {
    val kType: KType = kParameter.type

    val isOptional: Boolean = kParameter.isOptional

    val isNullable: Boolean = kType.isMarkedNullable

    override fun toString(): String {
        return "$name: $kType"
    }
}