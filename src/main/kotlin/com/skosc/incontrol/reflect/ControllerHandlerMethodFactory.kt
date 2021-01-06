package com.skosc.incontrol.reflect

import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters

internal class ControllerHandlerMethodFactory(
    private val controllerHandlerParameterFactory: ControllerHandlerParameterFactory
) {

    fun from(kFunction: KFunction<*>): ControllerHandlerMethod {
        return ControllerHandlerMethod(
            kFunction = kFunction,
            parameters = kFunction.valueParameters.map(controllerHandlerParameterFactory::from)
        )
    }
}