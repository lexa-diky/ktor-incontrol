package com.skosc.incontrol.exeption

/**
 * @throws InControlException created with passed parameters
 *
 * @author a.yakovlev
 * @since indev
 */
internal fun inControlError(
    code: InControlErrorCode,
    reason: String,
    howToSolve: String,
    cause: Throwable? = null
): Nothing {
    throw InControlException(
        cause = cause,
        errorCode = code,
        message = """
            
            ======
            Some error occurred inside InControl library.
            Error Code: ${code.readable}
            Reason: $reason
            Possible Solution: $howToSolve
            ======
        """.trimIndent()
    )
}