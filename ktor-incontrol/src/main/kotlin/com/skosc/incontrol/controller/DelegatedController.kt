package com.skosc.incontrol.controller

import com.skosc.incontrol.InControl
import com.skosc.incontrol.di.DIContainerWrapperAggregate
import com.skosc.incontrol.di.DefaultAnonymousDITypeContainer
import com.skosc.incontrol.handler.ControllerHandlerMethod
import com.skosc.incontrol.handler.ControllerHandlerMethodFactory
import com.skosc.incontrol.handler.parameter.ControllerHandlerParameterFactory
import com.skosc.incontrol.handler.HandlerMethodFinder
import io.ktor.application.*
import io.ktor.response.*

/**
 * Controller that delegates its functions to user defined [Controller]s.
 * Responsible for finding, caching and calling handler of sub controller.
 *
 * @author a.yakovlev
 * @since indev
 */
internal class DelegatedController(private val inControl: InControl, private val controller: Controller) {


    private val delegatedHandler: ControllerHandlerMethod = inControl.controllerHandlerMethodFactory.from(
        inControl.handlerMethodFinder.findHandlerMethod(controller)
    )

    /**
     * Handles passed [call] with wrapped controller
     */
    suspend fun handle(call: ApplicationCall) {

        val diContainer = buildLocalDiContainer(inControl, call)
        val parameters = inControl.parameterRetriever.retrieveParameters(
            expectedParameters = delegatedHandler.parameters,
            diContainerWrapper = diContainer,
            call = call
        )
        val callResult = delegatedHandler.call(controller, parameters)
        if (callResult != Unit) {
            callResult?.let { call.respond(it) }
        }
    }

    private fun buildLocalDiContainer(feature: InControl, call: ApplicationCall) = DIContainerWrapperAggregate(
        listOf(DefaultAnonymousDITypeContainer.fromCall(call), feature.diContainer)
    )
}