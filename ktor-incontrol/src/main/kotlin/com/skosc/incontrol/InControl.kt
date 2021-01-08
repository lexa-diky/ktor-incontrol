package com.skosc.incontrol

import com.skosc.incontrol.controller.ControllerParameterRetriever
import com.skosc.incontrol.di.DIContainerWrapper
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*

/**
 * Settings class of InControl module
 *
 * @author a.yakovlev
 * @since indev
 */
class InControl(val application: Application) {

    /**
     * Container witch will retrieve handler method dependencies
     */
    var diContainer: DIContainerWrapper = DIContainerWrapper.empty()

    internal val parameterRetriever: ControllerParameterRetriever = ControllerParameterRetriever()

    /**
     * Feature for enabling [Controller] usage
     */
    companion object Feature : ApplicationFeature<Application, InControl, InControl> {

        override val key: AttributeKey<InControl> = AttributeKey("InControl")

        override fun install(pipeline: Application, configure: InControl.() -> Unit): InControl {
            return InControl(pipeline).apply(configure)
        }
    }
}
