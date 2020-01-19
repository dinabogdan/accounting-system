package com.freesoft.savings

import com.freesoft.savings.application.*
import com.freesoft.savings.domain.repository.Repository
import com.freesoft.savings.infrastructure.serverConfig
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import com.freesoft.savings.infrastructure.logConfig
import org.apache.logging.log4j.core.config.Configurator
import org.http4k.core.*
import org.slf4j.LoggerFactory
import com.freesoft.savings.infrastructure.CustomJackson.auto
import com.freesoft.savings.infrastructure.database.TransactionManagerImpl
import com.freesoft.savings.infrastructure.database.createDb
import com.freesoft.savings.infrastructure.dbConfig

fun main() {

    Configurator.initialize(null, logConfig)

    val logger = LoggerFactory.getLogger("main")

    val openSavingAccountReqLens = Body.auto<OpenSavingAccountReq>().toLens()

    val db = createDb(dbConfig.getString("url"), dbConfig.getString("driver"))

    val system = SavingsAccountFreesoftSystem(TransactionManagerImpl(db, AccountRepository()))

    logger.info("Starting server...")
    val port = serverConfig.getInt("port")
    DebuggingFilters
        .PrintRequestAndResponse()
        .then(
            routes(
                "/api/savings" bind Method.POST to { request ->
                    OpenSavingAccount(openSavingAccountReqLens(request)).execute(system)
                    Response(OK)
                }
            )
        ).asServer(
            Jetty(port)
        ).start()
        .block()
    logger.info("Server started on port $port")
}