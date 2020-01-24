package com.freesoft.savings.application

import com.freesoft.savings.api.OpenSavingAccountReq
import com.freesoft.savings.api.RetrieveSavingAccountReq
import com.freesoft.savings.domain.Account
import com.freesoft.savings.domain.Command
import com.freesoft.savings.domain.Query
import com.freesoft.savings.domain.system.FreesoftSystem
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class OpenSavingAccount(
    val openSavingAccountReq: OpenSavingAccountReq
) : Command<AccountingFreesoftSystem> {

    private val logger: Logger = LoggerFactory.getLogger(OpenSavingAccount::class.java)

    override val description: String
        get() = "Open a saving account"

    override fun execute(system: AccountingFreesoftSystem) {
        logger.info(description)
        val account = openSavingAccountReq.toAccount()
        system.txManager.tx {
            val holderKey = addHolder(account.accountHolder)
            addAccount(account, holderKey)
        }
    }
}

data class RetrieveAccount(
    val retrieveSavingAccountReq: RetrieveSavingAccountReq
) : Query<FreesoftSystem, Account> {

    private val logger: Logger = LoggerFactory.getLogger(RetrieveAccount::class.java)

    override val description: String
        get() = "Retrieve a saving account"

    override fun execute(system: FreesoftSystem): Account {
        logger.info(description)
        return system.txManager.tx {
            getAccountByHolderKey(retrieveSavingAccountReq.accHolderKey)!!
        }
    }
}

