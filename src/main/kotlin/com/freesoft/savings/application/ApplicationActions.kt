package com.freesoft.savings.application

import com.freesoft.savings.domain.Command
import com.freesoft.savings.domain.system.FreesoftSystem
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class OpenSavingAccount(
    val openSavingAccountReq: OpenSavingAccountReq
) : Command<FreesoftSystem> {

    private val logger: Logger = LoggerFactory.getLogger(OpenSavingAccount::class.java)

    override val description: String
        get() = "Open a saving account"

    override fun execute(system: FreesoftSystem) {
        val account = openSavingAccountReq.toAccount()
        system.txManager.tx {
            val holderKey = addHolder(account.accountHolder)
            addAccount(account, holderKey)
        }
    }
}

