package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.handler.ParameterType
import kotlin.reflect.KParameter
import kotlin.reflect.full.hasAnnotation

/**
 * Helper class that resolves [ParameterType] from [KParameter]
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ParameterTypeResolver(private val parameter: KParameter) {

    fun resolve(): ParameterType = when {
        checkIsBody() -> ParameterType.BODY
        checkIsPath() -> ParameterType.PATH
        checkIsQuery() -> ParameterType.QUERY
        else -> error("Can't resolve parameter type of $parameter")
    }

    private fun checkIsBody() : Boolean {
        return parameter.name == NAME_BODY || parameter.hasAnnotation<Body>()
    }

    private fun checkIsPath(): Boolean {
        return parameter.hasAnnotation<Path>()
    }

    private fun checkIsQuery(): Boolean {
        return parameter.hasAnnotation<Query>()
    }

    companion object {

        private const val NAME_BODY = "body"
    }
}