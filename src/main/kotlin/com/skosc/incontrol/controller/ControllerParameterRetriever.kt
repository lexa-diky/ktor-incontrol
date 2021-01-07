package com.skosc.incontrol.controller

import com.skosc.incontrol.di.DIContainerWrapper
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.handler.ParameterType
import com.skosc.incontrol.reflect.ControllerHandlerParameter
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.util.*

/**
 * Retrieves parameters for controller from [ApplicationCall]
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ControllerParameterRetriever {

    private val optionalParameterValueMarker = object {}

    suspend fun retrieveParameters(
        expectedParameters: List<ControllerHandlerParameter>,
        diContainerWrapper: DIContainerWrapper,
        call: ApplicationCall
    ): Map<ControllerHandlerParameter, Any?> {
        return expectedParameters.associateWith { parameter ->
            when {
                parameter.type == ParameterType.BODY ->
                    call.receive(parameter.kType)
                parameter.type == ParameterType.QUERY ->
                    normalize(parameter, call.request.queryParameters[parameter.name])
                parameter.type == ParameterType.PATH ->
                    normalize(parameter, call.parameters[parameter.name])
                parameter.type == ParameterType.DEPENDENCY -> diContainerWrapper.resolve(parameter.name, parameter.kType)
                    ?: throwCantFindDependency(parameter, diContainerWrapper)
                else -> throwCantFindParameter(parameter)
            }
        }.filter { (_, v) -> v != optionalParameterValueMarker }
    }

    private fun normalize(parameter: ControllerHandlerParameter, value: Any?): Any? {
        return when {
            value != null -> value
            parameter.isOptional -> optionalParameterValueMarker
            parameter.isNullable -> null
            else -> throwCantFindParameter(parameter)
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