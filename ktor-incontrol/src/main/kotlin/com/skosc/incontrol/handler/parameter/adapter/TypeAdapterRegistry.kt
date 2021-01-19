package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType

internal interface TypeAdapterRegistry {

    fun resolve(type: KType): PlainTypeAdapter<Any>?

    fun contains(type: KType): Boolean
}

internal interface MutableTypeAdapterRegistry : TypeAdapterRegistry {

    fun <T> register(adapter: PlainTypeAdapter<T>)
}

