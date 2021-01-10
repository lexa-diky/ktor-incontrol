package com.skosc.incontrol.exeption

/**
 * Base exception for library specific errors
 */
class InControlException(message: String, val errorCode: InControlErrorCode, cause: Throwable? = null) :
    RuntimeException(message, cause)