package com.freesoft.savings.integration.setup

import com.freesoft.savings.domain.system.Configuration
import com.typesafe.config.ConfigFactory
import java.time.LocalDateTime

data class InMemoryAppConfiguration(
    private val _serverPort: Int = serverConfig.getInt("port"),
    private val _dbUrl: String = dbConfig.getString("url"),
    private val _dbDriver: String = dbConfig.getString("driver")
) : Configuration(_serverPort, _dbUrl, _dbDriver, LocalDateTime.now().hour, LocalDateTime.now().hour + 2)

private val systemConfig = ConfigFactory.load("in-memory-test.conf")
    .getConfig("com.freesoft.in-memory-accounting-system")

private val logConfig = "log4j2.yaml"

private val dbConfig = systemConfig.getConfig("database")

private val serverConfig = systemConfig.getConfig("server")