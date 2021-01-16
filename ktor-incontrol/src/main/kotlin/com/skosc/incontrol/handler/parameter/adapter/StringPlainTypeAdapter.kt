package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class StringPlainTypeAdapter : PlainTypeAdapter<String> {

    override val type: KType = String::class.createType()

    override fun convert(value: String): String = value
}