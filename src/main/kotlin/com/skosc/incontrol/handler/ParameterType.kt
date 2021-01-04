package com.skosc.incontrol.handler

import com.skosc.incontrol.annotation.Body

/**
 * Enum containing all possible handler parameter types.
 *
 * @author a.yakovlev
 * @since indev
 */
internal enum class ParameterType {
    /**
     * Body of HTTP request. Can be resolved by name of parameter 'body' or by adding annotation [Body]
     */
    BODY,

    /**
     * Path parameter in request url, for example https://domain.com/{parameter}
     */
    PATH,

    /**
     * Query parameter in request url, for example https://domain.com?parameter=value
     */
    QUERY
}