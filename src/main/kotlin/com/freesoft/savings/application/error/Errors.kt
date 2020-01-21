package com.freesoft.savings.application.error

import org.http4k.core.Status
import java.lang.RuntimeException

open class HttpException(
    val status: Status,
    message: String = status.description
) : RuntimeException(message)