package com.freesoft.savings.domain.system

abstract class Configuration(
    open val serverPort: Int,
    open val dbUrl: String,
    open val dbDriver: String,
    open val startHour: Int,
    open val endHour: Int
)