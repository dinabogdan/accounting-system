package com.freesoft.savings.integration

import com.freesoft.savings.application.FreesoftSystem
import com.freesoft.savings.application.TransactionManager
import com.freesoft.savings.domain.repository.Repository
import org.slf4j.LoggerFactory
import java.io.Closeable

private val logger = LoggerFactory.getLogger("IntegrationTest")

class InMemoryApplicationSystem(
    override val txManager: TransactionManager<Repository>
) : FreesoftSystem {
    override val name: String
        get() = "In memory test-application system"

    override fun start() {
        println("$name is started")
    }

}

class Application : Closeable {

    val

    val system = InMemoryApplicationSystem()

    override fun close() {
        logger.info("Closing Integration Test Application ...")
    }

}