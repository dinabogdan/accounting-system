package com.freesoft.savings.domain.system

import com.freesoft.savings.Router
import com.freesoft.savings.application.TransactionManager
import com.freesoft.savings.domain.repository.Repository
import org.http4k.server.Http4kServer

interface FreesoftSystem {
    val name: String

    val txManager: TransactionManager<Repository>

    infix fun `start as server`(router: Router): Http4kServer
}
