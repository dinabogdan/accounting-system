package com.freesoft.savings.application.handler

import com.freesoft.savings.application.OpenSavingAccountReq

interface CreateAccountHandler {
    operator fun invoke(openSavingAccountReq: OpenSavingAccountReq)
}

class CreateAccountHandlerImpl : CreateAccountHandler {
    override fun invoke(openSavingAccountReq: OpenSavingAccountReq) {

    }

}