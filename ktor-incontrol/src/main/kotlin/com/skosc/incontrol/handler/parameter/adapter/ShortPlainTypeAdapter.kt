package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class ShortPlainTypeAdapter : PlainTypeAdapter<Short> {

    override val type: KType = Short::class.createType()

    override fun convert(value: String): Short = value.toShort()
}