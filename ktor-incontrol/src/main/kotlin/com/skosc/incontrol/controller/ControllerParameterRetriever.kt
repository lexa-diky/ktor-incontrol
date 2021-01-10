package com.skosc.incontrol.controller

import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.di.DIContainerWrapper
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.handler.ParameterType
import com.skosc.incontrol.reflect.ControllerHandlerParameter
import com.skosc.incontrol.reflect.StaticTypes
import io.ktor.application.*
import io.ktor.request.*
import java.lang.Exception
import kotlin.reflect.full.isSubtypeOf

/**
 * Retrieves parameters for controller from [ApplicationCall]
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ControllerParameterRetriever {

    private val optionalParameterValueMarker = object {}

    /**
     * Retrieves values for all passed [expectedParameters].
     * If parameter is optional it might be omitted.
     * If parameter is nullable if might be automatically set to null.
     */
    suspend fun retrieveParameters(
        expectedParameters: List<ControllerHandlerParameter>,
        diContainerWrapper: DIContainerWrapper,
        call: ApplicationCall,
    ): Map<ControllerHandlerParameter, Any?> {
        return expectedParameters.associateWith { parameter ->
            when (parameter.type) {
                ParameterType.BODY ->
                    call.receive(parameter.kType)
                ParameterType.QUERY ->
                    normalizeQueryOrPath(parameter, call.request.queryParameters[parameter.name])
                ParameterType.PATH ->
                    normalizeQueryOrPath(parameter, extractPathParameter(call, parameter))
                ParameterType.DEPENDENCY ->
                    tryResolveDependencyParameter(diContainerWrapper, parameter)
                else -> throwCantFindParameter(parameter)
            }
        }.filter { (_, v) -> v != optionalParameterValueMarker }
    }

    private fun extractPathParameter(call: ApplicationCall, parameter: ControllerHandlerParameter): String? {
        val parameters = call.parameters.getAll(parameter.name) ?: return null
        return if (parameters.size == 1) {
            parameters.first()
        } else {
            val callImposter = call.request.queryParameters[parameter.name]
            parameters.firstOrNull { it != callImposter }
        }
    }

    private fun tryResolveDependencyParameter(
        diContainerWrapper: DIContainerWrapper,
        parameter: ControllerHandlerParameter,
    ): Any {
        // If parameter name in code matches resolved parameter name, then tag is not set
        val tag = if (parameter.kParameter.name == parameter.name) null else parameter.name
        return diContainerWrapper.resolve(tag, parameter.kType)
            ?: throwCantFindDependency(parameter, diContainerWrapper)
    }

    private fun normalizeQueryOrPath(parameter: ControllerHandlerParameter, value: String?): Any? {
        return when {
            value != null -> castQueryOrPathToNearestType(parameter, value)
            parameter.isOptional -> optionalParameterValueMarker
            parameter.isNullable -> null
            else -> throwCantFindParameter(parameter)
        }
    }

    private fun castQueryOrPathToNearestType(parameter: ControllerHandlerParameter, value: String): Any {
        try {
            return when {
                parameter.kType.isSubtypeOf(StaticTypes.STRING) -> value
                parameter.kType.isSubtypeOf(StaticTypes.INT) -> value.toInt()
                parameter.kType.isSubtypeOf(StaticTypes.DOUBLE) -> value.toDouble()
                parameter.kType.isSubtypeOf(StaticTypes.BOOLEAN) -> value.toBoolean()
                else -> inControlError(
                    code = InControlErrorCode.OTHER_INTEGRITY,
                    reason = "Integrity error",
                    howToSolve = "Please make Github issue"
                )
            }
        } catch (e: Exception) {
            inControlError(
                cause = e,
                code = InControlErrorCode.HANDLER_PARAMETER_CAST,
                reason = "Can't cast handler parameter $parameter",
                howToSolve = "Please check type annotations"
            )
        }
    }

    private fun throwCantFindParameter(parameter: ControllerHandlerParameter): Nothing = inControlError(
        code = InControlErrorCode.PARAMETER_CANT_FIND_PARAMETER,
        reason = "Can't find required parameter: $parameter",
        howToSolve = "Either pass parameter in request or mark it as nullable in handler"
    )

    private fun throwCantFindDependency(parameter: ControllerHandlerParameter, container: DIContainerWrapper): Nothing =
        inControlError(
            code = InControlErrorCode.PARAMETER_CANT_FIND_PARAMETER,
            reason = "Can't find dependency: $parameter in container: $container",
            howToSolve = "Declare required dependency in your container"
        )
}