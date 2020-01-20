package com.freesoft.savings.application

import com.freesoft.savings.domain.Command
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class OpenSavingAccount(
    val openSavingAccountReq: OpenSavingAccountReq
) : Command<AccountingFreesoftSystem> {

    private val logger: Logger = LoggerFactory.getLogger(OpenSavingAccount::class.java)

    override val description: String
        get() = "Ope"

    override fun execute(system: AccountingFreesoftSystem) {
        val account = openSavingAccountReq.toAccount()
        system.txManager.tx {
            val holderKey = addHolder(account.accountHolder)
            addAccount(account, holderKey)
        }
    }
}

