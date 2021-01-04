package com.skosc.incontrol.handler.validator

import com.skosc.incontrol.reflect.ControllerHandlerMethod
import com.skosc.incontrol.reflect.ControllerHandlerParameter

/**
 * Class composing validation of [ControllerHandlerMethod].
 *
 * @author a.yakovlev
 * @since indev
 */
internal class HandlerValidator {
    private val parameterValidator: ParameterValidator = ParameterValidator()

    /**
     * Validates passed [handler], throws [IllegalArgumentException] if it is invalid
     */
    fun validateOrThrow(handler: ControllerHandlerMethod) {
        handler.parameters.forEach(parameterValidator::validateOrThrow)
    }
}