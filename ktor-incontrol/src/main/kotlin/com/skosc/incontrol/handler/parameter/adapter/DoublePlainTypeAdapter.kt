package com.skosc.incontrol.handler.parameter.adapter

import kotlin.reflect.KType
import kotlin.reflect.full.createType

internal class DoublePlainTypeAdapter : PlainTypeAdapter<Double> {

    override val type: KType = Double::class.createType()

    override fun convert(value: String): Double = value.toDouble()
}