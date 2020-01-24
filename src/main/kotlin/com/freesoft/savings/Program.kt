package com.freesoft.savings

import com.freesoft.savings.application.SavingsAccountFreesoftSystem
import com.freesoft.savings.application.handler.CreateAccountHandlerImpl
import com.freesoft.savings.application.handler.RetrieveAccountHandlerImpl
import com.freesoft.savings.infrastructure.ApplicationConfiguration
import org.slf4j.LoggerFactory

fun main() {

    val logger = LoggerFactory.getLogger("main")

    logger.info("Starting server...")

    val appConfig = ApplicationConfiguration()

    val system = SavingsAccountFreesoftSystem(appConfig)

    val router = Router(
        CreateAccountHandlerImpl(),
        RetrieveAccountHandlerImpl()
    )

    system `start as server` router

}