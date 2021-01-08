package com.skosc.incontrol.exeption

/**
 * Standardized library error codes
 */
enum class InControlErrorCode(val readable: String) {
    HANDLER_TOO_MANY_MATCHING("Too Many Matching Handlers"),
    HANDLER_NOT_FOUND("No Handlers Found In Controllers"),
    HANDLER_NOT_IN_CLASS_INSTANCE("Handler Not In Class Instance"),
    HANDLER_PARAMETER_CAST("Can't Cast Handler Parameter"),
    PARAMETER_CANT_FIND_PARAMETER("Can't Find Parameter For Handler"),
    PARAMETER_CANT_RESOLVE_TYPE("Can't Resolve Type"),
    PARAMETER_UNSUPPORTED_TYPE("Unsupported Handler Parameter Type"),

    OTHER_INTEGRITY("Integrity Issue In Library")
}