package com.skosc.incontrol.reflect

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.InControlException
import com.skosc.incontrol.handler.HandlerMethodFinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HandlerMethodFinderTest {

    private val finder = HandlerMethodFinder()

    @Test
    fun `passing ok controller`() {

        val handlerMethod = finder.findHandlerMethod(OkController())
        assertEquals(OkController::handle, handlerMethod)
    }

    @Test
    fun `failing controller with no handlers`() {
        val exception = assertThrows<InControlException> {
            finder.findHandlerMethod(FailControllerWithNoHandlers())
        }

        assertEquals(InControlErrorCode.HANDLER_NOT_FOUND, exception.errorCode)
    }

    @Test
    fun `failing controller with more then one handler`() {
        val exception = assertThrows<InControlException> {
            finder.findHandlerMethod(FailControllerWithTooManyHandlers())
        }

        assertEquals(InControlErrorCode.HANDLER_TOO_MANY_MATCHING, exception.errorCode)
    }

    internal class OkController : Controller {

        override val route: String = "/"

        suspend fun handle() = helper()

        private fun helper() = 2
    }

    internal class FailControllerWithNoHandlers : Controller {

        override val route: String = "/"

        private suspend fun handler() = 2
    }

    internal class FailControllerWithTooManyHandlers : Controller {

        override val route: String = "/"

        suspend fun handle2() = 2

        suspend fun handle3() = 3
    }

    internal class FailControllerWithPublicMethodWithoutSuspend : Controller {

        override val route: String = "/"

        fun handle() = 2
    }
}
