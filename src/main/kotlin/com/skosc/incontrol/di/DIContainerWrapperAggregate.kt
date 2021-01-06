package com.skosc.incontrol.di

import kotlin.reflect.KType

internal class DIContainerWrapperAggregate(private val subContainers: List<DIContainerWrapper>) : DIContainerWrapper {

    override fun resolve(tag: String?, type: KType): Any? =
        subContainers.asSequence()
            .map { it.resolve(tag, type) }
            .firstOrNull { it != null }
}