package com.skosc.incontrol

import com.skosc.incontrol.controller.ControllerParameterRetriever
import com.skosc.incontrol.di.DIContainerWrapper
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
class InControl(val application: Application) {

    /**
     * Container witch will retrieve handler method dependencies
     */
    var diContainer: DIContainerWrapper = DIContainerWrapper.empty()

    /**
     * Modules that will be applied to feature, when [ensureInitialized] will be called first time
     */
    private val modules: MutableList<InControlModule> = LinkedList()

    private var isInitialized: Boolean = false

    internal val parameterRetriever: ControllerParameterRetriever = ControllerParameterRetriever()

    /**
     * Adds module with lazy initialization
     */
    fun registerModule(module: InControlModule) {
        modules.add(module)
    }

    private fun ensureInitialized() {
        if (!isInitialized) {
            modules.forEach { it.apply(this) }
            modules.clear()
            isInitialized = true
        }
    }

    /**
     * Feature for enabling [Controller] usage
     */
    companion object Feature : ApplicationFeature<Application, InControl, InControl> {

        override val key: AttributeKey<InControl> = AttributeKey("InControl")

        override fun install(pipeline: Application, configure: InControl.() -> Unit): InControl {
            return InControl(pipeline).apply(configure).apply {
                ensureInitialized()
            }
        }
    }
}
