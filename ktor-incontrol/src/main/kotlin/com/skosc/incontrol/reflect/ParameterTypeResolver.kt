package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
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
        StaticTypes.STRING,
        StaticTypes.DOUBLE,
        StaticTypes.INT,
        StaticTypes.BOOLEAN
    )

    fun resolve(parameter: KParameter): ParameterType = when {
        checkIsBody(parameter) -> ParameterType.BODY
        checkIsPath(parameter) -> ParameterType.PATH
        checkIsQuery(parameter) -> ParameterType.QUERY
        checkIsDependency(parameter) -> ParameterType.DEPENDENCY
        else -> throwCantResolveTypeParameter(parameter)
    }

    private fun checkIsBody(parameter: KParameter): Boolean {
        return parameter.name == NAME_BODY || parameter.hasAnnotation<Body>()
    }

    private fun checkIsPath(parameter: KParameter): Boolean {
        return if (parameter.hasAnnotation<Path>()) {
            if (pathAndQueryTypes.any { it.isSupertypeOf(parameter.type) }) {
                true
            } else {
                inControlError(
                    code = InControlErrorCode.PARAMETER_UNSUPPORTED_TYPE,
                    reason = "Unsupported type: ${parameter.type} for parameter: ${parameter.name}",
                    howToSolve = "Change parameter type to one of: $pathAndQueryTypes"
                )
            }
        } else {
            false
        }
    }

    private fun checkIsQuery(parameter: KParameter): Boolean {
        return if (parameter.hasAnnotation<Query>()) {
            if (pathAndQueryTypes.any { it.isSupertypeOf(parameter.type) }) {
                true
            } else {
                inControlError(
                    code = InControlErrorCode.PARAMETER_UNSUPPORTED_TYPE,
                    reason = "Unsupported type: ${parameter.type} for parameter: ${parameter.name}",
                    howToSolve = "Change parameter type to one of: $pathAndQueryTypes"
                )
            }
        } else {
            false
        }
    }

    private fun checkIsDependency(parameter: KParameter): Boolean {
        return parameter.hasAnnotation<Dependency>() ||
                buildInDependencyTypes.any { it.isSupertypeOf(parameter.type) }
    }

    private fun throwCantResolveTypeParameter(parameter: KParameter): Nothing = inControlError(
        code = InControlErrorCode.PARAMETER_CANT_RESOLVE_TYPE,
        reason = "Can't resolve parameter type of $parameter",
        howToSolve = "Add parameter type annotation: @Body, @Query, @Path, @Dependency"
    )

    companion object {

        val DEFAULT = ParameterTypeResolver()

        private const val NAME_BODY = "body"
    }
}