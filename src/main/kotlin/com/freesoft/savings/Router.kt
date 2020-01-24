package com.freesoft.savings

import com.freesoft.savings.api.AccountResponse
import com.freesoft.savings.application.AccountingFreesoftSystem
import com.freesoft.savings.api.OpenSavingAccountReq
import com.freesoft.savings.api.RetrieveSavingAccountReq
import com.freesoft.savings.application.handler.CreateAccountHandler
import com.freesoft.savings.application.handler.RetrieveAccountHandler
import com.freesoft.savings.infrastructure.CustomJackson.auto
import com.freesoft.savings.infrastructure.error.HttpExceptionHandler
import com.freesoft.savings.infrastructure.error.anErrorResponse
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.lens.Path
import org.http4k.lens.nonEmptyString
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    val createAccount: CreateAccountHandler,
    val retrieveAccount: RetrieveAccountHandler
) {

    private val openSavingAccountReqLens = Body.auto<OpenSavingAccountReq>().toLens()
    private val accHolderKeyLens = Path.nonEmptyString().map(::RetrieveSavingAccountReq).of("accHolderKey")
    private val accountResponseLens = Body.auto<AccountResponse>().toLens()

    operator fun invoke(system: AccountingFreesoftSystem): RoutingHttpHandler =
        HttpExceptionHandler()
            .then(DebuggingFilters.PrintRequestAndResponse())
            .then(WorkingTimeFilter.Check(system))
            .then(ServerFilters.CatchLensFailure { failureFn ->
                anErrorResponse(Status.BAD_REQUEST, listOf(failureFn.cause?.message ?: "Unexpected error"))
            })
            .then(
                routes(
                    "/api/accounts/{accHolderKey}" bind GET to system.retrieveSavingAccount(),
                    "/api/accounts" bind POST to system.openSavingAccount()
                )
            )

    private fun AccountingFreesoftSystem.retrieveSavingAccount() = { request: Request ->
        val account = retrieveAccount(accHolderKeyLens(request)).execute(this)
        accountResponseLens(AccountResponse(account), Response(Status.OK))
    }

    private fun AccountingFreesoftSystem.openSavingAccount() = { request: Request ->
        createAccount(openSavingAccountReqLens(request)).execute(this)
        Response(Status.OK)
    }
}