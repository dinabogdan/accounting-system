package com.freesoft.savings

import com.freesoft.savings.infrastructure.serverConfig
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    DebuggingFilters
        .PrintRequestAndResponse()
        .then(
            routes(
                "/" bind Method.GET to { Response(OK).body("Home") },
                "/savings" bind Method.POST to { request -> Response(OK) }
            )
        ).asServer(Netty(serverConfig.getInt("port"))).start()
}