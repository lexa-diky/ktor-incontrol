package com.skosc.incontrol.di

import kotlin.reflect.KType

interface DIContainerWrapper {

    fun resolve(tag: String?, type: KType): Any?

    companion object {

        internal fun empty(): DIContainerWrapper = object : DIContainerWrapper {
            override fun resolve(tag: String?, type: KType): Any? = null
        }
    }
}