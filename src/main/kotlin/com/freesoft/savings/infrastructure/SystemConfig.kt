package com.freesoft.savings.infrastructure

import com.freesoft.savings.domain.system.Configuration
import com.typesafe.config.ConfigFactory
import org.apache.logging.log4j.core.config.Configurator

data class ApplicationConfiguration(
    private val _serverPort: Int = serverConfig.getInt("port"),
    private val _dbUrl: String = dbConfig.getString("url"),
    private val _dbDriver: String = dbConfig.getString("driver")
) : Configuration(_serverPort, _dbUrl, _dbDriver) {

    init {
        Configurator.initialize(null, logConfig)
    }

}

private val systemConfig = ConfigFactory.load("application.conf")
    .getConfig("com.freesoft.accounting-system")

private val logConfig = "log4j2.yaml"

private val dbConfig = systemConfig.getConfig("database")

private val serverConfig = systemConfig.getConfig("server")