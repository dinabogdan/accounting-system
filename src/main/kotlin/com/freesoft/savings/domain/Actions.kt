package com.freesoft.savings.domain

import com.freesoft.savings.infrastructure.System

interface Action<S : System, T> {

    val description: String

    fun execute(system: S): T

}

interface Query<SYS : System, T> : Action<SYS, T>

interface Command<SYS : System> : Action<SYS, Unit>

