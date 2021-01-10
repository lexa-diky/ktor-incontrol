package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.annotation.isResolveDefault
import com.skosc.incontrol.handler.ParameterType
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation

/**
 * Helper class that resolves name of the parameter
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ParameterNameResolver {

    fun resolve(parameter: KParameter, type: ParameterType): String {
        return when (type) {
            ParameterType.BODY -> requireNotNull(parameter.name)
            ParameterType.PATH -> resolveForPath(parameter)
            ParameterType.QUERY -> resolveForQuery(parameter)
            ParameterType.DEPENDENCY -> resolveForDependency(parameter)
        }
    }

    private fun resolveForPath(parameter: KParameter): String {
        val annotation = parameter.findAnnotation<Path>()
        if (annotation?.isResolveDefault != false) {
            return requireNotNull(parameter.name)
        }
        return annotation.name
    }

    private fun resolveForQuery(parameter: KParameter): String {
        val annotation = parameter.findAnnotation<Query>()
        if (annotation?.isResolveDefault != false) {
            return requireNotNull(parameter.name)
        }
        return annotation.name
    }

    private fun resolveForDependency(parameter: KParameter): String {
        val annotation = parameter.findAnnotation<Dependency>()
        if (annotation?.isResolveDefault != false) {
            return requireNotNull(parameter.name)
        }
        return annotation.tag
    }

    companion object {

        val DEFAULT: ParameterNameResolver = ParameterNameResolver()
    }
}