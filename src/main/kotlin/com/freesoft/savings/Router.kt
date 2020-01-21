package com.freesoft.savings

import com.freesoft.savings.application.AccountRepository
import com.freesoft.savings.application.OpenSavingAccount
import com.freesoft.savings.application.OpenSavingAccountReq
import com.freesoft.savings.application.SavingsAccountFreesoftSystem
import com.freesoft.savings.application.handler.CreateAccountHandler
import com.freesoft.savings.infrastructure.CustomJackson.auto
import com.freesoft.savings.infrastructure.database.TransactionManagerImpl
import com.freesoft.savings.infrastructure.database.createDb
import com.freesoft.savings.infrastructure.dbConfig
import com.freesoft.savings.infrastructure.error.HttpExceptionHandler
import com.freesoft.savings.infrastructure.error.anErrorResponse
import com.freesoft.savings.infrastructure.serverConfig
import org.http4k.core.*
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    val createAccountHandler: CreateAccountHandler
) {

    val openSavingAccountReqLens = Body.auto<OpenSavingAccountReq>().toLens()



    private val db = createDb(dbConfig.getString("url"), dbConfig.getString("driver"))

    private val system = SavingsAccountFreesoftSystem(TransactionManagerImpl(db, AccountRepository()))

    operator fun invoke(): RoutingHttpHandler =
        HttpExceptionHandler()
            .then(DebuggingFilters.PrintRequestAndResponse())
            .then(ServerFilters.CatchLensFailure { failureFn ->
                anErrorResponse(Status.BAD_REQUEST, listOf(failureFn.cause?.message ?: "Unexpected error"))
            })
            .then(
                routes(
                    "/api/savings" bind Method.POST to { request ->
                        OpenSavingAccount(openSavingAccountReqLens(request)).execute(system)
                        Response(Status.OK)
                    }
                )
            )

}