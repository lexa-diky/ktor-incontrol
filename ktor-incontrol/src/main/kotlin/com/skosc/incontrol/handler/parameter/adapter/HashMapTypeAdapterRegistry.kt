package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType

internal class HashMapTypeAdapterRegistry : MutableTypeAdapterRegistry {

    private val cache: MutableMap<KType, PlainTypeAdapter<Any>> = HashMap()

    @Suppress("UNCHECKED_CAST")
    override fun <T> register(adapter: PlainTypeAdapter<T>) {
        cache[adapter.type] = adapter as PlainTypeAdapter<Any>
    }

    override fun resolve(type: KType): PlainTypeAdapter<Any>? = cache[type]

    override fun contains(type: KType): Boolean = resolve(type) != null
}