package com.freesoft.savings.application

import com.freesoft.savings.domain.FreesoftSystem
import com.freesoft.savings.domain.repository.Repository

interface AccountingFreesoftSystem : FreesoftSystem {
    val txManager: TransactionManager<Repository>
}

class SavingsAccountFreesoftSystem(
    override val txManager: TransactionManager<Repository>
) : AccountingFreesoftSystem {

    override val name: String
        get() = "Production Savings Account FreesoftSystem"

    override fun checkStarted() {
        println("$name is started")
    }
}
