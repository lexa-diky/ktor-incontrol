package com.skosc.incontrol.kodein

import com.skosc.incontrol.di.DIContainerWrapper
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.jxinject.jx
import org.kodein.type.TypeToken
import org.kodein.type.typeToken
import kotlin.reflect.KType
import kotlin.reflect.javaType

/**
 * @author a.yakovlev
 * @since indev
 *
 * Used to resolve kodein dependencies with [DIContainerWrapper] interface
 */
internal class KodeinDIContainerWrapper(private val di: DI) : DIContainerWrapper {

    @ExperimentalStdlibApi
    override fun resolve(tag: String?, type: KType): Any? =
        tryResolveWithContainer(tag, type) ?: tryResolveWithJSR330(type)

    @Suppress("UNCHECKED_CAST")
    @ExperimentalStdlibApi
    private fun tryResolveWithContainer(tag: String?, type: KType): Any? {
        val typeToken = typeToken(type.javaType) as TypeToken<Any>
        return di.direct.Instance(typeToken, tag)
    }

    @ExperimentalStdlibApi
    private fun tryResolveWithJSR330(type: KType): Any? {
        val cls = Class.forName(type.javaType.typeName)
        return di.jx.newInstance(cls)
    }
}