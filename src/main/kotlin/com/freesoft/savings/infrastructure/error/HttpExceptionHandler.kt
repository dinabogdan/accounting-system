package com.freesoft.savings.infrastructure.error

import com.freesoft.savings.application.error.HttpException
import org.slf4j.LoggerFactory
import com.freesoft.savings.infrastructure.CustomJackson.auto
import org.http4k.core.*

data class ErrorResponse(val errors: List<String?>)

object HttpExceptionHandler {
    private val logger = LoggerFactory.getLogger(HttpExceptionHandler::class.java)

    operator fun invoke() = Filter { it.handleError() }

    private fun HttpHandler.handleError(): (Request) -> Response {
        return { request ->
            try {
                this(request)
            } catch (httpException: HttpException) {
                logger.error("Error occurred: $httpException")
                anErrorResponse(httpException.status, listOf(httpException.message))
            }
        }
    }
}

private val errorLens = Body.auto<ErrorResponse>().toLens()

fun anErrorResponse(status: Status, messages: List<String?>): Response =
    Response(status).with(errorLens of ErrorResponse(messages))