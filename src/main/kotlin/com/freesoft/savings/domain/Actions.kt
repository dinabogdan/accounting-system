package com.freesoft.savings.domain

import com.freesoft.savings.domain.system.FreesoftSystem

interface Action<S : FreesoftSystem, T> {

    val description: String

    fun execute(system: S): T

}

interface Query<SYS : FreesoftSystem, T> : Action<SYS, T>

interface Command<SYS : FreesoftSystem> : Action<SYS, Unit>

