package com.skosc.incontrol.controller

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

    suspend fun retrieveParameters(
        expectedParameters: List<ControllerHandlerParameter>,
        call: ApplicationCall
    ): Map<ControllerHandlerParameter, Any> {
        return expectedParameters.associateWith { parameter ->
            when(parameter.type) {
                ParameterType.BODY -> call.receive(parameter.kType)
                ParameterType.QUERY -> call.request.queryParameters[parameter.name]
                    ?: error("Can't find required parameter: $parameter")
                ParameterType.PATH -> call.parameters[parameter.name]
                    ?: error("Can't find required parameter: $parameter")
            }
        }
    }
}