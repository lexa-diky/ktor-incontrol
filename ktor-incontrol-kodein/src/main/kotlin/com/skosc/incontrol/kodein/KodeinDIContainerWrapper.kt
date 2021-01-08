package com.skosc.incontrol.kodein

import com.skosc.incontrol.di.DIContainerWrapper
import org.kodein.di.DI
import org.kodein.di.jxinject.jx
import kotlin.reflect.KType
import kotlin.reflect.javaType

class   KodeinDIContainerWrapper(private val di: DI) : DIContainerWrapper {

    @ExperimentalStdlibApi
    override fun resolve(tag: String?, type: KType): Any {
        assert(tag == null) { "Tagged dependencies not yet supported" }
        val cls = Class.forName(type.javaType.typeName)
        return di.jx.newInstance(cls)
    }
}