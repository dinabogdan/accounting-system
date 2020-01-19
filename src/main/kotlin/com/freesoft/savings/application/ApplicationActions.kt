package com.freesoft.savings.application

import com.freesoft.savings.domain.Command
import com.freesoft.savings.infrastructure.OpenSavingAccountReq

data class OpenSavingAccount(
    val openSavingAccountReq: OpenSavingAccountReq
) : Command<AccountingFreesoftSystem> {
    override val description: String
        get() = "Ope"

    override fun execute(system: AccountingFreesoftSystem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}