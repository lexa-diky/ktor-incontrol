package com.skosc.incontrol.kodein

import com.skosc.incontrol.InControl
import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.routing.handle
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.future.future
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.instanceOrNull
import org.kodein.di.jxinject.jx
import org.kodein.di.ktor.DIFeature
import org.kodein.di.ktor.di
import kotlin.reflect.KClass
import kotlin.reflect.full.createType

/**
 * Extension for standard control routing to support integration with kodein
 *
 * @author a.yakovlev
 * @since indev
 */

@ContextDsl
inline fun <reified T: Controller> Route.get(): Route = handle<T>(HttpMethod.Get)

@ContextDsl
inline fun <reified T: Controller> Route.post(): Route = handle<T>(HttpMethod.Post)

@ContextDsl
inline fun <reified T: Controller> Route.delete(): Route = handle<T>(HttpMethod.Delete)

@ContextDsl
inline fun <reified T: Controller> Route.head(): Route = handle<T>(HttpMethod.Head)

@ContextDsl
inline fun <reified T: Controller> Route.put(): Route = handle<T>(HttpMethod.Put)

@ContextDsl
inline fun <reified T: Controller> Route.options(): Route = handle<T>(HttpMethod.Options)

@ContextDsl
inline fun <reified T: Controller> Route.patch(): Route = handle<T>(HttpMethod.Patch)

@ContextDsl
inline fun <reified T: Controller> Route.handle(method: HttpMethod): Route {
    val controller = di().direct.instanceOrNull<T>() ?: di().jx.newInstance(T::class.java)
    return handle(controller, method)
}
