package com.skosc.incontrol.reflect

import kotlin.reflect.full.createType

object StaticTypes {
    val STRING = String::class.createType(nullable = true)
    val INT = Int::class.createType(nullable = true)
    val DOUBLE = Double::class.createType(nullable = true)
    val BOOLEAN = Boolean::class.createType(nullable = true)
}