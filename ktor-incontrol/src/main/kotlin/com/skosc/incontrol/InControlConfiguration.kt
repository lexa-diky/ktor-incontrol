package com.skosc.incontrol

import com.skosc.incontrol.di.DIContainerWrapper
import com.skosc.incontrol.exeption.InControlErrorCode
import com.skosc.incontrol.exeption.inControlError
import com.skosc.incontrol.handler.parameter.adapter.*
import io.ktor.application.*

class InControlConfiguration(val application: Application) {

    var registerDefaultTypeAdapters: Boolean = true

    var diContainer: DIContainerWrapper = DIContainerWrapper.empty()

    internal val typeAdapterRegistry: TypeAdapterRegistry = TypeAdapterRegistry()

    private var isBuild: Boolean = false

    internal fun build() : InControlConfiguration {
        if (isBuild) inControlError(
            code = InControlErrorCode.OTHER_INTEGRITY,
            reason = "InControlConfiguration::build was called twice",
            howToSolve = "Make issue on github"
        )
        registerDefaultTypeAdaptersIfNeeded()
        isBuild = true
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun registerTypeAdapters(adapters: List<PlainTypeAdapter<*>>) {
        adapters.forEach {
            typeAdapterRegistry.register(it as PlainTypeAdapter<Any>)
        }
    }

    private fun registerDefaultTypeAdaptersIfNeeded() {
        if (registerDefaultTypeAdapters) {
            registerTypeAdapters(listOf(
                StringPlainTypeAdapter(),
                IntPlainTypeAdapter(),
                ShortPlainTypeAdapter(),
                LongPlainTypeAdapter(),
                DoublePlainTypeAdapter(),
                FloatPlainTypeAdapter(),
                BooleanPlainTypeAdapter()
            ))
        }
    }
}