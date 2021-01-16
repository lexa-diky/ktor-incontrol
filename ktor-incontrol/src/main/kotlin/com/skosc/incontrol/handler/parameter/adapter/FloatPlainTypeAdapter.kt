package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class FloatPlainTypeAdapter : PlainTypeAdapter<Float> {

    override val type: KType = Float::class.createType()

    override fun convert(value: String): Float = value.toFloat()
}