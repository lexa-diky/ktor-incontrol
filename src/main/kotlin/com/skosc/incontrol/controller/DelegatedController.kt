package com.skosc.incontrol.controller

import com.skosc.incontrol.InControl
import com.skosc.incontrol.di.DIContainerWrapperAggregate
import com.skosc.incontrol.di.DefaultAnonymousDITypeContainer
import com.skosc.incontrol.reflect.ControllerHandlerMethod
import com.skosc.incontrol.reflect.ControllerHandlerMethodFactory
import com.skosc.incontrol.reflect.ControllerHandlerParameterFactory
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

    private val controllerHandlerMethodFactory: ControllerHandlerMethodFactory =
        ControllerHandlerMethodFactory(ControllerHandlerParameterFactory())
    private val handlerMethodFinder: HandlerMethodFinder = HandlerMethodFinder()
    private val parameterRetriever: ControllerParameterRetriever = ControllerParameterRetriever()
    private val delegatedHandler: ControllerHandlerMethod =
        controllerHandlerMethodFactory.from(handlerMethodFinder.findHandlerMethod(controller))

    suspend fun handle(call: ApplicationCall) {
        val diContainer = DIContainerWrapperAggregate(listOf(
                DefaultAnonymousDITypeContainer.fromCall(call),
                call.application.feature(InControl).diContainer
        ))
        val parameters = parameterRetriever.retrieveParameters(
            expectedParameters = delegatedHandler.parameters,
            diContainerWrapper = diContainer,
            call = call
        )
        val callResult = delegatedHandler.call(controller, parameters)
        callResult?.let { call.respond(it) }
    }
}