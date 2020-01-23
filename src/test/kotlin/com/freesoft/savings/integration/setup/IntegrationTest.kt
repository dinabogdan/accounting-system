package com.freesoft.savings.integration.setup

import com.freesoft.savings.Router
import com.freesoft.savings.application.AccountingFreesoftSystem
import com.freesoft.savings.application.handler.CreateAccountHandlerImpl
import com.freesoft.savings.domain.system.Configuration
import io.kotlintest.extensions.TestListener
import org.http4k.server.Http4kServer
import org.slf4j.LoggerFactory
import java.io.Closeable

private val logger = LoggerFactory.getLogger("IntegrationTest")

class InMemoryApplicationSystem(
    private val _configuration: Configuration
) : AccountingFreesoftSystem(_configuration) {

    override val name: String
        get() = "In Memory Application system"
}

class Application : Closeable {

    private val system =
        InMemoryApplicationSystem(InMemoryAppConfiguration())

    private val router = Router(CreateAccountHandlerImpl())

    private val server: Http4kServer

    init {
        server = system `start as server` router
    }

    override fun close() {
        logger.info("Closing Integration Test Application ...")
    }
}

object IntegrationTest : TestListener {

    private val lazyApp = lazy { Application() }
    val application: Application by lazyApp

    override fun afterProject() {
        if (lazyApp.isInitialized()) {
            application.close()
        }
    }
}

