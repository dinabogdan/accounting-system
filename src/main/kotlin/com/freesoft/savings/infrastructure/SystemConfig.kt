package com.freesoft.savings.infrastructure

import com.freesoft.savings.domain.system.Configuration
import com.typesafe.config.ConfigFactory
import org.apache.logging.log4j.core.config.Configurator
import java.time.LocalDateTime

data class ApplicationConfiguration(
    private val _serverPort: Int = serverConfig.getInt("port"),
    private val _dbUrl: String = dbConfig.getString("url"),
    private val _dbDriver: String = dbConfig.getString("driver"),
    private val _startHour: Int = workingTimeConfig.getInt("startHour"),
    private val _endHour: Int = workingTimeConfig.getInt("endHour")
) : Configuration(_serverPort, _dbUrl, _dbDriver, _startHour, _endHour) {

    init {
        Configurator.initialize(null, logConfig)
    }

}


private val systemConfig = ConfigFactory.load("application.conf")
    .getConfig("com.freesoft.accounting-system")

private val workingTimeConfig = systemConfig.getConfig("workingTime")

private val logConfig = "log4j2.yaml"

private val dbConfig = systemConfig.getConfig("database")

private val serverConfig = systemConfig.getConfig("server")