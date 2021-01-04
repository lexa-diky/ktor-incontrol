package com.skosc.incontrol.handler.validator

import com.skosc.incontrol.handler.ParameterType
import com.skosc.incontrol.reflect.ControllerHandlerParameter
import java.lang.IllegalArgumentException
import kotlin.reflect.KType
import kotlin.reflect.full.createType

/**
 * Class containing validation logic for [ControllerHandlerParameter]
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ParameterValidator {

    /**
     * Valid types for [ParameterType.QUERY] and [ParameterType.PATH]
     */
    private val validPathAndQueryParameterTypes: Set<KType> = setOf(
        String::class.createType(),
        Int::class.createType(),
        Double::class.createType(),
        Boolean::class.createType()
    )

    /**
     * Validates [parameter], throws [IllegalArgumentException] if it is invalid
     */
    fun validateOrThrow(parameter: ControllerHandlerParameter) = when (parameter.type) {
        ParameterType.BODY -> validateBody(parameter)
        ParameterType.QUERY,
        ParameterType.PATH -> validatePathOrQuery(parameter)
    }

    private fun validateBody(parameter: ControllerHandlerParameter) = when {
        parameter.kType.isMarkedNullable ->
            throw IllegalArgumentException("Optional parameters not supported")
        else -> Unit
    }

    private fun validatePathOrQuery(parameter: ControllerHandlerParameter) = when {
        parameter.kType.isMarkedNullable ->
            throw IllegalArgumentException("Optional parameters not supported")
        parameter.kType !in validPathAndQueryParameterTypes ->
            throw IllegalArgumentException("Unsupported parameter type: ${parameter.kType}")
        else -> Unit
    }
}