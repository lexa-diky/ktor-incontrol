package com.skosc.incontrol.exeption

enum class InControlErrorCode(val readable: String) {
    HANDLER_TOO_MANY_MATCHING("Too Many Matching Handlers"),
    HANDLER_NOT_FOUND("No Handlers Found In Controllers")
}