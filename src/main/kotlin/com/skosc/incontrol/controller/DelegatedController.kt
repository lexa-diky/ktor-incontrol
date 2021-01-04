package com.skosc.incontrol.controller

import com.skosc.incontrol.handler.validator.HandlerValidator
import com.skosc.incontrol.reflect.ControllerHandlerMethod
import com.skosc.incontrol.reflect.HandlerMethodFinder
import io.ktor.application.*
import io.ktor.response.*

/**
 * Controller that delegates its functions to user defined [Controller]s.
 * Responsible for finding, caching and calling handler of sub controller.
 *
 * @author a.yakovlev
 * @since indev
 */
internal class DelegatedController(private val controller: Controller) {

    private val handlerMethodFinder: HandlerMethodFinder = HandlerMethodFinder()
    private val parameterRetriever: ControllerParameterRetriever = ControllerParameterRetriever()
    private val delegatedHandler: ControllerHandlerMethod = ControllerHandlerMethod(handlerMethodFinder.findHandlerMethod(controller))
    private val handlerValidator: HandlerValidator = HandlerValidator()

    init {
        handlerValidator.validateOrThrow(delegatedHandler)
    }

    suspend fun handle(call: ApplicationCall) {
        val parameters = parameterRetriever.retrieveParameters(delegatedHandler.parameters, call)
        val callResult = delegatedHandler.call(controller, parameters)
        callResult?.let { call.respond(it) }
    }
}