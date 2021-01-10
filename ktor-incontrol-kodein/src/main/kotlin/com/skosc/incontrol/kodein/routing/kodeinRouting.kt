package com.skosc.incontrol.kodein

import com.skosc.incontrol.controller.Controller
import com.skosc.incontrol.routing.handle
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.direct
import org.kodein.di.instanceOrNull
import org.kodein.di.jxinject.jx
import org.kodein.di.ktor.di
import org.kodein.type.TypeToken
import org.kodein.type.typeToken
import kotlin.reflect.KClass
import kotlin.reflect.full.createType
import kotlin.reflect.javaType

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
    return handle(T::class, method)
}

@Suppress("UNCHECKED_CAST")
@ContextDsl
@OptIn(ExperimentalStdlibApi::class)
fun <T: Controller> Route.handle(type: KClass<T>, method: HttpMethod): Route {
    val typeToken = typeToken(type.createType().javaType) as TypeToken<Any>
    val controller = di().direct.InstanceOrNull(typeToken)
        ?: di().jx.newInstance(type.java)
    return handle(controller as Controller, method)
}