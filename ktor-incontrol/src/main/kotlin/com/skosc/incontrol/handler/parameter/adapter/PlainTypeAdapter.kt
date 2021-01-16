package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType

/**
 * Adapter for converting plain [String] values to class type [T]
 */
interface PlainTypeAdapter<T> {

    val type: KType

    fun convert(value: String): T
}