package com.freesoft.savings.application.handler

import com.freesoft.savings.application.OpenSavingAccount
import com.freesoft.savings.api.OpenSavingAccountReq
import com.freesoft.savings.api.RetrieveSavingAccountReq
import com.freesoft.savings.application.RetrieveAccount

interface CreateAccountHandler {
    operator fun invoke(openSavingAccountReq: OpenSavingAccountReq): OpenSavingAccount
}

class CreateAccountHandlerImpl : CreateAccountHandler {
    override fun invoke(openSavingAccountReq: OpenSavingAccountReq): OpenSavingAccount {
        return OpenSavingAccount(openSavingAccountReq)
    }
}