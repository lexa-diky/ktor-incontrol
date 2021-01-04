package com.skosc.incontrol.reflect

import com.skosc.incontrol.controller.Controller
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

internal class HandlerMethodFinderTest {

    private val finder = HandlerMethodFinder()

    @Test
    fun `passing ok controller`() {

        val handlerMethod = finder.findHandlerMethod(OkController())
        assertEquals(OkController::handle, handlerMethod)
    }

    @Test
    fun `failing controller with no handlers`() {
        assertThrows<IllegalArgumentException> {
            finder.findHandlerMethod(FailControllerWithNoHandlers())
        }
    }

    @Test
    fun `failing controller with more then one handler`() {
        assertThrows<IllegalArgumentException> {
            finder.findHandlerMethod(FailControllerWithTooManyHandlers())
        }
    }

    @Test
    fun `handler method should be marked as suspend`() {
        assertThrows<IllegalArgumentException> {
            finder.findHandlerMethod(FailControllerWithPublicMethodWithoutSuspend())
        }
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

    internal class FailControllerWithTooManyHandlers: Controller {

        override val route: String = "/"

        suspend fun handle2() = 2

        suspend fun handle3() = 3
    }

    internal class FailControllerWithPublicMethodWithoutSuspend: Controller {

        override val route: String = "/"

        fun handle() = 2
    }
}