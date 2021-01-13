package com.skosc.incontrol.reflect

import kotlin.reflect.KFunction
import kotlin.reflect.full.valueParameters

/**
 * @author a.yakovlev
 * @since indev
 *
 * Factory producing [ControllerHandlerMethod] from [KFunction]
 *
 * @param controllerHandlerParameterFactory - factory of parameters
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