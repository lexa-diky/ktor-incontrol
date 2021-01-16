package com.skosc.incontrol.handler.parameter

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Dependency
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.handler.parameter.adapter.TypeAdapterRegistry
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
internal class ParameterTypeResolver(private val typeAdapterRegistry: TypeAdapterRegistry) {

    private val buildInDependencyTypes: List<KType> = listOf(
        ApplicationCall::class.createType()
    )

    fun resolve(parameter: KParameter): ParameterType = when {
        checkIsBody(parameter) -> ParameterType.BODY
        checkIsPath(parameter) -> ParameterType.PATH
        checkIsQuery(parameter) -> ParameterType.QUERY
        checkIsDependency(parameter) -> ParameterType.DEPENDENCY
        else -> ParameterType.AUTO
    }

    private fun checkIsBody(parameter: KParameter): Boolean {
        return parameter.name == NAME_BODY || parameter.hasAnnotation<Body>()
    }

    private fun checkIsPath(parameter: KParameter): Boolean {
        return if (parameter.hasAnnotation<Path>()) {
            if (typeAdapterRegistry.contains(parameter.type)) {
                true
            } else {
                inControlError(
                    code = InControlErrorCode.PARAMETER_UNSUPPORTED_TYPE,
                    reason = "Unsupported type: ${parameter.type} for parameter: ${parameter.name}",
                    howToSolve = "Add type adapter for ${parameter.type}"
                )
            }
        } else {
            false
        }
    }

    private fun checkIsQuery(parameter: KParameter): Boolean {
        return if (parameter.hasAnnotation<Query>()) {
            if (typeAdapterRegistry.contains(parameter.type)) {
                true
            } else {
                inControlError(
                    code = InControlErrorCode.PARAMETER_UNSUPPORTED_TYPE,
                    reason = "Unsupported type: ${parameter.type} for parameter: ${parameter.name}",
                    howToSolve = "Add type adapter for ${parameter.type}"
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

    companion object {

        private const val NAME_BODY = "body"
    }
}