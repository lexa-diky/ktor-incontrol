package com.skosc.incontrol.annotation

annotation class Dependency(val name: String) {

    companion object {

        internal const val DEFAULT_NAME_RESOLVE = "__RESOLVE_DEFAULT"
    }
}

val Dependency.isResolveDefault: Boolean get() = name == Dependency.DEFAULT_NAME_RESOLVE