package com.freesoft.savings

import com.typesafe.config.ConfigFactory

val systemConfig = ConfigFactory.load("application.conf")
    .getConfig("com.freesoft.accounting-system")

val serverConfig = systemConfig.getConfig("server")