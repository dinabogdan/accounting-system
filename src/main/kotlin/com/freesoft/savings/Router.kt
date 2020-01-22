package com.freesoft.savings

import com.freesoft.savings.application.OpenSavingAccount
import com.freesoft.savings.application.OpenSavingAccountReq
import com.freesoft.savings.application.handler.CreateAccountHandler
import com.freesoft.savings.domain.system.FreesoftSystem
import com.freesoft.savings.infrastructure.CustomJackson.auto
import com.freesoft.savings.infrastructure.error.HttpExceptionHandler
import com.freesoft.savings.infrastructure.error.anErrorResponse
import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

class Router(
    val createAccountHandler: CreateAccountHandler
) {

    val openSavingAccountReqLens = Body.auto<OpenSavingAccountReq>().toLens()

    operator fun invoke(system: FreesoftSystem): RoutingHttpHandler =
        HttpExceptionHandler()
            .then(DebuggingFilters.PrintRequestAndResponse())
            .then(ServerFilters.CatchLensFailure { failureFn ->
                anErrorResponse(Status.BAD_REQUEST, listOf(failureFn.cause?.message ?: "Unexpected error"))
            })
            .then(
                routes(
                    "/api/savings" bind POST to { request ->
                        OpenSavingAccount(openSavingAccountReqLens(request)).execute(system)
                        Response(Status.OK)
                    }
                )
            )
}