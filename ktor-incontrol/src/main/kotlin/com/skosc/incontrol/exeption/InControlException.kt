package com.skosc.incontrol.exeption

import java.lang.RuntimeException

/**
 * Base exception for library specific errors
 */
class InControlException(message: String, val errorCode: InControlErrorCode, cause: Throwable? = null)
    : RuntimeException(message, cause)