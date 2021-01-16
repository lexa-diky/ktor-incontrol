package com.skosc.incontrol.handler.parameter.adapter

import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import kotlin.reflect.KType

internal class TypeAdapterRegistry {

    private val cache: MutableMap<KType, PlainTypeAdapter<Any>> = HashMap()

    @Suppress("UNCHECKED_CAST")
    fun <T> register(adapter: PlainTypeAdapter<T>) {
        cache[adapter.type] = adapter as PlainTypeAdapter<Any>
    }

    fun resolve(type: KType): PlainTypeAdapter<Any>? = cache[type]

    fun resolveTryResolve(type: KType): PlainTypeAdapter<Any> = resolve(type) ?: inControlError(
        code = InControlErrorCode.PARAMETER_UNSUPPORTED_TYPE,
        reason = "Can't find type adapter for type: $type",
        howToSolve = "Register corresponding type adapter"
    )

    fun contains(type: KType): Boolean {
        return resolve(type) != null
    }
}