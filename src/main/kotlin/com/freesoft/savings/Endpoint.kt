package com.freesoft.savings

import com.freesoft.savings.application.handler.CreateAccountHandlerImpl
import com.freesoft.savings.infrastructure.logConfig
import com.freesoft.savings.infrastructure.serverConfig
import org.apache.logging.log4j.core.config.Configurator
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

fun main() {

    Configurator.initialize(null, logConfig)

    val port = serverConfig.getInt("port")

    val logger = LoggerFactory.getLogger("main")

    logger.info("Starting server...")

    val router = Router(CreateAccountHandlerImpl())()

    val server = router.asServer(Jetty(port)).start()

    logger.info("Server started on port $port")
}