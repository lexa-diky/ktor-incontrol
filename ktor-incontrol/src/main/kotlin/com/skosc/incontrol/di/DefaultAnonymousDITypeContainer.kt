package com.skosc.incontrol.di

import io.ktor.application.*
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSupertypeOf

/**
 * Simple implementation of di container matching types to instance.
 */
internal class DefaultAnonymousDITypeContainer(
    private val dependencies: List<Pair<KType, Any>>,
) : DIContainerWrapper {

    override fun resolve(tag: String?, type: KType): Any? =
        dependencies.firstOrNull { (filterType, _) -> filterType.isSupertypeOf(type) }?.second

    companion object {

        private val applicationCallType = ApplicationCall::class.createType()

        fun fromCall(call: ApplicationCall): DefaultAnonymousDITypeContainer =
            DefaultAnonymousDITypeContainer(
                listOf(
                    applicationCallType to call
                )
            )
    }
}