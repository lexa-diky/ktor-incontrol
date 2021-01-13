package com.skosc.incontrol.handler.parameter

import kotlin.reflect.KParameter

internal class ControllerHandlerParameterFactory {

    fun from(kParameter: KParameter): ControllerHandlerParameter {
        val type = ParameterTypeResolver.DEFAULT.resolve(kParameter)
        return ControllerHandlerParameter(
            kParameter = kParameter,
            type = type,
            name = ParameterNameResolver.DEFAULT.resolve(kParameter, type)
        )
    }
}