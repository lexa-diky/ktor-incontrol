package com.skosc.incontrol.annotation

import com.skosc.incontrol.controller.Controller
import kotlin.reflect.KParameter

/**
 * Marks handler method parameter as [ParameterType.Query]
 *
 * @author a.yakovlev
 * @since indev
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query