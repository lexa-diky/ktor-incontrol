package com.skosc.incontrol.kodein.autowire

import io.ktor.http.*

annotation class AutoRouting(val method: String, val group: String = DEFAULT_GROUP) {

    companion object {

        const val DEFAULT_GROUP = ""
    }
}