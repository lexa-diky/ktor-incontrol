package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.handler.ParameterType
import io.ktor.application.*
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSupertypeOf

/**
 * Helper class that resolves [ParameterType] from [KParameter]
 *
 * @author a.yakovlev
 * @since indev
 */
internal class ParameterTypeResolver {

    private val buildInDependencyTypes: List<KType> = listOf(
        ApplicationCall::class.createType()
    )

    private val pathAndQueryTypes: List<KType> = listOf(
        String::class.createType(),
        Int::class.createType(),
        Double::class.createType(),
        Boolean::class.createType()
    )

    fun resolve(parameter: KParameter): ParameterType = when {
        checkIsBody(parameter) -> ParameterType.BODY
        checkIsPath(parameter) -> ParameterType.PATH
        checkIsQuery(parameter) -> ParameterType.QUERY
        checkIsDependency(parameter) -> ParameterType.DEPENDENCY
        else -> error("Can't resolve parameter type of $parameter")
    }

    private fun checkIsBody(parameter: KParameter) : Boolean {
        return parameter.name == NAME_BODY || parameter.hasAnnotation<Body>()
    }

    private fun checkIsPath(parameter: KParameter): Boolean {
        return parameter.hasAnnotation<Path>() && pathAndQueryTypes.any { it.isSupertypeOf(parameter.type) }
    }

    private fun checkIsQuery(parameter: KParameter): Boolean {
        return parameter.hasAnnotation<Query>() && pathAndQueryTypes.any { it.isSupertypeOf(parameter.type) }
    }

    private fun checkIsDependency(parameter: KParameter): Boolean {
        return parameter.hasAnnotation<Dependency>() ||
                buildInDependencyTypes.any { it.isSupertypeOf(parameter.type) }
    }

    companion object {

        val DEFAULT = ParameterTypeResolver()

        private const val NAME_BODY = "body"
    }
}