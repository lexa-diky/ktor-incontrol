package com.skosc.incontrol

import com.skosc.incontrol.controller.ControllerParameterRetriever
import com.skosc.incontrol.di.DIContainerWrapper
import com.skosc.incontrol.handler.ControllerHandlerMethodFactory
import com.skosc.incontrol.handler.HandlerMethodFinder
import com.skosc.incontrol.handler.parameter.ControllerHandlerParameterFactory
import com.skosc.incontrol.handler.parameter.adapter.TypeAdapterRegistry
import com.skosc.incontrol.reflect.RunningEnvironment
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import java.util.*

/**
 * Settings class of InControl module
 *
 * @author a.yakovlev
 * @since indev
 */
class InControl(val application: Application, configuration: InControlConfiguration) {

    /**
     * Container witch will retrieve handler method dependencies
     */
    var diContainer: DIContainerWrapper = configuration.diContainer

    /**
     * Environment of current running instance of application
     */
    val runningEnvironment: RunningEnvironment = RunningEnvironment()

    internal val typeAdapterRegistry: TypeAdapterRegistry = configuration.typeAdapterRegistry

    internal val controllerHandlerParameterFactory = ControllerHandlerParameterFactory(typeAdapterRegistry)

    internal val controllerHandlerMethodFactory: ControllerHandlerMethodFactory =
        ControllerHandlerMethodFactory(controllerHandlerParameterFactory)

    internal val parameterRetriever: ControllerParameterRetriever =
        ControllerParameterRetriever(configuration.typeAdapterRegistry)

    internal val handlerMethodFinder: HandlerMethodFinder = HandlerMethodFinder()

    /**
     * Feature for enabling [Controller] usage
     */
    companion object Feature : ApplicationFeature<Application, InControlConfiguration, InControl> {

        override val key: AttributeKey<InControl> = AttributeKey("InControl")

        override fun install(pipeline: Application, configure: InControlConfiguration.() -> Unit): InControl {
            val configuration = InControlConfiguration(pipeline).apply(configure).build()
            return InControl(pipeline, configuration)
        }
    }
}
