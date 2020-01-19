package com.freesoft.savings.infrastructure

import com.typesafe.config.ConfigFactory

val systemConfig = ConfigFactory.load("application.conf")
    .getConfig("com.freesoft.accounting-system")

val serverConfig = systemConfig.getConfig("server")