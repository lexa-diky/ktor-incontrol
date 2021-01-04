package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.handler.ParameterType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberFunctions

internal class ControllerHandlerParameterTest {

    @Test
    fun `resolves all fields`() {
        val method = SampleController::class.declaredMemberFunctions.first { it.name == "sample" }
        val parameter = method.parameters.first { it.name == "body" }
        val handlerParameter = ControllerHandlerParameter(parameter)
        assertEquals("body", handlerParameter.name)
        assertEquals(parameter.type, handlerParameter.kType)
        assertEquals(parameter, handlerParameter.kParameter)
        assertEquals(ParameterType.BODY, handlerParameter.type)

    }

    internal class SampleController {

        fun sample(@Body body: String) = 2
    }
}
