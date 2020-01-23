package com.freesoft.savings

import com.freesoft.savings.application.AccountingFreesoftSystem
import com.freesoft.savings.application.OpenSavingAccount
import com.freesoft.savings.application.OpenSavingAccountReq
import com.freesoft.savings.application.handler.CreateAccountHandler
import com.freesoft.savings.infrastructure.CustomJackson.auto
import com.freesoft.savings.infrastructure.error.HttpExceptionHandler
import com.freesoft.savings.infrastructure.error.anErrorResponse
import org.http4k.core.*
import org.http4k.core.Method.POST
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    val createAccount: CreateAccountHandler
) {

    val openSavingAccountReqLens = Body.auto<OpenSavingAccountReq>().toLens()

    operator fun invoke(system: AccountingFreesoftSystem): RoutingHttpHandler =
        HttpExceptionHandler()
            .then(DebuggingFilters.PrintRequestAndResponse())
            .then(ServerFilters.CatchLensFailure { failureFn ->
                anErrorResponse(Status.BAD_REQUEST, listOf(failureFn.cause?.message ?: "Unexpected error"))
            })
            .then(
                routes(
                    "/api/accounts" bind POST to system.openSavingAccount()
                )
            )

    private fun AccountingFreesoftSystem.openSavingAccount() = { request: Request ->
        createAccount(openSavingAccountReqLens(request)).execute(this)
        Response(Status.OK)
    }
}