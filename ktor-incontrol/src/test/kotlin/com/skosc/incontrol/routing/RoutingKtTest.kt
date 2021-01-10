package com.skosc.incontrol.routing

import com.skosc.incontrol.InControl
import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.controller.Controller
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RoutingKtTest {

    private fun testController(message: String): Controller = object : Controller {
        suspend fun handle() = message
    }

    private fun withTestRoute(routing: Route.() -> Unit, test: TestApplicationEngine.() -> Unit) {
        withTestApplication({
            install(Routing)
            install(InControl)
            routing(routing)
        }, test)
    }

    @Test
    fun get() {
        withTestRoute({ get(testController("get")) }) {
            val call = handleRequest(HttpMethod.Get, "/")
            assertEquals("get", call.response.content)
        }
    }

    @Test
    fun post() {
        withTestRoute({ post(testController("post")) }) {
            val call = handleRequest(HttpMethod.Post, "/")
            assertEquals("post", call.response.content)
        }
    }

    @Test
    fun delete() {
        withTestRoute({ delete(testController("delete")) }) {
            val call = handleRequest(HttpMethod.Delete, "/")
            assertEquals("delete", call.response.content)
        }
    }

    @Test
    fun head() {
        withTestRoute({ head(testController("head")) }) {
            val call = handleRequest(HttpMethod.Head, "/")
            assertEquals("head", call.response.content)
        }
    }

    @Test
    fun put() {
        withTestRoute({ put(testController("put")) }) {
            val call = handleRequest(HttpMethod.Put, "/")
            assertEquals("put", call.response.content)
        }
    }

    @Test
    fun options() {
        withTestRoute({ options(testController("options")) }) {
            val call = handleRequest(HttpMethod.Options, "/")
            assertEquals("options", call.response.content)
        }
    }

    @Test
    fun patch() {
        withTestRoute({ patch(testController("patch")) }) {
            val call = handleRequest(HttpMethod.Patch, "/")
            assertEquals("patch", call.response.content)
        }
    }

    @Test
    fun `controller returning unit`() {
        withTestRoute({
            get(object : Controller {
                suspend fun handler(@Dependency call: ApplicationCall): Unit {
                    call.respond("UnitValue")
                }
            })
        }) {
            val call = handleRequest(HttpMethod.Get, "/")
            assertEquals("UnitValue", call.response.content)
        }
    }

    @Test
    fun `controller can have path and query parameters with same name`() {
        withTestRoute({
            get(object : Controller {
                override val route: String = "/{user}"

                suspend fun handler(
                    @Path("user") pathUser: String,
                    @Query("user") queryUser: String,
                ): String {
                    return pathUser + queryUser
                }
            })
        }) {
            val call = handleRequest(HttpMethod.Get, "/alex?user=michail")
            assertEquals("alexmichail", call.response.content)
        }
    }
}