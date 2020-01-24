package com.freesoft.savings.integration

import io.kotlintest.shouldBe
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.intellij.lang.annotations.Language
import java.time.LocalDateTime
import java.util.*

class OpenSavingsAccountTest : BaseTest() {

    init {

        Given("1. A valid OpenSavingAccountReq payload") {

            val holderKey = UUID.randomUUID().toString()

            @Language("JSON")
            val requestPayload = """
                {
                "accHolderType": "CLIENT",
                "accHolderKey": "$holderKey",
                "maturityDate": "${LocalDateTime.now()}",
                "name": "Acc01",
                "accruedInterest": 10.0,
                "interestPmtPoint": "everyMonth",
                "accType": "savingsPlan",
                "currency": "EUR"
                }
            """.trimIndent()

            val request = Request(Method.POST, "$baseUrl/api/accounts")
                .header("Content-Type", "application/json")
                .body(requestPayload)

            When("Sending it") {

                val response: Response = send(request)

                Then("Create a saving account") {

                    response.status shouldBe Status.OK

                }

            }

        }

        Given("2. A valid OpenSavingAccountReq payload that is send to server") {

            When("RetrieveAccount by holder key") {

                Then("Return the expected account") {


                }

            }

        }

    }
}