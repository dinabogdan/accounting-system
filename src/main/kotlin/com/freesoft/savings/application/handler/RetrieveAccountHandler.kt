package com.freesoft.savings.application.handler

import com.freesoft.savings.application.RetrieveAccount
import com.freesoft.savings.application.RetrieveSavingAccountReq

interface RetrieveAccountHandler {
    operator fun invoke(retrieveSavingAccountReq: RetrieveSavingAccountReq): RetrieveAccount
}

class RetrieveAccountHandlerImpl : RetrieveAccountHandler {
    override fun invoke(retrieveSavingAccountReq: RetrieveSavingAccountReq): RetrieveAccount {
        return RetrieveAccount(retrieveSavingAccountReq)
    }

}