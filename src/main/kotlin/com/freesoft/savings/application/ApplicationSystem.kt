package com.freesoft.savings.application

import com.freesoft.savings.domain.FreesoftSystem

interface AccountingFreesoftSystem : FreesoftSystem {



}

object SavingsAccountFreesoftSystem : AccountingFreesoftSystem {
    override val name: String
        get() = "Production Savings Account FreesoftSystem"

    override fun checkStarted() {
        println("$name is started")
    }
}
