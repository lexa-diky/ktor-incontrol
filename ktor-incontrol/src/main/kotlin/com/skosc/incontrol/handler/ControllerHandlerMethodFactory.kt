package com.skosc.incontrol.handler

import com.skosc.incontrol.handler.parameter.ControllerHandlerParameterFactory
import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters

/**
 * Factory producing [ControllerHandlerMethod] from [KFunction]
 *
 * @param controllerHandlerParameterFactory - factory of parameters
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ControllerHandlerMethodFactory(
    private val controllerHandlerParameterFactory: ControllerHandlerParameterFactory,
) {

    /**
     * Creates [ControllerHandlerMethod] instance
     */
    fun from(kFunction: KFunction<*>): ControllerHandlerMethod {
        return ControllerHandlerMethod(
            kFunction = kFunction,
            parameters = kFunction.valueParameters.map(controllerHandlerParameterFactory::from)
        )
    }
}