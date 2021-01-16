package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class BooleanPlainTypeAdapter : PlainTypeAdapter<Boolean> {

    override val type: KType = Boolean::class.createType()

    override fun convert(value: String): Boolean = value.toBoolean()
}