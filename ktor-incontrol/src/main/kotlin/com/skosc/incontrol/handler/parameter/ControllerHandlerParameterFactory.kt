package com.skosc.incontrol.handler.parameter

import com.skosc.incontrol.handler.parameter.adapter.TypeAdapterRegistry
import kotlin.reflect.KParameter

internal class ControllerHandlerParameterFactory(
    private val typeAdapterRegistry: TypeAdapterRegistry
) {
    private val parameterTypeResolver = ParameterTypeResolver(typeAdapterRegistry)

    fun from(kParameter: KParameter): ControllerHandlerParameter {
        val type = parameterTypeResolver.resolve(kParameter)
        return ControllerHandlerParameter(
            kParameter = kParameter,
            type = type,
            name = ParameterNameResolver.DEFAULT.resolve(kParameter, type)
        )
    }
}