package com.freesoft.savings.integration

import com.freesoft.savings.integration.setup.IntegrationTest
import io.kotlintest.specs.BehaviorSpec
import org.http4k.client.ApacheClient

abstract class BaseTest : BehaviorSpec() {

    val baseUrl = "http://localhost:${IntegrationTest.application.config.serverPort}"

    val send = ApacheClient()


}