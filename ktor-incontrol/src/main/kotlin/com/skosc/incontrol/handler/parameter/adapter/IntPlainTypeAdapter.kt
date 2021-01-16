package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class IntPlainTypeAdapter : PlainTypeAdapter<Int> {

    override val type: KType = Int::class.createType()

    override fun convert(value: String): Int = value.toInt()
}