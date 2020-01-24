package com.freesoft.savings.integration

import com.freesoft.savings.infrastructure.toJson
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
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

            val holderKey = UUID.randomUUID().toString()

            @Language("JSON")
            val createAccReqPayload = """
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

            val createAccReq = Request(Method.POST, "$baseUrl/api/accounts")
                .header("Content-Type", "application/json")
                .body(createAccReqPayload)

            send(createAccReq)

            When("RetrieveAccount by holder key") {

                val retrieveAccReq = Request(Method.GET, "$baseUrl/api/accounts/$holderKey")
                    .header("Content-Type", "application/json")
                val response = send(retrieveAccReq)
                response.status shouldBe Status.OK

                Then("Return the expected account") {

                    val responseBody = response.bodyString().toJson()

                    responseBody shouldNotBe null
                    responseBody["account"]["accountHolder"]["key"].asText() shouldBe holderKey
                    responseBody["account"]["name"].asText() shouldBe "Acc01"

                }

            }

        }

    }
}