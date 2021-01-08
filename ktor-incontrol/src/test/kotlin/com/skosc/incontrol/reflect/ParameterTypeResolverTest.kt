package com.skosc.incontrol.reflect

import com.skosc.incontrol.annotation.Body
import com.skosc.incontrol.annotation.Path
import com.skosc.incontrol.annotation.Query
import com.skosc.incontrol.handler.ParameterType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findParameterByName

internal class ParameterTypeResolverTest {

    @Test
    fun `resolves body type by name`() {
        val bodyParameter = TestingSample::handlerOfBody.findParameterByName("body")!!
        assertEquals(ParameterType.BODY, ParameterTypeResolver().resolve(bodyParameter))
    }

    @Test
    fun `resolves body type by annotation`() {
        val bodyParameter = TestingSample::handlerOfBodyWithAnnotation.findParameterByName("param")!!
        assertEquals(ParameterType.BODY, ParameterTypeResolver().resolve(bodyParameter))
    }

    @Test
    fun `resolves path type by annotation`() {
        val bodyParameter = TestingSample::handlerOfPath.findParameterByName("path")!!
        assertEquals(ParameterType.PATH, ParameterTypeResolver().resolve(bodyParameter))
    }

    @Test
    fun `resolves query type by annotation`() {
        val bodyParameter = TestingSample::handlerOfQuery.findParameterByName("query")!!
        assertEquals(ParameterType.QUERY, ParameterTypeResolver().resolve(bodyParameter))
    }

    internal class TestingSample {

        fun handlerOfBody(body: String): Int = 2

        fun handlerOfBodyWithAnnotation(@Body param: String): Int = 3

        fun handlerOfPath(@Path path: String): Int = 4

        fun handlerOfQuery(@Query query: String): Int = 5
    }
}