package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class LongPlainTypeAdapter : PlainTypeAdapter<Long> {

    override val type: KType = Long::class.createType()

    override fun convert(value: String): Long = value.toLong()
}