package com.freesoft.savings.integration

import org.slf4j.LoggerFactory
import java.io.Closeable

private val logger = LoggerFactory.getLogger("IntegrationTest")

class Application : Closeable {


    override fun close() {
        logger.info("Closing Integration Test Application ...")
    }

}