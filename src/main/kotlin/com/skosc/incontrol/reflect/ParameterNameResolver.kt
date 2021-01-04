package com.skosc.incontrol.reflect

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
internal class ParameterNameResolver(private val parameter: KParameter, private val type: ParameterType) {

   fun resolve(): String {
       return when(type) {
           ParameterType.BODY -> requireNotNull(parameter.name)
           ParameterType.PATH -> resolveForPath()
           ParameterType.QUERY -> resolveForQuery()
       }
   }

    private fun resolveForPath(): String {
        val annotation = parameter.findAnnotation<Path>()
        if (annotation?.isResolveDefault != false) {
            return requireNotNull(parameter.name)
        }
        return annotation.name
    }

    private fun resolveForQuery(): String {
        val annotation = parameter.findAnnotation<Query>()
        if (annotation?.isResolveDefault != false) {
            return requireNotNull(parameter.name)
        }
        return annotation.name
    }
}