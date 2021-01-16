package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.InControlException
import com.skosc.incontrol.handler.ControllerHandlerMethodFactory
import com.skosc.incontrol.handler.parameter.ControllerHandlerParameterFactory
import com.skosc.incontrol.handler.parameter.adapter.StringPlainTypeAdapter
import com.skosc.incontrol.handler.parameter.adapter.TypeAdapterRegistry
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private suspend fun freeHandlerMethod(@Body body: String) = 2

internal class ControllerHandlerMethodFactoryTest {

    val typeAdapterRegistry = TypeAdapterRegistry().apply {
        register(StringPlainTypeAdapter())
    }

    val controllerHandlerMethodFactory = ControllerHandlerMethodFactory(
        ControllerHandlerParameterFactory(typeAdapterRegistry)
    )

    @Test
    fun `resolves all parameters`() {
        val handler = controllerHandlerMethodFactory.from(SampleController::sample)
        assertEquals(2, handler.parameters.size)
        assertEquals(SampleController::sample, handler.kFunction)
    }

    @Test
    fun `can't use free method`() {
        val exception = assertThrows<InControlException> {
            controllerHandlerMethodFactory.from(::freeHandlerMethod)
        }
        assertEquals(InControlErrorCode.HANDLER_NOT_IN_CLASS_INSTANCE, exception.errorCode)
    }

    @Test
    fun `can invoke handler`(): Unit = runBlocking {
        val handler = controllerHandlerMethodFactory.from(SampleController::sample)
        val (body, user) = handler.parameters
        val callResult = handler.call(SampleController(), mapOf(
            body to "body",
            user to "user"
        ))

        assertEquals(callResult, "body user")
    }

    internal class SampleController : Controller {

        suspend fun sample(@Body body: String, @Path user: String) = "$body $user"
    }
}

